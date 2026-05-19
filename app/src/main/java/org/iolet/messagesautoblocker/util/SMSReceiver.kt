package org.iolet.messagesautoblocker.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import kotlin.collections.iterator

class SMSReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val chunks = Telephony.Sms.Intents.getMessagesFromIntent(intent)

        val msgs = HashMap<String, StringBuilder>()
        for (chunk in chunks) {
            if (chunk == null) continue
            val sender = chunk.displayOriginatingAddress
            if (!msgs.contains(sender))
                msgs[sender] = StringBuilder()
            msgs[sender]!!.append(chunk.displayMessageBody)
        }

        for (msg in msgs) {
            Log.i(SMSReceiver::class.java.simpleName, msg.key + ": \"" + msg.value + "\"")
//            Toast.makeText(context, msg.key + ": " + msg.value, Toast.LENGTH_LONG).show()
        }
    }
}