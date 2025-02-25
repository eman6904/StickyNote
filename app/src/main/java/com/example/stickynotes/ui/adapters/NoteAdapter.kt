package com.example.stickynotes.ui.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.stickynotes.R
import com.example.stickynotes.data.tables.StickyNotesTable
import com.example.stickynotes.data.viewModel.StickyNotesViewModel
import com.example.stickynotes.databinding.NoteItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteAdapter (private val list: List<StickyNotesTable>,private val stickyNotesViewModel: StickyNotesViewModel) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var noteBody = binding.noteBody
        val noteContent = binding.noteContent
        val isSelectedCheckBox = binding.isSelectedCheckBox
        val lockIcon = binding.lockIcon2
        val layer = binding.layer

    }
    private var onClickListener:OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.noteBody.backgroundTintList = ColorStateList.valueOf(Color.parseColor(list[position].stickyNoteColor))
        holder.noteContent.setText(list[position].stickyNoteContent)
        if (!list[position].isLocked){

            holder.layer.visibility = View.GONE
        } else {
            holder.layer.visibility = View.VISIBLE
            holder.layer.backgroundTintList = ColorStateList.valueOf(Color.parseColor(list[position].stickyNoteColor))
            holder.noteContent.setText(list[position].stickyNoteContent)
        }
        holder.noteContent.setTextColor(Color.parseColor(list[position].fontColor))

        holder.lockIcon.isVisible = list[position].isLocked

        if(stickyNotesViewModel.selectionMode.value == true) {

            holder.isSelectedCheckBox.visibility = View.VISIBLE
        }
        else
            holder.isSelectedCheckBox.visibility = View.GONE

        holder.isSelectedCheckBox.isChecked = stickyNotesViewModel.selectedIds.value!!.contains(list[position].id)

        if(holder.isSelectedCheckBox.isChecked)
            holder.noteBody.alpha = 0.7f
        else
            holder.noteBody.alpha = 1f
           holder.isSelectedCheckBox.setOnClickListener {

               if (stickyNotesViewModel.selectionMode.value == true) {

                   if (stickyNotesViewModel.selectedIds.value!!.contains(list[position].id)) {

                       stickyNotesViewModel.removeFromSelectedIds(list[position].id)
                       holder.noteBody.alpha = 1f
                   }
                   else {
                        stickyNotesViewModel.addToSelectedIds(list[position].id)
                       holder.noteBody.alpha = 0.7f
                   }

               }
           }

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, list[position])

        }

        holder.itemView.setOnLongClickListener {
            onClickListener?.onLongClick(position, list[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun setOnClickListener(onClickListener:OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {

        fun onClick(position: Int, model: StickyNotesTable)
        fun onLongClick(position: Int, model: StickyNotesTable)
    }
}