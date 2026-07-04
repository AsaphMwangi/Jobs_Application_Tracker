package online.asaphmwangi.jobsapplicationtracker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import online.asaphmwangi.jobsapplicationtracker.datamanager.JobAdapter
import online.asaphmwangi.jobsapplicationtracker.datamanager.JobViewModel

class JobFragment : Fragment() {

    private lateinit var jobViewModel: JobViewModel
    private lateinit var adapter: JobAdapter
    private var statusFilter: String = "all"

    companion object {
        fun newInstance(status: String): JobFragment {
            val fragment = JobFragment()
            val args = Bundle()
            args.putString("status", status)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusFilter = arguments?.getString("status") ?: "all"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_job_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val emptyView = view.findViewById<TextView>(R.id.emptyView)

        adapter = JobAdapter(
            onItemClick = { selectedJob ->
                val intent = Intent(requireContext(), UpdateJobStatus::class.java)
                    .apply {
                        putExtra("job_id", selectedJob.id.toString())
                        putExtra("job_title", selectedJob.title)
                        putExtra("job_status", selectedJob.status)
                    }
                startActivity(intent)
            },
            onDeleteClick = { selectedJob ->
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Delete Job")
                    .setMessage("Are you sure you want to delete '${selectedJob.title}'?")
                    .setPositiveButton("Yes") { _, _ ->
                        jobViewModel.deleteJobById(selectedJob.id)
                        Toast.makeText(requireContext(), "Job deleted", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        jobViewModel = ViewModelProvider(this)[JobViewModel::class.java]
        jobViewModel.readAllJobs.observe(viewLifecycleOwner) { jobs ->
            val filteredJobs = if (statusFilter == "all") {
                jobs
            } else {
                jobs.filter { it.status == statusFilter }
            }
            adapter.setData(filteredJobs)
            emptyView.visibility = if (filteredJobs.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}
