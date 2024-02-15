package ru.topbun.customviewyearandmonthpicker.screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import ru.topbun.customviewyearandmonthpicker.R
import ru.topbun.customviewyearandmonthpicker.databinding.ItemPickBinding

class PickAdapter: ListAdapter<PickEntity, PickViewHolder>(PickDiffCallback()) {

    var setItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPickBinding.inflate(inflater, parent, false)
        return PickViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PickViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            btnPick.text = item.value
            if (item.isEnabled){
                btnPick.setTextColor(holder.itemView.context.getColor(R.color.white))
                btnPick.backgroundTintList =
                    holder.itemView.context.getColorStateList(R.color.default_purple)
            } else {
                btnPick.setTextColor(holder.itemView.context.getColor(R.color.default_grey))
                btnPick.backgroundTintList =
                    holder.itemView.context.getColorStateList(R.color.white)
            }
        }
        holder.itemView.setOnClickListener {
            setItemClickListener?.invoke(item.id)
        }
    }
}