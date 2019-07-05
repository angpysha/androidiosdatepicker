package io.github.angpysha.iospicker

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.NumberPicker
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.*
import android.graphics.drawable.ColorDrawable
import org.joda.time.DateTime


class BasePickerDialog() {


    companion object {
        private var dialog: Dialog? =null
        private var yearView: NumberPicker? = null
        private var monthView: NumberPicker? = null
        private var dayView: NumberPicker? = null
        private var cancel: Button? = null
        private var Ok: Button? = null
        private var titleContainer: LinearLayout? = null

        private var dateFormater: SimpleDateFormat? = SimpleDateFormat("MMMM",Resources.getSystem().configuration.locale)

        fun showDialog(context: Context,titleBackgroundColor: Int? = null, callback: (Date) -> Unit) {
            val view = View.inflate(context,R.layout.datepicker,null)
            yearView = view.findViewById<NumberPicker>(R.id.datepicker_year)
            monthView = view.findViewById<NumberPicker>(R.id.datepicker_month)
            dayView = view.findViewById<NumberPicker>(R.id.datepicker_day)
            cancel = view.findViewById(R.id.datepicker_cancel)
            Ok = view.findViewById(R.id.datepicker_ok)
            titleContainer = view.findViewById(R.id.title_container)

            dialog = Dialog(context,R.style.Custom_Dialog)

            yearView?.minValue = 1970
            yearView?.maxValue = 2200
            val date = DateTime()
            yearView?.value = date.year

            var locale = Resources.getSystem().configuration.locale

            val monthFormats = DateFormatSymbols(Resources.getSystem().configuration.locale)
            val monthes = monthFormats.months
            monthView?.minValue = 1
            monthView?.maxValue = 12
            monthView?.displayedValues = monthes
            monthView?.value = date.monthOfYear
         //   val cal = GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH),date.get(Calendar.YEAR))
            val daysInMonth = date.dayOfMonth().maximumValue
            dayView?.minValue = 1
            dayView?.maxValue = daysInMonth
            dayView?.value = date.dayOfMonth
            monthView?.setOnValueChangedListener { picker, oldVal, newVal ->
               var dateNew = DateTime("${yearView?.value!!}-${monthView?.value!!}-01")
                val maxVal = dateNew.dayOfMonth().maximumValue
                if (dayView?.value!! > maxVal)
                    dayView?.value = maxVal
                dayView?.minValue = 1
                dayView?.maxValue = maxVal
            }

            yearView?.setOnValueChangedListener { picker, oldVal, newVal ->
                var dateNew = DateTime("${yearView?.value!!}-${monthView?.value!!}-01")
                val maxVal = dateNew.dayOfMonth().maximumValue
                if (dayView?.value!! > maxVal)
                    dayView?.value = maxVal
                dayView?.minValue = 1
                dayView?.maxValue = maxVal
            }


            val windows = dialog?.window
            var wlp = windows?.attributes
            windows?.requestFeature(Window.FEATURE_NO_TITLE)

            dialog?.setContentView(view)
            windows?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            wlp?.gravity = Gravity.BOTTOM
            wlp?.flags = wlp!!.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()

            windows?.attributes = wlp
          //  windows?.setBackgroundDrawableResource(android.R.color.holo_green_light)
            dialog?.setCancelable(false)

            dialog?.show()

            cancel?.setOnClickListener {
                dialog?.hide()
            }

            Ok?.setOnClickListener {
                val calendar = GregorianCalendar(yearView?.value!!, monthView?.value!!, dayView?.value!!)
                callback?.invoke(calendar.time)
                dialog?.hide()
            }

            titleBackgroundColor?.let {
                titleContainer?.setBackgroundColor(it)
                setDividerColor(dayView!!,it)
                setDividerColor(monthView!!,it)
                setDividerColor(yearView!!,it)
            }


        }

        private fun setDividerColor(picker: NumberPicker, color: Int) {

            val pickerFields = NumberPicker::class.java.declaredFields
            for (pf in pickerFields) {
                if (pf.name == "mSelectionDivider") {
                    pf.isAccessible = true
                    try {
                        val colorDrawable = ColorDrawable(color)
                        pf.set(picker, colorDrawable)
                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                    } catch (e: Resources.NotFoundException) {
                        e.printStackTrace()
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    }

                    break
                }
            }
        }
    }
}