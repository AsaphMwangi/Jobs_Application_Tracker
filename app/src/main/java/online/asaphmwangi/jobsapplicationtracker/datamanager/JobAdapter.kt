package online.asaphmwangi.jobsapplicationtracker.datamanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import online.asaphmwangi.jobsapplicationtracker.R
import online.asaphmwangi.jobsapplicationtracker.databinding.JobItemBinding

class JobAdapter ( private val onItemClick: (JobData) -> Unit,
                   private val onDeleteClick: (JobData) -> Unit
) : RecyclerView.Adapter<JobAdapter.MyViewHolder>() {

    private var jobList = emptyList<JobData>()

    inner class MyViewHolder(private val binding: JobItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: JobData) {
            binding.jobTitle.text = currentItem.title
            binding.company.text = currentItem.company
            binding.location.text = currentItem.location

            binding.jobDate.text = currentItem.date.toString()
            val statusNumber = currentItem.status.toInt()
            var status =""

            if (statusNumber==1)
            {
                status = "Pending"
                binding.status.setBackgroundResource(R.drawable.pending_background)
            }else if (statusNumber==2)
            {
                status = "Interviewing"
                binding.status.setBackgroundResource(R.drawable.interviewing_background)
            }
            else if (statusNumber==3)
            {
                status = "Offer"
                binding.status.setBackgroundResource(R.drawable.approved_background)
            }
            else if (statusNumber==4)
            {
                status = "Rejected"
                binding.status.setBackgroundResource(R.drawable.rejected_background)
            }
            else{
                status = "Not working"
            }
            binding.status.text = status

            binding.jobStatusEdit.setOnClickListener {
                onItemClick(currentItem)
            }
            binding.jobDelete.setOnClickListener {
                onDeleteClick(currentItem)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = JobItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(jobList[position])
    }

    override fun getItemCount(): Int = jobList.size

    fun setData(newList: List<JobData>) {
        jobList = newList
        notifyDataSetChanged()
    }
}
