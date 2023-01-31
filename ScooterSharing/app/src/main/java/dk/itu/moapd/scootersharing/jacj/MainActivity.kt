package dk.itu.moapd.scootersharing.jacj

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    // A set of private constant used in this class.
    companion object {
        private val TAG = MainActivity::class.qualifiedName
    }

    // GUI variables.
    private lateinit var scooterName: EditText
    private lateinit var scooterLocation: EditText
    private lateinit var startRideButton: Button
    private lateinit var api_level : TextView
    private val scooter: Scooter = Scooter("","")

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Edit texts
        scooterName = findViewById(R.id.edit_text_name)
        scooterLocation = findViewById(R.id.edit_text_location)

        // Buttons.
        startRideButton = findViewById(R.id.start_ride_button)
        startRideButton.setOnClickListener {
            if(scooterName.text.isNotEmpty() && scooterLocation.text.isNotEmpty()) {
                // Update the object attributes.
                val name = scooterName.text.toString().trim()
                scooter.setName(name)
                val location = scooterLocation.text.toString().trim()
                scooter.setLocation(location)

                // Reset the text fields and update the UI
                showMessage()
            }
        }

        // API Level
        val version = Build.VERSION.SDK_INT
        api_level = findViewById(R.id.api_level)
        api_level.setText("API Level " + version);
    }

    private fun showMessage() {
        // Print a message in the 'Logcat' system.
        Log.d(TAG, scooter.toString())
    }
}