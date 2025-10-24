package online.asaphmwangi.jobsapplicationtracker.datamanager

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JobDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addJob( job: JobData)

    @Query("SELECT * FROM jobs ORDER BY date DESC ")
    fun readAllJobs(): LiveData<List<JobData>>

    @Query("UPDATE jobs SET status = :newStatus WHERE id = :jobId")
    suspend fun updateStatus(jobId: Int, newStatus: String)

    @Query("DELETE FROM jobs WHERE id = :jobId")
    suspend fun deleteJobById(jobId: Int)


}