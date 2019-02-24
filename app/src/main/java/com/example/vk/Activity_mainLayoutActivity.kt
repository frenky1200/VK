package com.example.vk

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.CHAIN_PACKED
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout

class SomeActivity : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui){

        constraintLayout {
            editText {
                id = R.id.ww
            }.lparams(width = matchParent, height = wrapContent){
                leftMargin = dip(2)
                rightMargin = dip(10)
                bottomMargin = dip(10)

                topToTop = PARENT_ID
                leftToLeft = PARENT_ID
                rightToRight = PARENT_ID
            }
            button("Start"){
                id = R.id.qq
                textSize = 25.0f
                textColor = Color.parseColor("#0061ea")
            }.lparams(width = wrapContent, height = wrapContent){
                bottomMargin = dip(10)
                topMargin = dip(10)

                topToBottom = R.id.ww
                leftToLeft = PARENT_ID
                rightToRight = PARENT_ID
                bottomToTop = R.id.stop

                horizontalBias = 0.5f
                verticalChainStyle = CHAIN_PACKED
            }
            button("Stop"){
                id = R.id.stop
                textSize = 25.0f
                textColor = Color.parseColor("#0061ea")
            }.lparams(width = wrapContent, height = wrapContent){
                bottomMargin = dip(10)
                topMargin = dip(10)

                bottomToBottom = PARENT_ID
                leftToLeft = PARENT_ID
                rightToRight = PARENT_ID
                topToBottom = R.id.qq
            }

        }
    }

}