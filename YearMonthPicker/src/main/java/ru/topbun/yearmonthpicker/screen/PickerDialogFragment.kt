package ru.topbun.yearmonthpicker.screen

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.topbun.yearmonthpicker.R
import ru.topbun.yearmonthpicker.databinding.FragmentPickerDialogBinding
import ru.topbun.yearmonthpicker.view.OnBottomButtonClickListener


class PickerDialogFragment : DialogFragment() {

    private var _binding: FragmentPickerDialogBinding? = null
    private val binding get() = checkNotNull(_binding) { "binding == null" }

    private var listener: OnBottomButtonClickListener? = null
    private var titleText: String? = null
    private var colorTitleText: Int = Color.BLACK
    private var textSizeTitle: Number = 28
    private var isVisibleTitle: Boolean = true
    private var positiveButtonText: String? = "Confirm"
    private var negativeButtonText: String? = "Dismiss"
    private var colorPositiveButtonText: Int = Color.WHITE
    private var colorNegativeButtonText: Int = Color.BLACK
    private var backgroundPositiveButton: Int = R.drawable.background_button_filled
    private var backgroundNegativeButton: Int = R.drawable.background_button_stroke
    private var tintPositiveButton: Int = 0
    private var tintNegativeButton: Int = 0
    private var textColorActiveButton: Int = Color.WHITE
    private var textColorDisactiveButton: Int = Color.BLACK
    private var backgroundActiveButton: Int = R.drawable.background_button_filled
    private var backgroundDisactiveButton: Int = R.drawable.background_button_stroke
    private var tintActiveButton: Int = 0
    private var tintDisactiveButton: Int = 0
    private var textColorActiveItem: Int = Color.WHITE
    private var textColorDisactiveItem: Int = R.color.default_grey
    private var backgroundActiveItem: Int = R.drawable.background_button_filled
    private var backgroundDisactiveItem: Int = R.color.transparent
    private var cornerRadius: Float = 25f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPickerDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applySettings()
    }

    private fun applySettings() {
        with(binding) {
            monthYearPicker.apply {
                setTitleText(titleText)
                setColorTitleText(colorTitleText)
                setTextSizeTitle(textSizeTitle)
                setIsVisibleTitle(isVisibleTitle)
                setPositiveButtonText(positiveButtonText)
                setNegativeButtonText(negativeButtonText)
                setColorPositiveButtonText(colorPositiveButtonText)
                setColorNegativeButtonText(colorNegativeButtonText)
                setBackgroundPositiveButton(backgroundPositiveButton)
                setBackgroundNegativeButton(backgroundNegativeButton)
                setTintPositiveButton(tintPositiveButton)
                setTintNegativeButton(tintNegativeButton)
                setTextColorActiveButton(textColorActiveButton)
                setTextColorDisactiveButton(textColorDisactiveButton)
                setBackgroundActiveButton(backgroundActiveButton)
                setBackgroundDisactiveButton(backgroundDisactiveButton)
                setTintActiveButton(tintActiveButton)
                setTintDisactiveButton(tintDisactiveButton)
                setTextColorActiveItem(textColorActiveItem)
                setTextColorDisactiveItem(textColorDisactiveItem)
                setBackgroundActiveItem(backgroundActiveItem)
                setBackgroundDisactiveItem(backgroundDisactiveItem)
                setListener(listener)
            }
            cardView.radius = cornerRadius
        }
    }

    fun setListener(listener: OnBottomButtonClickListener?) : PickerDialogFragment {
        return this.apply { this.listener = listener }
    }

    fun setTitleText(text: String?): PickerDialogFragment {
        return this.apply { titleText = text }
    }

    fun setColorTitleText(color: Int): PickerDialogFragment {
        return this.apply { colorTitleText = color }
    }

    fun setTextSizeTitle(sizeSp: Number): PickerDialogFragment {
        return this.apply { textSizeTitle = sizeSp }
    }

    fun setIsVisibleTitle(isShowTitle: Boolean): PickerDialogFragment {
        return this.apply { isVisibleTitle = isShowTitle }
    }

    fun setPositiveButtonText(text: String?): PickerDialogFragment {
        return this.apply { positiveButtonText = text }
    }

    fun setNegativeButtonText(text: String?): PickerDialogFragment {
        return this.apply { negativeButtonText = text }
    }

    fun setColorPositiveButtonText(color: Int): PickerDialogFragment {
        return this.apply { colorPositiveButtonText = color }
    }

    fun setColorNegativeButtonText(color: Int): PickerDialogFragment {
        return this.apply { colorNegativeButtonText = color }
    }

    fun setBackgroundPositiveButton(resource: Int): PickerDialogFragment {
        return this.apply { backgroundPositiveButton = resource }
    }

    fun setBackgroundNegativeButton(resource: Int): PickerDialogFragment {
        return this.apply { backgroundNegativeButton = resource }
    }

    fun setTintPositiveButton(color: Int): PickerDialogFragment {
        return this.apply { tintPositiveButton = color }
    }

    fun setTintNegativeButton(color: Int): PickerDialogFragment {
        return this.apply { tintNegativeButton = color }
    }

    fun setTextColorActiveButton(color: Int): PickerDialogFragment {
        return this.apply { textColorActiveButton = color }
    }

    fun setTextColorDisactiveButton(color: Int): PickerDialogFragment {
        return this.apply { textColorDisactiveButton = color }
    }

    fun setBackgroundActiveButton(resource: Int): PickerDialogFragment {
        return this.apply { backgroundActiveButton = resource }
    }

    fun setBackgroundDisactiveButton(resource: Int): PickerDialogFragment {
        return this.apply { backgroundDisactiveButton = resource }
    }

    fun setTintActiveButton(resource: Int): PickerDialogFragment {
        return this.apply { tintActiveButton = resource }
    }

    fun setTintDisactiveButton(resource: Int): PickerDialogFragment {
        return this.apply { tintDisactiveButton = resource }
    }

    fun setTextColorActiveItem(color: Int): PickerDialogFragment {
        return this.apply { textColorActiveItem = color }
    }

    fun setTextColorDisactiveItem(color: Int): PickerDialogFragment {
        return this.apply { textColorDisactiveItem = color }
    }

    fun setBackgroundActiveItem(resource: Int): PickerDialogFragment {
        return this.apply { backgroundActiveItem = resource }
    }

    fun setBackgroundDisactiveItem(resource: Int): PickerDialogFragment {
        return this.apply { backgroundDisactiveItem = resource }
    }

    fun setCornerRadius(radius: Float): PickerDialogFragment {
        return this.apply { cornerRadius = radius }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}