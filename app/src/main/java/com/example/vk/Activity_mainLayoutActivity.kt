package com.example.vk

import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout

class SomeActivity : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui){

        constraintLayout {
            textView("Hello World!") {
                id = R.id.ww
            }.lparams(width = wrapContent, height = wrapContent){
                leftMargin = dip(2)
                rightMargin = dip(10)
                bottomMargin = dip(10)
                topToBottom = R.id.up
                leftToLeft = PARENT_ID
                bottomToBottom = PARENT_ID
                rightToRight = PARENT_ID
                horizontalBias = 0.25f
            }
            button("GO!"){
                id = R.id.qq
            }.lparams(width = wrapContent, height = wrapContent){
                topToTop = PARENT_ID
                leftToLeft = PARENT_ID
                rightToRight = PARENT_ID
            }

        }
    }

}