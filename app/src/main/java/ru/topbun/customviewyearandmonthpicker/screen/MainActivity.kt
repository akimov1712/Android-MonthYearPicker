package ru.topbun.customviewyearandmonthpicker.screen

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ru.topbun.customviewyearandmonthpicker.R
import ru.topbun.customviewyearandmonthpicker.databinding.ActivityMainBinding
import ru.topbun.customviewyearandmonthpicker.utils.formatTimeMillis

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        openDialog()
        binding.btn.setOnClickListener {
            openDialog()
        }
    }

    private fun openDialog(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val dialogPicker = PickerDialogFragment()
        dialogPicker.setListenersInButton({
            binding.btn.text = formatTimeMillis(it)
            dialogPicker.dismiss()
        },{
            dialogPicker.dismiss()
        })
        dialogPicker.show(fragmentTransaction, "dialogPicker")
    }

}