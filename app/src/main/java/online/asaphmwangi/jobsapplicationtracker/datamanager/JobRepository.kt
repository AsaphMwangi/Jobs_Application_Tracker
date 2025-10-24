package online.asaphmwangi.jobsapplicationtracker.datamanager

import androidx.lifecycle.LiveData

class JobRepository(private val jobDao: JobDao) {
    val readAllJobs: LiveData<List<JobData>> = jobDao.readAllJobs()

     suspend fun addJob(jobData: JobData)
    {
        jobDao.addJob(jobData)
    }
    suspend fun updateStatus(jobId: Int, newStatus: String) {
        jobDao.updateStatus(jobId, newStatus)
    }

    suspend fun deleteJobById(jobId: Int) {
        jobDao.deleteJobById(jobId)
    }
}