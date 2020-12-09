package ir.malv.pusheprofiler

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkEx(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return Result.success()
    }
}