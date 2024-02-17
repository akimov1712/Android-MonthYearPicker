package ru.topbun.customviewyearandmonthpicker.screen

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import ru.topbun.customviewyearandmonthpicker.Const
import ru.topbun.customviewyearandmonthpicker.Const.monthList
import ru.topbun.customviewyearandmonthpicker.Const.yearList
import ru.topbun.customviewyearandmonthpicker.R
import ru.topbun.customviewyearandmonthpicker.databinding.FragmentPickerDialogBinding
import ru.topbun.customviewyearandmonthpicker.screen.adapter.PickAdapter
import ru.topbun.customviewyearandmonthpicker.utils.getCurrentMonth
import ru.topbun.customviewyearandmonthpicker.utils.getCurrentYear
import ru.topbun.customviewyearandmonthpicker.utils.getTimeMillisFromMonthAndYear


class PickerDialogFragment : Fragment() {

    private var _binding: FragmentPickerDialogBinding? = null
    private val binding get() = checkNotNull(_binding){"binding == null"}


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
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setViews()
//    }
//

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}