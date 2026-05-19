package org.iolet.messagesautoblocker

import android.Manifest.permission
import android.content.IntentFilter
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.provider.Telephony.Sms.Intents
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.IntRange
import androidx.annotation.NonNull
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.room.Room
import org.iolet.messagesautoblocker.util.AppDatabase
import org.iolet.messagesautoblocker.ui.components.App
import org.iolet.messagesautoblocker.ui.theme.MessagesAutoblockerTheme
import org.iolet.messagesautoblocker.util.SMSReceiver

class MainActivity : ComponentActivity() {
    lateinit var smsReceiver : SMSReceiver
    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getPermission(permission.RECEIVE_SMS, 0)

        smsReceiver = SMSReceiver()
        IntentFilter(Intents.SMS_RECEIVED_ACTION).also {
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
        super.onStop()
    }

    override fun onDestroy() {
        unregisterReceiver(smsReceiver)
        super.onDestroy()
    }

    private fun getPermission(@NonNull permission: String, @IntRange(from = 0L) code: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) == PERMISSION_GRANTED)
            return
        ActivityCompat.requestPermissions(this, arrayOf(permission), code)
    }
}