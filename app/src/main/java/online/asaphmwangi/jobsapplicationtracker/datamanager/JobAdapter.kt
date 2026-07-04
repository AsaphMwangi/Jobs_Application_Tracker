package online.asaphmwangi.jobsapplicationtracker.datamanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import online.asaphmwangi.jobsapplicationtracker.R
import online.asaphmwangi.jobsapplicationtracker.databinding.JobItemBinding

import com.google.android.material.color.MaterialColors
import java.text.SimpleDateFormat
import java.util.Locale

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

            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            binding.jobDate.text = binding.root.context.getString(R.string.applied_on, dateFormat.format(currentItem.date))

            val statusNumber = currentItem.status.toInt()
            var statusText = ""
            var containerColor = 0
            var textColor = 0

            when (statusNumber) {
                1 -> {
                    statusText = "Applied"
                    containerColor = MaterialColors.getColor(binding.root, com.google.android.material.R.attr.colorSecondaryContainer)
                    textColor = MaterialColors.getColor(binding.root, com.google.android.material.R.attr.colorOnSecondaryContainer)
                }
                2 -> {
                    statusText = "Interviewing"
                    containerColor = MaterialColors.getColor(binding.root, com.google.android.material.R.attr.colorTertiaryContainer)
                    textColor = MaterialColors.getColor(binding.root, com.google.android.material.R.attr.colorOnTertiaryContainer)
                }
                3 -> {
                    statusText = "Offer"
                    containerColor = MaterialColors.getColor(binding.root, com.google.android.material.R.attr.colorPrimaryContainer)
                    textColor = MaterialColors.getColor(binding.root, com.google.android.material.R.attr.colorOnPrimaryContainer)
                }
                4 -> {
                    statusText = "Rejected"
                    containerColor = MaterialColors.getColor(binding.root, com.google.android.material.R.attr.colorErrorContainer)
                    textColor = MaterialColors.getColor(binding.root, com.google.android.material.R.attr.colorOnErrorContainer)
                }
                else -> {
                    statusText = "Unknown"
                    containerColor = MaterialColors.getColor(binding.root, com.google.android.material.R.attr.colorSurfaceVariant)
                    textColor = MaterialColors.getColor(binding.root, com.google.android.material.R.attr.colorOnSurfaceVariant)
                }
            }

            binding.status.text = statusText
            binding.status.setTextColor(textColor)
            binding.statusContainer.setCardBackgroundColor(containerColor)

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
