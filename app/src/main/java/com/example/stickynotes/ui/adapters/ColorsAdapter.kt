package com.example.stickynotes.ui.adapters

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stickynotes.databinding.ColorItemModelBinding
import com.example.stickynotes.ui.models.ColorModel

class ColorsAdapter(private val list: List<ColorModel>) :
    RecyclerView.Adapter<ColorsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ColorItemModelBinding) :
        RecyclerView.ViewHolder(binding.root) {

            val brush = binding.brush
    }
    private var onClickListener:OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ColorItemModelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.brush.setImageResource(list[position].brush)
        holder.brush.setColorFilter(Color.parseColor(list[position].brushColor), PorterDuff.Mode.SRC_IN)

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, list[position] )
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun setOnClickListener(onClickListener:OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {

        fun onClick(position: Int, model:ColorModel)
    }
}