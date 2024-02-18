package ru.topbun.yearmonthpicker.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.topbun.yearmonthpicker.databinding.ItemPickBinding

class PickAdapter(
    var backgroundActiveItem: Int,
    var backgroundDisactiveItem: Int,
    var textColorActiveItem: Int,
    var textColorDisactiveItem: Int,
): ListAdapter<PickEntity, PickViewHolder>(PickDiffCallback()) {

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
                btnPick.setTextColor(textColorActiveItem)
                btnPick.setBackgroundResource(backgroundActiveItem)
            } else {
                btnPick.setBackgroundResource(backgroundDisactiveItem)
                btnPick.setTextColor(textColorDisactiveItem)
            }
        }
        holder.itemView.setOnClickListener {
            setItemClickListener?.invoke(item.id)
        }
    }
}