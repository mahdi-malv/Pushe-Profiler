package ir.malv.pusheprofiler.service

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ir.malv.utils.Pulp
import java.lang.Exception

class FcmService : FirebaseMessagingService() {


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Pulp.debug("Fcm", "Token received") {
            "Token" to p0
        }
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Pulp.debug("Fcm", "Fcm Message received") {
            "Message" to p0.data
            "Notification" to (p0.notification?.body?:"EMPTY")
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Pulp.warn("Fcm", "Token was removed")
    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
        Pulp.debug("Fcm", "Message $p0 sent")
    }

    override fun onSendError(p0: String, p1: Exception) {
        super.onSendError(p0, p1)
        Pulp.error("Fcm", "Error sending $p0", p1)
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
        Pulp.debug("Fcm", "A message was deleted")
    }
}