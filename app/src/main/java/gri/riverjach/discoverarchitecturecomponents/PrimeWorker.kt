package gri.riverjach.discoverarchitecturecomponents

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*


const val KEY_LOW_NUMBER = "lowNumber"
const val KEY_HIGH_NUMBER = "highNumber"

class PrimeWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val lowNumber = inputData.getInt(KEY_LOW_NUMBER, 1)
        val highNumber = inputData.getInt(KEY_HIGH_NUMBER, 1)
        Log.i("PrimeWorker", "Starting prime number search in [ $lowNumber , $highNumber]")

        val primeNumbers = calculatePrimeNumbers(lowNumber, highNumber)

        val sleepDuration = 1000 + Random().nextInt(3000)
        Thread.sleep(sleepDuration.toLong())

        Log.i("PrimeWorker", "Prime numbers, size = ${primeNumbers.size} , values=$primeNumbers")

        MainActivity.primeNumbers.addAll(primeNumbers)

        return Result.success()
    }
}