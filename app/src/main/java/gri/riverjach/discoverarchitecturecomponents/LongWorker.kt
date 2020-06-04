package gri.riverjach.discoverarchitecturecomponents

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

const val KEY_SLEEP_DURATION = "sleepDuration"
const val KEY_RESULT = "result"

class LongWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val sleepDuration = inputData.getLong(KEY_SLEEP_DURATION, 0)
        Log.i("LongWorker", "Starting LongWorker ... Going to sleep for $sleepDuration ms")
        Thread.sleep(sleepDuration)
        Log.i("LongWorker", "Finished!")

        val sleepQuality = when {
            sleepDuration < 500 -> "Just a quick nap"
            sleepDuration < 1000 -> "Feeling refreshed"
            else -> "WOW! The night is gone?"
        }

        var outputData = workDataOf(KEY_RESULT to sleepQuality)

        return Result.success(outputData)
    }
}