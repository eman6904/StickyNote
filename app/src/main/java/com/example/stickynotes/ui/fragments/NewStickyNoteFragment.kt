package com.example.stickynotes.ui.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stickynotes.R
import com.example.stickynotes.data.tables.StickyNotesTable
import com.example.stickynotes.data.viewModel.StickyNotesViewModel
import com.example.stickynotes.databinding.FragmentNewStickyNoteBinding
import com.example.stickynotes.ui.adapters.ColorsAdapter
import com.example.stickynotes.ui.colorsGroup.ColorsLists
import com.example.stickynotes.ui.models.ColorModel


class NewStickyNoteFragment : Fragment() {

    private val stickyNoteViewModel: StickyNotesViewModel by activityViewModels()
    private var stickyNoteContent:String? = null
    private var stickyNoteColor:String = "#0B0A0A"
    private var stickyNoteFontColor:String = "#023732"
    private lateinit var colorLists: ColorsLists
    private val binding: FragmentNewStickyNoteBinding by lazy {
        FragmentNewStickyNoteBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorLists = ColorsLists()
        colorLists.setStickyNoteColor()

        stickyNoteColor(

            context = requireContext(),
            colorLists = colorLists
        )

        fontColor(
            context = requireContext(),
            colorsList = colorLists.colorsList.reversed()
        )

        manageNoteSize(
            context = requireContext(),
        )

        insertNote()

    }

    private fun stickyNoteColor(
        context: Context,
        colorLists: ColorsLists
    ) {

        val adapter = ColorsAdapter(colorLists.colorsList)
        binding.backgroundColorBtn.setOnClickListener {

            binding.backgroundColorBtn.visibility = View.GONE
            binding.backgroundColorRecycler.visibility = View.VISIBLE
        }
        binding.backgroundColorRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.backgroundColorRecycler.adapter = adapter

        adapter.setOnClickListener(object :
            ColorsAdapter.OnClickListener {
            override fun onClick(position: Int, model: ColorModel) {

                binding.backgroundColorBtn.visibility = View.VISIBLE
                binding.backgroundColorRecycler.visibility = View.GONE
                stickyNoteViewModel.setStickyNoteColor(colorLists.colorsList[position].brushColor)
            }
        })
    }

    private fun fontColor(
        context: Context,
        colorsList: List<ColorModel>
    ) {

        val adapter = ColorsAdapter(colorsList)
        binding.fontColorBtn.setOnClickListener {

            binding.fontColorBtn.visibility = View.GONE
            binding.fontColorRecycler.visibility = View.VISIBLE
        }
        binding.fontColorRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.fontColorRecycler.adapter = adapter

        adapter.setOnClickListener(object :
            ColorsAdapter.OnClickListener {
            override fun onClick(position: Int, model: ColorModel) {

                stickyNoteViewModel.setFontColor(colorsList[position].brushColor)
                binding.fontColorBtn.visibility = View.VISIBLE
                binding.fontColorRecycler.visibility = View.GONE

            }
        })
    }

    private fun manageNoteSize(
        context: Context,
        mx_note_size: Int = 150
    ) {

        binding.myNote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                binding.noteSize.setText((mx_note_size - s?.length!!).toString())
                if (s?.length!! >= mx_note_size) {
                    binding.noteSize.setTextColor(context.getColor(R.color.red))
                    binding.myNote.isCursorVisible = false

                    if (s?.length!! > mx_note_size) {
                        binding.myNote.setText(s?.substring(0, mx_note_size))
                        binding.myNote.setSelection(mx_note_size)
                    }
                } else {
                    binding.noteSize.setTextColor(context.getColor(R.color.appColor2))
                    binding.myNote.isCursorVisible = true
                    stickyNoteViewModel.setStickyNoteContent(binding.myNote.text.toString())
                }
                if (s?.length!! > 0)
                    binding.saveBtn.visibility = View.VISIBLE
                else
                    binding.saveBtn.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun insertNote(){
        
        binding.myNote.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(stickyNoteColor))
        binding.myNote.setTextColor(Color.parseColor(stickyNoteFontColor))
        binding.myNote.setHintTextColor(Color.parseColor(stickyNoteFontColor))

        stickyNoteViewModel.fontColor.observe(viewLifecycleOwner){color->

            stickyNoteFontColor = color
            binding.myNote.setTextColor(Color.parseColor(color))
            binding.myNote.setHintTextColor(Color.parseColor(color))
        }
        stickyNoteViewModel.stickyNoteColor.observe(viewLifecycleOwner){color->

            stickyNoteColor = color
            binding.myNote.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(color))
        }
        stickyNoteViewModel.stickyNoteContent.observe(viewLifecycleOwner){note->

            stickyNoteContent = note
        }
        binding.saveBtn.setOnClickListener{

            stickyNoteViewModel.insert(
                StickyNotesTable(
                    stickyNoteContent = stickyNoteContent!!,
                    stickyNoteColor = stickyNoteColor,
                    fontColor = stickyNoteFontColor,
                    isFavorite = false
                )
            )

        }
    }
}