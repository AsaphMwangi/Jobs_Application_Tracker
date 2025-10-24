package online.asaphmwangi.jobsapplicationtracker.datamanager

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import online.asaphmwangi.jobsapplicationtracker.MainActivity
import online.asaphmwangi.jobsapplicationtracker.R

class AddJob : AppCompatActivity() {

    private lateinit var addJobButton: Button
    private lateinit var jobTitle: TextInputEditText
    private lateinit var jobCompany: TextInputEditText
    private lateinit var jobLocation: TextInputEditText
    private lateinit var jobViewModel: JobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_job)
        setStatusBarIconColorToBlack(window)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        jobViewModel = ViewModelProvider(this).get(JobViewModel::class.java)
        jobTitle = findViewById(R.id.add_job_title)
        jobLocation = findViewById(R.id.add_location)
        jobCompany = findViewById(R.id.add_company)


        addJobButton = findViewById(R.id.add_job_btn)

        addJobButton.setOnClickListener {
            insertDataToDatabase()
        }

    }
    private fun insertDataToDatabase()
    {
        val job_title = jobTitle.text.toString().trim()
        val company = jobCompany.text.toString().trim()
        val location = jobLocation.text.toString().trim()
        val date = java.util.Date()


        if (inputCheck(job_title,company,location))
        {
            val job = JobData(0,job_title,company,location,"1",date)
            jobViewModel.addJob(job)
            Toast.makeText(this,"Job added successfully", Toast.LENGTH_SHORT).show()
            jobTitle.text?.clear()
            jobCompany.text?.clear()
            jobLocation.text?.clear()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else
        {
            Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(title: String, company: String, location: String): Boolean {
        return title.isNotBlank() && company.isNotBlank() && location.isNotBlank()
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