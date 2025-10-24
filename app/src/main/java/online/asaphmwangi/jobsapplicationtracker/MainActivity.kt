package online.asaphmwangi.jobsapplicationtracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ScrollingView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import online.asaphmwangi.jobsapplicationtracker.datamanager.AddJob
import online.asaphmwangi.jobsapplicationtracker.datamanager.JobAdapter
import online.asaphmwangi.jobsapplicationtracker.datamanager.JobViewModel




class MainActivity : AppCompatActivity() {



    private lateinit var topAppBar: MaterialToolbar
    private lateinit var floatingActionButton: FloatingActionButton

    private lateinit var jobViewModel: JobViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var nestedScrollingView: NestedScrollView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setStatusBarIconColorToBlack(window)



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        emptyView = findViewById(R.id.null_message)

        recyclerView = findViewById(R.id.jobs_recycler_view)
        val adapter = JobAdapter(
            onItemClick = { selectedJob ->
                val intent = Intent(this, UpdateJobStatus::class.java)
                    .apply { putExtra("job_id", selectedJob.id.toString())
                        putExtra("job_title", selectedJob.title)
                        putExtra("job_status", selectedJob.status) }
                startActivity(intent)
            },
            onDeleteClick = { selectedJob ->
                androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Delete Job")
                    .setMessage("Are you sure you want to delete '${selectedJob.title}'?")
                    .setPositiveButton("Yes") { _, _ ->
                        jobViewModel.deleteJobById(selectedJob.id)
                        Toast.makeText(this, "Job deleted", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        )

        recyclerView.adapter =adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        nestedScrollingView = findViewById(R.id.nestedScrollView)

        jobViewModel = ViewModelProvider(this).get(JobViewModel::class.java)
        jobViewModel.readAllJobs.observe(this, Observer{jobData ->
            adapter.setData(jobData)

            if (jobData.isEmpty()) {
                nestedScrollingView.visibility = View.GONE

                emptyView.visibility = View.VISIBLE
            } else {

                emptyView.visibility = View.GONE
                nestedScrollingView.visibility = View.VISIBLE
            }
        })


        topAppBar = findViewById(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener {

        }
        floatingActionButton = findViewById(R.id.add_job_application)
        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, AddJob::class.java))
        }




    }

    fun setStatusBarIconColorToBlack(window: Window) {
        WindowCompat.getInsetsController(window, window.decorView).let { controller ->
            controller.isAppearanceLightStatusBars = true
        }

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
    }
}