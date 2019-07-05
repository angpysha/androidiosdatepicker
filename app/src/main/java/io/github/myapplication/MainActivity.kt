package io.github.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.github.angpysha.iospicker.BasePickerDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numberPicker?.displayedValues = arrayOf("1", "2", "3")
        numberPicker?.minValue = 1
        numberPicker?.maxValue = 3

        var fd = intArrayOf(1,2,3,4,5)
        var ffd = fd.map { it*3 }
//        BasePickerDialog.showDialog(context = this) {
//            date ->
//                Log.d("Date selected", date.toString())
//        }
    }
}
