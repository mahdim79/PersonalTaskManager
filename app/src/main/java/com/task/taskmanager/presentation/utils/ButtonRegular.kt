package com.task.taskmanager.presentation.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.task.taskmanager.di.annotations.RegularTypeface
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ButtonRegular : androidx.appcompat.widget.AppCompatButton {
    @Inject
    @RegularTypeface
    lateinit var typeFace: Typeface

    constructor(context: Context) : super(context) {
        setTypeface()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setTypeface()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setTypeface()
    }

    private fun setTypeface() {
        typeface = this.typeFace
    }
}