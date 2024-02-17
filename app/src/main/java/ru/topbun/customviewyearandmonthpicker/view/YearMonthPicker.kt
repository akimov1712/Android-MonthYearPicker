package ru.topbun.customviewyearandmonthpicker.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import ru.topbun.customviewyearandmonthpicker.Const
import ru.topbun.customviewyearandmonthpicker.R
import ru.topbun.customviewyearandmonthpicker.databinding.MonthYearPickerBinding
import ru.topbun.customviewyearandmonthpicker.screen.adapter.PickAdapter
import ru.topbun.customviewyearandmonthpicker.utils.getCurrentMonth
import ru.topbun.customviewyearandmonthpicker.utils.getCurrentYear
import ru.topbun.customviewyearandmonthpicker.utils.getTimeMillisFromMonthAndYear
import ru.topbun.customviewyearandmonthpicker.utils.pxToSp
import ru.topbun.customviewyearandmonthpicker.utils.spToPp

enum class BottomButtonAction{
    POSITIVE, NEGATIVE
}

typealias OnBottomButtonClickListener = (bottomAction: BottomButtonAction, timeMillis: Long) -> Unit

class YearMonthPicker @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: MonthYearPickerBinding

    private var textColorActiveButton = Color.WHITE
    private var textColorDisactiveButton = Color.BLACK
    private var bgActiveButton = R.drawable.background_button_filled
    private var bgDisactiveButton = R.drawable.background_button_stroke
    private var bgTintActiveButton = 0
    private var bgTintDisactiveButton = 0

    private var backgroundActiveItem = R.drawable.background_button_filled
    private var backgroundDisactiveItem = R.color.transparent
    private var textColorActiveItem = Color.WHITE
    private var textColorDisactiveItem = R.color.default_grey

    private val monthAdapter by lazy {
        PickAdapter(
            backgroundActiveItem,
            backgroundDisactiveItem,
            textColorActiveItem,
            textColorDisactiveItem
        )
    }

    private val yearAdapter by lazy {
        PickAdapter(
            backgroundActiveItem,
            backgroundDisactiveItem,
            textColorActiveItem,
            textColorDisactiveItem
        )
    }

    private var choiceMonth = getCurrentMonth()
    private var choiceYear = getCurrentYear().toString()

    private var listener: OnBottomButtonClickListener? = null

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.month_year_picker, this, true)
        binding = MonthYearPickerBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
        setViews()
    }

    private fun setViews() {
        setChoiceMonth(choiceMonth)
        setListenersInView()
        setAdapters()
    }

    private fun setAdapters() {
        setMonthAdapter()
        setYearAdapter()
    }

    private fun setMonthAdapter() {
        binding.rvMonth.adapter = monthAdapter
        monthAdapter.submitList(Const.monthList)
        monthAdapter.setItemClickListener = { id ->
            val updatedMonthList = Const.monthList.map {
                if (it.id == id) {
                    setChoiceMonth(it.value)
                    it.copy(isEnabled = true)
                } else {
                    it.copy(isEnabled = false)
                }
            }
            monthAdapter.submitList(updatedMonthList)
        }
    }

    private fun setYearAdapter() {
        binding.rvYear.adapter = yearAdapter
        yearAdapter.submitList(Const.yearList)
        yearAdapter.setItemClickListener = { id ->
            val updatedYearList = Const.yearList.map {
                if (it.id == id) {
                    setChoiceYear(it.value)
                    it.copy(isEnabled = true)
                } else {
                    it.copy(isEnabled = false)
                }
            }
            yearAdapter.submitList(updatedYearList)
        }
        binding.rvYear.scrollToPosition(Const.yearList.size - 1)
    }

    private fun setListenersInView() {
        with(binding) {
            btnChoiceMonth.setOnClickListener {
                openMonthPicker()
            }
            btnChoiceYear.setOnClickListener {
                openYearPicker()
            }
            btnNegative.setOnClickListener {
                val timeStamp = getTimeMillisFromMonthAndYear(choiceMonth, choiceYear)
                listener?.invoke(BottomButtonAction.NEGATIVE, timeStamp)
            }
            btnPositive.setOnClickListener {
                val timeStamp = getTimeMillisFromMonthAndYear(choiceMonth, choiceYear)
                listener?.invoke(BottomButtonAction.POSITIVE, timeStamp)
            }
        }
    }

    private fun setChoiceMonth(month: String) {
        choiceMonth = month
        binding.btnChoiceMonth.text = choiceMonth
    }

    private fun setChoiceYear(year: String) {
        choiceYear = year
        binding.btnChoiceYear.text = choiceYear
    }

    private fun openMonthPicker() {
        with(binding) {
            btnChoiceMonth.setBackgroundResource(bgActiveButton)
            btnChoiceYear.setBackgroundResource(bgDisactiveButton)
            btnChoiceMonth.setTextColor(textColorActiveButton)
            btnChoiceYear.setTextColor(textColorDisactiveButton)
            if (bgTintActiveButton != 0) {
                btnChoiceMonth.backgroundTintList = ColorStateList.valueOf(bgTintActiveButton)
            }
            if (bgTintDisactiveButton != 0) {
                btnChoiceYear.backgroundTintList = ColorStateList.valueOf(bgTintDisactiveButton)
            }
            rvMonth.visibility = View.VISIBLE
            rvYear.visibility = View.GONE
        }

    }

    private fun openYearPicker() {
        with(binding) {
            btnChoiceMonth.setBackgroundResource(bgDisactiveButton)
            btnChoiceYear.setBackgroundResource(bgActiveButton)
            btnChoiceMonth.setTextColor(textColorDisactiveButton)
            btnChoiceYear.setTextColor(textColorActiveButton)
            if (bgTintActiveButton != 0) {
                btnChoiceYear.backgroundTintList = ColorStateList.valueOf(bgTintActiveButton)
            }
            if (bgTintDisactiveButton != 0) {
                btnChoiceMonth.backgroundTintList = ColorStateList.valueOf(bgTintDisactiveButton)
            }
            rvMonth.visibility = View.GONE
            rvYear.visibility = View.VISIBLE
        }
    }


    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.YearMonthPicker,
            defStyleAttr,
            defStyleRes
        )
        with(binding) {
            setupTitle(typedArray)
            setupBottomButtonsText(typedArray)
            setupBottomButtonsBackground(typedArray)
            setupToggleButtonsText(typedArray)
            setupToggleButtonsBackground(typedArray)
            setupRecyclerItem(typedArray)
        }
        typedArray.recycle()
    }

    private fun setupRecyclerItem(
        typedArray: TypedArray
    ){
        val textColorActiveItemAttrs = typedArray.getColor(
            R.styleable.YearMonthPicker_colorTextActiveItem,
            Color.WHITE
        )
        setTextColorActiveItem(textColorActiveItemAttrs)

        val textColorDisactiveItemAttrs = typedArray.getColor(
            R.styleable.YearMonthPicker_colorTextDisactiveItem,
            context.getColor(R.color.default_grey)
        )
        setTextColorDisactiveItem(textColorDisactiveItemAttrs)

        val backgroundActiveItemAttrs = typedArray.getResourceId(
            R.styleable.YearMonthPicker_backgroundActiveItem,
            R.drawable.background_button_filled
        )
        setBackgroundActiveItem(backgroundActiveItemAttrs)

        val backgroundDisactiveItemAttrs = typedArray.getResourceId(
            R.styleable.YearMonthPicker_backgroundDisactiveItem,
            R.color.transparent
        )
        setBackgroundDisactiveItem(backgroundDisactiveItemAttrs)
    }

    private fun setupToggleButtonsBackground(
        typedArray: TypedArray
    ) {
        val bgActiveButtonAttr = typedArray.getResourceId(
            R.styleable.YearMonthPicker_backgroundActiveButton,
            R.drawable.background_button_filled
        )
        setBackgroundActiveButton(bgActiveButtonAttr)

        val bgDisactiveButtonAttr = typedArray.getResourceId(
            R.styleable.YearMonthPicker_backgroundDisactiveButton,
            R.drawable.background_button_stroke
        )
        setBackgroundDisactiveButton(bgDisactiveButtonAttr)

        val tintActiveButtonAttr = typedArray.getColor(
            R.styleable.YearMonthPicker_backgroundTintActiveButton,
            0
        )
        setTintActiveButton(tintActiveButtonAttr)

        val tintDisactiveButtonAttr = typedArray.getColor(
            R.styleable.YearMonthPicker_backgroundTintDisactiveButton,
            0
        )
        setTintDisactiveButton(tintDisactiveButtonAttr)
    }

    private fun setupToggleButtonsText(
        typedArray: TypedArray
    ) {
        val textColorActiveButtonAttr =
            typedArray.getColor(R.styleable.YearMonthPicker_colorTextActiveButton, Color.WHITE)
        setTextColorActiveButton(textColorActiveButtonAttr)

        val textColorDisactiveButtonAttr =
            typedArray.getColor(R.styleable.YearMonthPicker_colorTextDisactiveButton, Color.BLACK)
        setTextColorDisactiveButton(textColorDisactiveButtonAttr)
    }

    private fun setupBottomButtonsBackground(
        typedArray: TypedArray
    ) {
        val bgPositiveButton = typedArray.getResourceId(
            R.styleable.YearMonthPicker_backgroundPositiveButton,
            R.drawable.background_button_filled
        )
        setBackgroundPositiveButton(bgPositiveButton)

        val bgNegativeButton = typedArray.getResourceId(
            R.styleable.YearMonthPicker_backgroundNegativeButton,
            R.drawable.background_button_stroke
        )
        setBackgroundNegativeButton(bgNegativeButton)

        val tintPositiveButton = typedArray.getColor(
            R.styleable.YearMonthPicker_backgroundTintPositiveButton,
            0
        )
        setTintPositiveButton(tintPositiveButton)

        val tintNegativeButton = typedArray.getColor(
            R.styleable.YearMonthPicker_backgroundTintNegativeButton,
            0
        )
        setTintNegativeButton(tintNegativeButton)
    }

    private fun setupBottomButtonsText(
        typedArray: TypedArray
    ) {
        val textPositiveButton =
            typedArray.getString(R.styleable.YearMonthPicker_textPositiveButton)
        setPositiveButtonText(textPositiveButton)

        val textNegativeButton =
            typedArray.getString(R.styleable.YearMonthPicker_textNegativeButton)
        setNegativeButtonText(textNegativeButton)

        val textColorPositiveButton =
            typedArray.getColor(R.styleable.YearMonthPicker_colorTextPositiveButton, Color.WHITE)
        setColorPositiveButtonText(textColorPositiveButton)

        val textColorNegativeButton =
            typedArray.getColor(R.styleable.YearMonthPicker_colorTextNegativeButton, Color.BLACK)
        setColorNegativeButtonText(textColorNegativeButton)
    }

    private fun setupTitle(
        typedArray: TypedArray
    ) {
        val textTitle = typedArray.getString(R.styleable.YearMonthPicker_textTitle)
        setTitleText(textTitle)

        val colorTitle = typedArray.getColor(R.styleable.YearMonthPicker_colorTitle, Color.BLACK)
        setColorTitleText(colorTitle)

        val textSizeTitle =
            typedArray.getDimensionPixelSize(R.styleable.YearMonthPicker_textSizeTitle,
                28).spToPp(context)
        setTextSizeTitle(textSizeTitle)

        val isShowTitle = typedArray.getBoolean(R.styleable.YearMonthPicker_isShowTitle, true)
        setIsVisibleTitle(isShowTitle)
    }

    fun setListener(listener: OnBottomButtonClickListener?) : YearMonthPicker{
        return this.apply { this.listener = listener }
    }

    fun setTitleText(text: String?): YearMonthPicker{
        return this.apply { binding.tvTitle.text = text ?: "Month and Year Picker" }
    }

    fun setColorTitleText(color: Int): YearMonthPicker{
        return this.apply { binding.tvTitle.setTextColor(color)}
    }

    fun setTextSizeTitle(sizeSp: Number): YearMonthPicker{
        return this.apply { binding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeSp.pxToSp(context)) }
    }

    fun setIsVisibleTitle(isShowTitle: Boolean): YearMonthPicker{
        return this.apply { binding.tvTitle.isVisible = isShowTitle }
    }

    fun setPositiveButtonText(text: String?): YearMonthPicker{
        return this.apply { binding.btnPositive.text = text ?: "Confirm" }
    }

    fun setNegativeButtonText(text: String?): YearMonthPicker{
        return this.apply { binding.btnNegative.text = text ?: "Dismiss" }
    }

    fun setColorPositiveButtonText(color:Int): YearMonthPicker{
        return this.apply { binding.btnPositive.setTextColor(color) }
    }

    fun setColorNegativeButtonText(color:Int): YearMonthPicker{
        return this.apply { binding.btnNegative.setTextColor(color) }
    }

    fun setBackgroundPositiveButton(resource: Int): YearMonthPicker{
        return this.apply { binding.btnPositive.setBackgroundResource(resource) }
    }

    fun setBackgroundNegativeButton(resource: Int): YearMonthPicker{
        return this.apply { binding.btnNegative.setBackgroundResource(resource) }
    }

    fun setTintPositiveButton(color: Int): YearMonthPicker{
        return this.apply {
            if (color != 0) {
                binding.btnPositive.backgroundTintList = ColorStateList.valueOf(color)
            }
        }
    }

    fun setTintNegativeButton(color: Int): YearMonthPicker{
        return this.apply {
            if (color != 0) {
                binding.btnNegative.backgroundTintList = ColorStateList.valueOf(color)
            }
        }
    }

    fun setTextColorActiveButton(color: Int):YearMonthPicker{
        return this.apply {
            textColorActiveButton = color
            binding.btnChoiceMonth.setTextColor(ColorStateList.valueOf(textColorActiveButton))
        }
    }

    fun setTextColorDisactiveButton(color: Int):YearMonthPicker{
        return this.apply {
            textColorDisactiveButton = color
            binding.btnChoiceYear.setTextColor(ColorStateList.valueOf(textColorDisactiveButton))
        }
    }

    fun setBackgroundActiveButton(resource: Int): YearMonthPicker{
        return this.apply {
            bgActiveButton = resource
            binding.btnChoiceMonth.setBackgroundResource(bgActiveButton)
        }
    }

    fun setBackgroundDisactiveButton(resource: Int): YearMonthPicker{
        return this.apply {
            bgDisactiveButton = resource
            binding.btnChoiceYear.setBackgroundResource(bgDisactiveButton)
        }
    }

    fun setTintActiveButton(resource: Int): YearMonthPicker{
        return this.apply {
            if (resource != 0) {
                bgTintActiveButton = resource
                binding.btnChoiceMonth.backgroundTintList = ColorStateList.valueOf(bgTintActiveButton)
            }
        }
    }

    fun setTintDisactiveButton(resource: Int): YearMonthPicker{
        return this.apply {
            if (resource != 0) {
                bgTintDisactiveButton = resource
                binding.btnChoiceYear.backgroundTintList = ColorStateList.valueOf(bgTintDisactiveButton)
            }
        }
    }

    fun setTextColorActiveItem(color: Int): YearMonthPicker{
        return this.apply {
            textColorActiveItem = color
            resetTextColorActiveItemInAdapters()
        }
    }

    fun setTextColorDisactiveItem(color: Int): YearMonthPicker{
        return this.apply {
            textColorDisactiveItem = color
            resetTextColorDisactiveItemInAdapters()
        }
    }

    fun setBackgroundActiveItem(resource: Int): YearMonthPicker{
        return this.apply {
            backgroundActiveItem = resource
            resetBackgroundActiveItemInAdapters()
        }
    }

    fun setBackgroundDisactiveItem(resource: Int): YearMonthPicker{
        return this.apply {
            backgroundDisactiveItem = resource
            resetBackgroundDisactiveItemInAdapters()
        }
    }


    private fun resetBackgroundActiveItemInAdapters(){
        monthAdapter.backgroundActiveItem = backgroundActiveItem
        yearAdapter.backgroundActiveItem = backgroundActiveItem
    }

    private fun resetBackgroundDisactiveItemInAdapters(){
        monthAdapter.backgroundDisactiveItem = backgroundDisactiveItem
        yearAdapter.backgroundDisactiveItem = backgroundDisactiveItem
    }

    private fun resetTextColorActiveItemInAdapters(){
        monthAdapter.textColorActiveItem = textColorActiveItem
        yearAdapter.textColorActiveItem = textColorActiveItem
    }

    private fun resetTextColorDisactiveItemInAdapters(){
        monthAdapter.textColorDisactiveItem = textColorDisactiveItem
        yearAdapter.textColorDisactiveItem = textColorDisactiveItem
    }

}