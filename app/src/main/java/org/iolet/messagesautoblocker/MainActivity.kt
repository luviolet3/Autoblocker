package org.iolet.messagesautoblocker

import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.room.Room
import org.iolet.messagesautoblocker.util.AppDatabase
import org.iolet.messagesautoblocker.ui.components.App
import org.iolet.messagesautoblocker.ui.theme.MessagesAutoblockerTheme

class MainActivity : ComponentActivity() {
    lateinit var smsReceiver : SMSReceiver
    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        smsReceiver = SMSReceiver()
        IntentFilter("android.provider.Telephony.SMS_RECEIVED").also {
            registerReceiver(smsReceiver, it)
        }

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        ).build()

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            MessagesAutoblockerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    App(db.ruleDao())
                }
            }
        }
    }

    override fun onStop() {
        unregisterReceiver(smsReceiver)
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}