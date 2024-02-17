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
import ru.topbun.customviewyearandmonthpicker.Const
import ru.topbun.customviewyearandmonthpicker.R
import ru.topbun.customviewyearandmonthpicker.databinding.MonthYearPickerBinding
import ru.topbun.customviewyearandmonthpicker.screen.adapter.PickAdapter
import ru.topbun.customviewyearandmonthpicker.utils.getCurrentMonth
import ru.topbun.customviewyearandmonthpicker.utils.getCurrentYear
import ru.topbun.customviewyearandmonthpicker.utils.getTimeMillisFromMonthAndYear

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

    private var setOnConfirmClickListener: ((Long) -> Unit)? = null
    private var setOnDismissClickListener: (() -> Unit)? = null

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

    fun setListenersInButton(
        confirmClickListener: ((Long) -> Unit),
        dismissClickListener: (() -> Unit)
    ) {
        setOnConfirmClickListener = confirmClickListener
        setOnDismissClickListener = dismissClickListener
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
                setOnDismissClickListener?.invoke()
            }
            btnPositive.setOnClickListener {
                val timeStamp = getTimeMillisFromMonthAndYear(choiceMonth, choiceYear)
                setOnConfirmClickListener?.invoke(timeStamp)
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

    private fun MonthYearPickerBinding.setupRecyclerItem(
        typedArray: TypedArray
    ){
        var textColorActiveItemAttrs = typedArray.getColor(
            R.styleable.YearMonthPicker_colorTextActiveItem,
            Color.WHITE
        )
        textColorActiveItem = textColorActiveItemAttrs

        var textColorDisactiveItemAttrs = typedArray.getColor(
            R.styleable.YearMonthPicker_colorTextDisactiveItem,
            context.getColor(R.color.default_grey)
        )
        textColorDisactiveItem = textColorDisactiveItemAttrs

        var backgroundActiveItemAttrs = typedArray.getResourceId(
            R.styleable.YearMonthPicker_backgroundActiveItem,
            R.drawable.background_button_filled
        )
        backgroundActiveItem = backgroundActiveItemAttrs


        var backgroundDisactiveItemAttrs = typedArray.getResourceId(
            R.styleable.YearMonthPicker_backgroundDisactiveItem,
            R.color.transparent
        )
        backgroundDisactiveItem = backgroundDisactiveItemAttrs
    }

    private fun MonthYearPickerBinding.setupToggleButtonsBackground(
        typedArray: TypedArray
    ) {
        val bgActiveButtonAttr = typedArray.getResourceId(
            R.styleable.YearMonthPicker_backgroundActiveButton,
            R.drawable.background_button_filled
        )
        bgActiveButton = bgActiveButtonAttr
        btnChoiceMonth.setBackgroundResource(bgActiveButton)

        val bgDisactiveButtonAttr = typedArray.getResourceId(
            R.styleable.YearMonthPicker_backgroundDisactiveButton,
            R.drawable.background_button_stroke
        )
        bgDisactiveButton = bgDisactiveButtonAttr
        btnChoiceYear.setBackgroundResource(bgDisactiveButton)

        val tintActiveButtonAttr = typedArray.getColor(
            R.styleable.YearMonthPicker_backgroundTintActiveButton,
            0
        )
        if (tintActiveButtonAttr != 0) {
            bgTintActiveButton = tintActiveButtonAttr
            btnChoiceMonth.backgroundTintList = ColorStateList.valueOf(bgTintActiveButton)
        }
        val tintDisactiveButtonAttr = typedArray.getColor(
            R.styleable.YearMonthPicker_backgroundTintDisactiveButton,
            0
        )
        if (tintDisactiveButtonAttr != 0) {
            bgTintDisactiveButton = tintDisactiveButtonAttr
            btnChoiceYear.backgroundTintList = ColorStateList.valueOf(bgTintDisactiveButton)
        }
    }

    private fun MonthYearPickerBinding.setupToggleButtonsText(
        typedArray: TypedArray
    ) {
        val textColorActiveButtonAttr =
            typedArray.getColor(R.styleable.YearMonthPicker_colorTextActiveButton, Color.WHITE)
        textColorActiveButton = textColorActiveButtonAttr
        btnChoiceMonth.setTextColor(ColorStateList.valueOf(textColorActiveButton))

        val textColorDisactiveButtonAttr =
            typedArray.getColor(R.styleable.YearMonthPicker_colorTextDisactiveButton, Color.BLACK)
        textColorDisactiveButton = textColorDisactiveButtonAttr
        btnChoiceYear.setTextColor(ColorStateList.valueOf(textColorDisactiveButton))
    }

    private fun MonthYearPickerBinding.setupBottomButtonsBackground(
        typedArray: TypedArray
    ) {
        val bgPositiveButton = typedArray.getResourceId(
            R.styleable.YearMonthPicker_backgroundPositiveButton,
            R.drawable.background_button_filled
        )
        btnPositive.setBackgroundResource(bgPositiveButton)

        val bgNegativeButton = typedArray.getResourceId(
            R.styleable.YearMonthPicker_backgroundNegativeButton,
            R.drawable.background_button_stroke
        )
        btnNegative.setBackgroundResource(bgNegativeButton)

        val tintPositiveButton = typedArray.getColor(
            R.styleable.YearMonthPicker_backgroundTintPositiveButton,
            0
        )
        if (tintPositiveButton != 0) {
            btnPositive.backgroundTintList = ColorStateList.valueOf(tintPositiveButton)
        }
        val tintNegativeButton = typedArray.getColor(
            R.styleable.YearMonthPicker_backgroundTintNegativeButton,
            0
        )
        if (tintNegativeButton != 0) {
            btnNegative.backgroundTintList = ColorStateList.valueOf(tintNegativeButton)
        }
    }

    private fun MonthYearPickerBinding.setupBottomButtonsText(
        typedArray: TypedArray
    ) {
        val textPositiveButton =
            typedArray.getString(R.styleable.YearMonthPicker_textPositiveButton) ?: "Confirm"
        btnPositive.text = textPositiveButton

        val textNegativeButton =
            typedArray.getString(R.styleable.YearMonthPicker_textNegativeButton) ?: "Dismiss"
        btnNegative.text = textNegativeButton

        val textColorPositiveButton =
            typedArray.getColor(R.styleable.YearMonthPicker_colorTextPositiveButton, Color.WHITE)
        btnPositive.setTextColor(ColorStateList.valueOf(textColorPositiveButton))

        val textColorNegativeButton =
            typedArray.getColor(R.styleable.YearMonthPicker_colorTextNegativeButton, Color.BLACK)
        btnNegative.setTextColor(ColorStateList.valueOf(textColorNegativeButton))
    }

    private fun MonthYearPickerBinding.setupTitle(
        typedArray: TypedArray
    ) {
        val textTitle = typedArray.getString(R.styleable.YearMonthPicker_textTitle)
        tvTitle.text = textTitle ?: "Month and Year Picker"

        val colorTitle = typedArray.getColor(R.styleable.YearMonthPicker_colorTitle, Color.BLACK)
        tvTitle.setTextColor(colorTitle)

        val textSizeTitle =
            typedArray.getDimension(R.styleable.YearMonthPicker_textSizeTitle, 28f)
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeTitle)
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        val desiredWidth = 330
//
//        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
//        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
//
//        val height = MeasureSpec.getSize(heightMeasureSpec)
//        val width = when (widthMode) {
//            MeasureSpec.EXACTLY -> widthSize
//            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize)
//            else -> desiredWidth
//        }
//
//        setMeasuredDimension(width, height)
//    }
}