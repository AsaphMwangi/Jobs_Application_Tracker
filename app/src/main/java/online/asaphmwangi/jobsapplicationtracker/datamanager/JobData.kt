package online.asaphmwangi.jobsapplicationtracker.datamanager

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "jobs")
data class JobData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val company: String,
    val location: String,
    val status: String, // "Applied", "Interviewing", "Rejected"
    val date: Date,
    val userId: String? = null
)
