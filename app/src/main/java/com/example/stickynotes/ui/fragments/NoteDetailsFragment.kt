package com.example.stickynotes.ui.fragments

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stickynotes.R
import com.example.stickynotes.data.tables.StickyNotesTable
import com.example.stickynotes.data.viewModel.StickyNotesViewModel
import com.example.stickynotes.databinding.FragmentNoteDetailsFrgmentBinding
import com.example.stickynotes.ui.adapters.ColorsAdapter
import com.example.stickynotes.ui.colorsGroup.ColorsLists
import com.example.stickynotes.ui.models.ColorModel


class NoteDetailsFragment : Fragment() {

    private val stickyNoteViewModel: StickyNotesViewModel by activityViewModels()
    private lateinit var selectedNote: StickyNotesTable
    private lateinit var colorLists: ColorsLists
    private lateinit var navController: NavController
    private val binding: FragmentNoteDetailsFrgmentBinding by lazy {
        FragmentNoteDetailsFrgmentBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorLists = ColorsLists()
        colorLists.setStickyNoteColor()
        colorLists.setFontColor()

        setFontColor(
            context = requireContext(),
            colorLists = colorLists
        )
        setStickyNoteColor(
            context = requireContext(),
            colorLists = colorLists
        )
        stickyNoteViewModel.selectedNote.observe(viewLifecycleOwner) { note ->

            selectedNote = stickyNoteViewModel.selectedNote.value!!
            if (!note.isLocked)
                binding.selectedNote.setText(note.stickyNoteContent)
            else
                binding.selectedNote.setText("")
            binding.selectedNote.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(note.stickyNoteColor))
            binding.selectedNote.setTextColor(Color.parseColor(note.fontColor))
            binding.selectedNote.setHintTextColor(Color.parseColor(note.fontColor))
            binding.isFav.isChecked = note.isFavorite
            binding.isLocked.isChecked = note.isLocked
            binding.lockIcon2.isVisible = note.isLocked
        }
        binding.addToFavorite.setOnClickListener {

            selectedNote.isFavorite = !selectedNote.isFavorite
            stickyNoteViewModel.setSelectedNote(selectedNote)
        }
        binding.lockNote.setOnClickListener {
            showCustomPasswordDialog(
                context = requireContext()
            )
        }
        binding.saveUpdatesBtn.setOnClickListener {
            selectedNote.stickyNoteContent = binding.selectedNote.text.toString()
            stickyNoteViewModel.setSelectedNote(selectedNote)
            stickyNoteViewModel.updateNote(selectedNote)
            saveNoteDialog(
                context = requireContext()
            )

        }
    }

    private fun setStickyNoteColor(
        context: Context,
        colorLists: ColorsLists
    ) {

        val adapter = ColorsAdapter(colorLists.noteColorsList)
        binding.noteColorList.layoutManager =
            GridLayoutManager(context, 3)
        binding.noteColorList.adapter = adapter

        adapter.setOnClickListener(object :
            ColorsAdapter.OnClickListener {
            override fun onClick(position: Int, model: ColorModel) {

                selectedNote.stickyNoteColor = colorLists.noteColorsList[position].brushColor
                stickyNoteViewModel.setSelectedNote(selectedNote)
            }
        })
    }

    private fun setFontColor(
        context: Context,
        colorLists: ColorsLists
    ) {

        val adapter = ColorsAdapter(colorLists.fontColorsList)
        binding.fontColorList.layoutManager =
            GridLayoutManager(context, 3)
        binding.fontColorList.adapter = adapter

        adapter.setOnClickListener(object :
            ColorsAdapter.OnClickListener {
            override fun onClick(position: Int, model: ColorModel) {

                selectedNote.fontColor = colorLists.noteColorsList[position].brushColor
                stickyNoteViewModel.setSelectedNote(selectedNote)
            }
        })
    }

    private fun showCustomPasswordDialog(
        context: Context,
    ) {

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.password_model)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        val cancelBtn = dialog.findViewById<Button>(R.id.cancelBtn)
        val lockBtn = dialog.findViewById<Button>(R.id.lockBtn)
        val passEdt = dialog.findViewById<EditText>(R.id.password)
        val incorrectPass = dialog.findViewById<TextView>(R.id.incorrectPass)
        cancelBtn.setOnClickListener {
            dialog.cancel()
        }
        passEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(pass: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (pass!!.isEmpty())
                    lockBtn.isEnabled = false
                else
                    lockBtn.isEnabled = true
            }

            override fun afterTextChanged(p0: Editable?) {

            }


        })
        if (selectedNote.isLocked) {

            lockBtn.setText("Open")
            passEdt.setHint("Enter your password")
            lockBtn.setOnClickListener {
                if (selectedNote.lockPassword == passEdt.text.toString()) {
                    selectedNote.isLocked = false
                    stickyNoteViewModel.setSelectedNote(selectedNote)
                    incorrectPass.visibility = View.GONE
                    dialog.cancel()
                } else {
                    incorrectPass.visibility = View.VISIBLE
                }
            }
        } else {
            lockBtn.setText("Lock")
            passEdt.setHint("Enter password")
            lockBtn.setOnClickListener {

                selectedNote.lockPassword = passEdt.text.toString()
                selectedNote.isLocked = true
                stickyNoteViewModel.setSelectedNote(selectedNote)
                dialog.cancel()

            }
        }

    }
}