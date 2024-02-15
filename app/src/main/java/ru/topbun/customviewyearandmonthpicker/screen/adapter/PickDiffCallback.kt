package ru.topbun.customviewyearandmonthpicker.screen.adapter

import androidx.recyclerview.widget.DiffUtil

class PickDiffCallback: DiffUtil.ItemCallback<PickEntity>() {

    override fun areItemsTheSame(oldItem: PickEntity, newItem: PickEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PickEntity, newItem: PickEntity): Boolean {
        return oldItem == newItem
    }
}