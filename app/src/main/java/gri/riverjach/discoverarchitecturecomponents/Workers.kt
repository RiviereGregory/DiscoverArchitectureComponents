package gri.riverjach.discoverarchitecturecomponents

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerA(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Log.i("WorkerA", "Starting hard work...")
        Thread.sleep(2000)
        Log.i("WorkerA", "Work finished")
        return Result.success()
    }
}

class WorkerB(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Log.i("WorkerB", "Starting hard work...")
        Thread.sleep(2000)
        Log.i("WorkerB", "Work finished")
        return Result.success()
    }
}

class WorkerC(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Log.i("WorkerC", "Starting hard work...")
        Thread.sleep(2000)
        Log.i("WorkerC", "Work finished")
        return Result.success()
    }
}