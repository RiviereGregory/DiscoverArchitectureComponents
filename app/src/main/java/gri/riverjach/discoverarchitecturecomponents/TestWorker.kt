package gri.riverjach.discoverarchitecturecomponents

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class TestWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Log.i("TestWorker", "Starting hard work...")
        Thread.sleep(2000)
        Log.i("TestWorker", "Work finished")
        return return Result.success()
    }
}