package ru.topbun.customviewyearandmonthpicker.screen

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.topbun.customviewyearandmonthpicker.R
import ru.topbun.customviewyearandmonthpicker.databinding.ActivityMainBinding
import ru.topbun.customviewyearandmonthpicker.utils.formatTimeMillis
import ru.topbun.customviewyearandmonthpicker.view.BottomButtonAction

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Call a method that will show dialogFragment
        openDialog()
    }

    /*
        For testing, all you have to do is copy this method and paste your code.
        And anywhere in the program, call this method and check the
        MonthYearPickerDialog for operation
     */
    private fun openDialog(){
        // FragmentTransaction, we'll need to show dialogFragment
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // Create the dialog itself, then set it up and show it to the user
        val dialogPicker = PickerDialogFragment()

        /*
            Below are all the methods to set the values and listeners of our DialogPickerFragment.
            You don't have to call all the methods, you can call the methods you need, all the rest
            of the information will be set by default
         */

        dialogPicker
            .setTitleText("MyApp")
            .setColorTitleText(Color.BLACK)
            .setTextSizeTitle(28)
            .setIsVisibleTitle(true)
            .setPositiveButtonText("Confirm")
            .setNegativeButtonText("Dismiss")
            .setColorPositiveButtonText(Color.WHITE)
            .setColorNegativeButtonText(Color.BLACK)
            .setBackgroundPositiveButton(R.drawable.background_button_filled)
            .setBackgroundNegativeButton(R.drawable.background_button_stroke)
            .setTintPositiveButton(0) // 0 – do not install tint. The color is taken from the background
            .setTintNegativeButton(0) // 0 – do not install tint. The color is taken from the background
            .setTextColorActiveButton(Color.WHITE)
            .setTextColorDisactiveButton(Color.BLACK)
            .setBackgroundActiveButton(R.drawable.background_button_filled)
            .setBackgroundDisactiveButton(R.drawable.background_button_stroke)
            .setTintActiveButton(0) // 0 – do not install tint. The color is taken from the background
            .setTintDisactiveButton(0) // 0 – do not install tint. The color is taken from the background
            .setTextColorActiveItem(Color.WHITE)
            .setTextColorDisactiveItem(R.color.default_grey)
            .setBackgroundActiveItem(R.drawable.background_button_filled)
            .setBackgroundDisactiveItem(R.color.transparent)
            .setIsVisibleTitle(true)
            .setCornerRadius(25f)
            .setListener { bottomAction, timeMillis ->
                /*
                    timeMillis is the time in timeStamp
                    It is further convenient to work with him.
                    Using the 'Date()' class you can get all the information (time, day, month, year).

                    We just save the value in our code and work with it further
                */

                // Using bottomAction we determine which of the two buttons was pressed
                when (bottomAction) {

                    // The positiveButton was pressed
                    BottomButtonAction.POSITIVE -> {
                        // For example here when I click on the positive button
                        // I display the date in dd MMM yyyy HH:mm format using formatTimeMillis()
                        val formatTime = formatTimeMillis(timeMillis)
                        Toast.makeText(this, formatTime, Toast.LENGTH_SHORT).show()
                        dialogPicker.dismiss()
                    }

                    // The negativeButton was pressed
                    BottomButtonAction.NEGATIVE -> {

                        //Exit FragmentDialog
                        dialogPicker.dismiss()
                    }
                }
            }

        // Show the DialogFragment by specifying the tag you like
        dialogPicker.show(fragmentTransaction, "picker")
    }

}