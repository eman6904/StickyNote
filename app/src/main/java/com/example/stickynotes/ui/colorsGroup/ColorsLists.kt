package com.example.stickynotes.ui.colorsGroup

import com.example.stickynotes.R
import com.example.stickynotes.ui.models.ColorModel

class ColorsLists {

    val colorsList = ArrayList<ColorModel>()

    fun setStickyNoteColor(){
       colorsList.add(ColorModel(R.drawable.brush_icon,"#810B0B"))
       colorsList.add(ColorModel(R.drawable.brush_icon,"#FF9800"))
       colorsList.add(ColorModel(R.drawable.brush_icon,"#673AB7"))
       colorsList.add(ColorModel(R.drawable.brush_icon,"#023732"))
       colorsList.add(ColorModel(R.drawable.brush_icon,"#0B6C98"))
       colorsList.add(ColorModel(R.drawable.brush_icon,"#DFF12D"))
       colorsList.add(ColorModel(R.drawable.brush_icon,"#613E09"))
       colorsList.add(ColorModel(R.drawable.brush_icon,"#E91E63"))
       colorsList.add(ColorModel(R.drawable.brush_icon,"#0B0A0A"))
       colorsList.add(ColorModel(R.drawable.brush_icon,"#1EDCF4"))
       colorsList.add(ColorModel(R.drawable.brush_icon,"#FFFFFFFF"))
    }

}