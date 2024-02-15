package ru.topbun.customviewyearandmonthpicker.screen

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.topbun.customviewyearandmonthpicker.Const
import ru.topbun.customviewyearandmonthpicker.Const.monthList
import ru.topbun.customviewyearandmonthpicker.Const.yearList
import ru.topbun.customviewyearandmonthpicker.R
import ru.topbun.customviewyearandmonthpicker.databinding.FragmentPickerDialogBinding
import ru.topbun.customviewyearandmonthpicker.screen.adapter.PickAdapter
import ru.topbun.customviewyearandmonthpicker.utils.getCurrentMonth
import ru.topbun.customviewyearandmonthpicker.utils.getCurrentYear
import ru.topbun.customviewyearandmonthpicker.utils.getTimeMillisFromMonthAndYear


class PickerDialogFragment : DialogFragment() {

    private var _binding: FragmentPickerDialogBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("binding == null")

    private val monthAdapter by lazy{ PickAdapter() }
    private val yearAdapter by lazy{ PickAdapter() }

    private var isOpenMonth = true
    private var choiceMonth = getCurrentMonth()
    private var choiceYear = getCurrentYear().toString()

    private var setOnConfirmClickListener: ((Long) -> Unit)? = null
    private var setOnDismissClickListener: (() -> Unit)? = null

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
        setViews()
    }

    private fun setViews(){
        setChoiceMonth(choiceMonth)
        setListenersInView()
        setAdapters()
    }

    private fun setAdapters(){
        setMonthAdapter()
        setYearAdapter()
    }

    private fun setMonthAdapter(){
        binding.rvMonth.adapter = monthAdapter
        monthAdapter.submitList(Const.monthList)
        monthAdapter.setItemClickListener = { id ->
            val updatedMonthList = monthList.map {
                if (it.id == id) {
                    setChoiceMonth(it.value)
                    it.copy(isEnabled = true)
                } else { it.copy(isEnabled = false) }
            }
            monthAdapter.submitList(updatedMonthList)
        }
    }

    private fun setYearAdapter(){
        binding.rvYear.adapter = yearAdapter
        yearAdapter.submitList(Const.yearList)
        yearAdapter.setItemClickListener = { id ->
            val updatedYearList = yearList.map {
                if (it.id == id) {
                    setChoiceYear(it.value)
                    it.copy(isEnabled = true)
                } else { it.copy(isEnabled = false) }
            }
            yearAdapter.submitList(updatedYearList)
        }
        binding.rvYear.scrollToPosition(yearList.size - 1)
    }

    fun setListenersInButton(
        confirmClickListener: ((Long) -> Unit),
        dismissClickListener: (() -> Unit)
    ){
        setOnConfirmClickListener = confirmClickListener
        setOnDismissClickListener = dismissClickListener
    }

    private fun setListenersInView(){
        with(binding){
            btnChoiceMonth.setOnClickListener {
                openMonthPicker()
            }
            btnChoiceYear.setOnClickListener {
                openYearPicker()
            }
            btnDismiss.setOnClickListener {
                setOnDismissClickListener?.invoke()
            }
            btnConfirm.setOnClickListener {
                val timeStamp = getTimeMillisFromMonthAndYear(choiceMonth, choiceYear)
                setOnConfirmClickListener?.invoke(timeStamp)
            }
        }
    }

    private fun setChoiceMonth(month: String){
        choiceMonth = month
        binding.btnChoiceMonth.text = choiceMonth
    }

    private fun setChoiceYear(year: String){
        choiceYear = year
        binding.btnChoiceYear.text = choiceYear
    }

    private fun openMonthPicker(){
        with(binding){
            isOpenMonth = true
            btnChoiceMonth.setBackgroundResource(R.drawable.background_button_filled)
            btnChoiceYear.setBackgroundResource(R.drawable.background_button_stroke)
            btnChoiceMonth.setTextColor(resources.getColor(R.color.white))
            btnChoiceYear.setTextColor(resources.getColor(R.color.black))
            rvMonth.visibility = View.VISIBLE
            rvYear.visibility = View.GONE
        }

    }

    private fun openYearPicker(){
        with(binding){
            isOpenMonth = false
            btnChoiceMonth.setBackgroundResource(R.drawable.background_button_stroke)
            btnChoiceYear.setBackgroundResource(R.drawable.background_button_filled)
            btnChoiceMonth.setTextColor(resources.getColor(R.color.black))
            btnChoiceYear.setTextColor(resources.getColor(R.color.white))
            rvMonth.visibility = View.GONE
            rvYear.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}