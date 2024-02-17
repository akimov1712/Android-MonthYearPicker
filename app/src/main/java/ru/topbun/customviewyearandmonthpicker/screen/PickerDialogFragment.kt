package ru.topbun.customviewyearandmonthpicker.screen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.topbun.customviewyearandmonthpicker.R
import ru.topbun.customviewyearandmonthpicker.databinding.FragmentPickerDialogBinding
import ru.topbun.customviewyearandmonthpicker.utils.formatTimeMillis
import ru.topbun.customviewyearandmonthpicker.view.BottomButtonAction


class PickerDialogFragment : Fragment() {

    private var _binding: FragmentPickerDialogBinding? = null
    private val binding get() = checkNotNull(_binding){"binding == null"}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPickerDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.monthYearPicker
            .setTitleText("MyApp")
            .setColorTitleText(Color.BLUE)
            .setTextSizeTitle(24)
            .setIsVisibleTitle(true)
            .setPositiveButtonText("Ok")
            .setNegativeButtonText("Cancel")
            .setColorPositiveButtonText(Color.YELLOW)
            .setColorNegativeButtonText(Color.RED)
            .setBackgroundPositiveButton(R.color.default_purple)
            .setBackgroundNegativeButton(R.color.default_purple)
            .setTintPositiveButton(Color.CYAN)
            .setTintNegativeButton(Color.DKGRAY)
            .setTextColorActiveButton(Color.BLACK)
            .setTextColorDisactiveButton(Color.BLACK)
            .setBackgroundActiveButton(R.drawable.background_button_filled)
            .setBackgroundDisactiveButton(Color.TRANSPARENT)
            .setTintActiveButton(R.color.default_purple)
            .setTintDisactiveButton(Color.WHITE)
            .setTextColorActiveItem(Color.WHITE)
            .setTextColorDisactiveItem(R.color.default_grey)
            .setBackgroundActiveItem(R.drawable.background_active_item)
            .setBackgroundDisactiveItem(R.drawable.background_button_stroke)
            .setListener { bottomAction, timeMillis ->
                when(bottomAction){
                    BottomButtonAction.POSITIVE -> {
                        Toast.makeText(requireContext(), formatTimeMillis(timeMillis), Toast.LENGTH_SHORT).show()
                    }
                    BottomButtonAction.NEGATIVE -> {
                        Toast.makeText(requireContext(), "выйти", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}