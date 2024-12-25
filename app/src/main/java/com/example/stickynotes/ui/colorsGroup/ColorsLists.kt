package com.example.stickynotes.ui.colorsGroup

import com.example.stickynotes.R
import com.example.stickynotes.ui.models.ColorModel

class ColorsLists {

    val noteColorsList = ArrayList<ColorModel>()
    val fontColorsList = ArrayList<ColorModel>()

    fun setStickyNoteColor() {
        noteColorsList.add(ColorModel(R.drawable.brush_icon, "#78B3CE"))
        noteColorsList.add(ColorModel(R.drawable.brush_icon, "#CA7373"))
        noteColorsList.add(ColorModel(R.drawable.brush_icon, "#CB9DF0"))
        noteColorsList.add(ColorModel(R.drawable.brush_icon, "#7ABA78"))
        noteColorsList.add(ColorModel(R.drawable.brush_icon, "#898121"))
        noteColorsList.add(ColorModel(R.drawable.brush_icon, "#EE66A6"))
        noteColorsList.add(ColorModel(R.drawable.brush_icon, "#704264"))
        noteColorsList.add(ColorModel(R.drawable.brush_icon, "#747264"))
        noteColorsList.add(ColorModel(R.drawable.brush_icon, "#F0BB78"))
    }
    fun setFontColor() {
        fontColorsList.add(ColorModel(R.drawable.brush_icon, "#1F1717"))
        fontColorsList.add(ColorModel(R.drawable.brush_icon, "#FFFFFFFF"))
        fontColorsList.add(ColorModel(R.drawable.brush_icon, "#192655"))
        fontColorsList.add(ColorModel(R.drawable.brush_icon, "#79AC78"))
        fontColorsList.add(ColorModel(R.drawable.brush_icon, "#FF9B50"))
        fontColorsList.add(ColorModel(R.drawable.brush_icon, "#B31312"))
        fontColorsList.add(ColorModel(R.drawable.brush_icon, "#512B81"))
        fontColorsList.add(ColorModel(R.drawable.brush_icon, "#6C3428"))
        fontColorsList.add(ColorModel(R.drawable.brush_icon, "#0079FF"))

    }

}