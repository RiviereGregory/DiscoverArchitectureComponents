package gri.riverjach.discoverarchitecturecomponents

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadPrimeWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Log.i(
            "UploadPrimeWorker",
            "Uploading prime numbers ... size=${MainActivity.primeNumbers.size}"
        )
        Thread.sleep(3000)
        Log.i("UploadPrimeWorker", "Finished")

        return Result.success()
    }
}