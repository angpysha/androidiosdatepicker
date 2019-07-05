package io.github.angpysha.iospicker

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import net.danlew.android.joda.JodaTimeAndroid
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class iOSDatePicker(context: Context, attributeSet: AttributeSet) : ConstraintLayout(context,attributeSet) {
    private lateinit var date: TextView
    private lateinit var title: TextView
    init {
        JodaTimeAndroid.init(context)
        var view = inflate(context,R.layout.datepicker_view,this)
        date = view.findViewById(R.id.datepicker_view_value)
        title = view.findViewById(R.id.datepicker_view_title)
        val elem = view.findViewById<ConstraintLayout>(R.id.linearLayout)
        view.isClickable = true
        view.isFocusable = true
        val formStr = SimpleDateFormat("MM/dd/yyyy")




        val attrib = context.obtainStyledAttributes(attributeSet, R.styleable.iOSDatePicker)
        title.text = attrib.getString(R.styleable.iOSDatePicker_titleText)
        date.text = formStr.format(Calendar.getInstance().time)
        val size = attrib.getDimension(R.styleable.iOSDatePicker_fontSize,14f)
        date.setTextSize(TypedValue.COMPLEX_UNIT_PX,size)
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX,size)
       // date.textSize =
        val color = attrib.getColor(R.styleable.iOSDatePicker_titlebackground,Color.MAGENTA)
        attrib.recycle()

        elem.setOnClickListener {
            BasePickerDialog.showDialog(context,color) {
                    date -> this.date.text = formStr.format(date)
            }
        }
    }
}