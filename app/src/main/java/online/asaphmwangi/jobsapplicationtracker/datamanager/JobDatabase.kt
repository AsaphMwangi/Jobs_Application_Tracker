package online.asaphmwangi.jobsapplicationtracker.datamanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [JobData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class JobDatabase: RoomDatabase(){

    abstract fun jobDao(): JobDao

    companion object
    {
        @Volatile
        private var INSTANCE: JobDatabase? = null

        fun getDatabase(context: Context): JobDatabase
        {
            val tempInstance = INSTANCE
            if (tempInstance !=null)
            {
                return tempInstance
            }
            synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JobDatabase::class.java,
                    "jobs"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}