package online.asaphmwangi.jobsapplicationtracker

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import online.asaphmwangi.jobsapplicationtracker.databinding.ActivityUpdateJobStatusBinding
import online.asaphmwangi.jobsapplicationtracker.datamanager.JobViewModel

class UpdateJobStatus : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateJobStatusBinding
    private lateinit var viewModel: JobViewModel

    private var jobId: Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateJobStatusBinding.inflate(layoutInflater)

        setStatusBarIconColorToBlack(window)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this).get(JobViewModel::class.java)

        binding.jobTitleUpdateShow.setText(intent.getStringExtra("job_title"))
        val itemId = intent.getStringExtra("job_id")
        if (itemId != null) {
            jobId = itemId.toInt()
        }


        val statusNumber = intent.getStringExtra("job_status")?.toInt()

        var status =""

        if (statusNumber==1)
        {
            status = "Pending"
            binding.statusValue.setBackgroundResource(R.drawable.pending_background)
        }else if (statusNumber==2)
        {
            status = "Interviewing"
            binding.statusValue.setBackgroundResource(R.drawable.interviewing_background)
        }
        else if (statusNumber==3)
        {
            status = "Offer"
            binding.statusValue.setBackgroundResource(R.drawable.approved_background)
        }
        else if (statusNumber==4)
        {
            status = "Rejected"
            binding.statusValue.setBackgroundResource(R.drawable.rejected_background)
        }
        else{
            status = "Not working"
        }
        val currentStatus = statusNumber.toString()

        binding.statusValue.setText(status)

        binding.jobStatusUpdateBtn.setOnClickListener {
            val newStatus = when (binding.statusRadio.checkedRadioButtonId) {
                R.id.interviewing_radio -> "2"
                R.id.offer_radio -> "3"
                R.id.rejected_radio -> "4"

                else -> currentStatus // fallback if none selected
            }

            if (newStatus != currentStatus) {
                viewModel.updateStatus(jobId, newStatus)
                Toast.makeText(this, "Status updated successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No change in status", Toast.LENGTH_SHORT).show()
            }

            finish()
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