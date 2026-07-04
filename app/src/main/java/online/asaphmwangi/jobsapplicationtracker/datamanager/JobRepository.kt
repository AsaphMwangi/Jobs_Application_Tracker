package online.asaphmwangi.jobsapplicationtracker.datamanager

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

class JobRepository(private val jobDao: JobDao) {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userId = auth.currentUser?.uid

    val readAllJobs: LiveData<List<JobData>> = jobDao.readAllJobs()

    suspend fun addJob(jobData: JobData) {
        val jobWithUser = jobData.copy(userId = userId)
        jobDao.addJob(jobWithUser)
        
        // Sync to Firestore if userId is available
        userId?.let { uid ->
            firestore.collection("users").document(uid)
                .collection("jobs")
                .document(jobWithUser.id.toString())
                .set(jobWithUser)
        }
    }

    suspend fun updateStatus(jobId: Int, newStatus: String) {
        jobDao.updateStatus(jobId, newStatus)
        userId?.let { uid ->
            firestore.collection("users").document(uid)
                .collection("jobs")
                .document(jobId.toString())
                .update("status", newStatus)
        }
    }

    suspend fun deleteJobById(jobId: Int) {
        jobDao.deleteJobById(jobId)
        userId?.let { uid ->
            firestore.collection("users").document(uid)
                .collection("jobs")
                .document(jobId.toString())
                .delete()
        }
    }
}
