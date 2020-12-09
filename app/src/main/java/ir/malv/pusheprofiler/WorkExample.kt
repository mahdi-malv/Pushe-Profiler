package ir.malv.pusheprofiler

import android.content.Context
import androidx.work.OneTimeWorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class WorkExample(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        println("From work")
        return Result.success()
    }

}