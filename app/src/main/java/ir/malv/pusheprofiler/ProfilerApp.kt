package ir.malv.pusheprofiler

import android.app.Application
//import co.pushe.plus.utils.log.Plog
import ir.malv.utils.Pulp

class ProfilerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Pulp.init(this).setDatabaseEnabled(true)
//        Plog.addHandler(ProfLogHandler())
    }
}