//package ir.malv.pusheprofiler
//
//import co.pushe.plus.utils.log.LogHandler
//import co.pushe.plus.utils.log.LogLevel
//import co.pushe.plus.utils.log.Plogger
//import ir.malv.utils.Pulp
//
//class ProfLogHandler : LogHandler() {
//    override val id: String = "Profiler"
//
//    override fun onLog(logItem: Plogger.LogItem?) {
//        if(logItem == null) return
//        when (logItem.level) {
//            LogLevel.DEBUG, LogLevel.TRACE -> {
//                Pulp.debug(
//                    tags = logItem.tags.toTypedArray(),
//                    message = logItem.message,
//                    error = logItem.throwable
//                ) {
//                    logItem.logData.forEach {
//                        it.key to (it.value?.toString() ?:"")
//                    }
//                }
//            }
//            LogLevel.INFO -> {
//                Pulp.info(
//                    tags = logItem.tags.toTypedArray(),
//                    message = logItem.message,
//                    error = logItem.throwable
//                ) {
//                    logItem.logData.forEach {
//                        it.key to (it.value?.toString() ?:"")
//                    }
//                }
//            }
//            LogLevel.WARN -> {
//                Pulp.warn(
//                    tags = logItem.tags.toTypedArray(),
//                    message = logItem.message,
//                    error = logItem.throwable
//                ) {
//                    logItem.logData.forEach {
//                        it.key to (it.value?.toString() ?:"")
//                    }
//                }
//            }
//            LogLevel.ERROR -> {
//                Pulp.error(
//                    tags = logItem.tags.toTypedArray(),
//                    message = logItem.message,
//                    error = logItem.throwable
//                ) {
//                    logItem.logData.forEach {
//                        it.key to (it.value?.toString() ?:"")
//                    }
//                }
//            }
//            LogLevel.WTF -> {
//                Pulp.wtf(
//                    tags = logItem.tags.toTypedArray(),
//                    message = logItem.message,
//                    error = logItem.throwable
//                ) {
//                    logItem.logData.forEach {
//                        it.key to (it.value?.toString() ?:"")
//                    }
//                }
//            }
//        }
//    }
//
//}