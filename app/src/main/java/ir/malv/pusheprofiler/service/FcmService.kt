package ir.malv.pusheprofiler.service

import android.content.Intent
import co.pushe.plus.Pushe
import co.pushe.plus.fcm.PusheFCM
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ir.malv.utils.Pulp
import java.lang.Exception

class FcmService : FirebaseMessagingService() {

    private val pusheFcm = Pushe.getPusheService(PusheFCM::class.java)?.fcmHandler

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Pulp.debug("Fcm", "Token received") {
            "Token" to p0
        }
        pusheFcm?.onNewToken(p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Pulp.debug("Fcm", "Fcm Message received") {
            "Message" to p0.data
            "Notification" to (p0.notification?.body?:"EMPTY")
        }
        pusheFcm?.onMessageReceived(p0)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Pulp.warn("Fcm", "Token was removed")
    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
        Pulp.debug("Fcm", "Message $p0 sent")
        pusheFcm?.onMessageSent(p0)
    }

    override fun onSendError(p0: String, p1: Exception) {
        super.onSendError(p0, p1)
        Pulp.error("Fcm", "Error sending $p0", p1)
        pusheFcm?.onSendError(p0, p1)
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
        Pulp.debug("Fcm", "messages were deleted")
        pusheFcm?.onDeletedMessages()
    }
}