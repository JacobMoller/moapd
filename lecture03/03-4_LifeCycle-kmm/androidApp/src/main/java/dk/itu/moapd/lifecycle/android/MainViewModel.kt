package dk.itu.moapd.lifecycle.android

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    /**
     * The current text showing in the main activity.
     */
    private var text = MutableLiveData<String>()

    /**
     * A `LiveData` which publicly exposes any update
     * in the UI TextView
     */
    val textState: LiveData<String>
        get() = text

    fun onTextChanged(text: String) {
        this.text.value = text
        Log.d("ViewModel", "Changed ViewModel")
    }

    /**
     * The current status of the UI checkbox.
     */
    private var checked = false
    /**
     * This method changes the boolean status of
     * the UI checkbox.
     */
    fun toggleChecked() {
        checked = !checked
    }
    fun isChecked(): Boolean {
        return checked
    }
}