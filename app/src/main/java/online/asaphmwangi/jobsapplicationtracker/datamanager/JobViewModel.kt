package online.asaphmwangi.jobsapplicationtracker.datamanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JobViewModel(application: Application) : AndroidViewModel(application) {

    val readAllJobs:  LiveData<List<JobData>>
    val repository: JobRepository

    init {
        val jobDao = JobDatabase.getDatabase(application).jobDao()
        repository = JobRepository(jobDao)
        readAllJobs = repository.readAllJobs
    }

    fun addJob(jobData: JobData)
    {
        viewModelScope.launch(Dispatchers.IO) {

            repository.addJob(jobData)
        }

    }
    fun updateStatus(jobId: Int, newStatus: String) = viewModelScope.launch {
        repository.updateStatus(jobId, newStatus)
    }
    fun deleteJobById(jobId: Int) = viewModelScope.launch {
        repository.deleteJobById(jobId)
    }
}