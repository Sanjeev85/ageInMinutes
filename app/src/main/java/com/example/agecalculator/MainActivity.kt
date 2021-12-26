package com.example.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val date_picker = findViewById<Button>(R.id.date_picker)
        date_picker.setOnClickListener { view ->
            datePicker(view)
        }
    }

    fun datePicker(view: View) {

        val myCalendar = Calendar.getInstance()

        /**
         * these are the default value which we pass
         * as current date */
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONDAY)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)


        var datepickerDialog = DatePickerDialog(
            this,
            // when date is selected then it will work
            DatePickerDialog.OnDateSetListener {
                /** below are the arguments that we will
                 * get when date is selected */
                    view, selected_year, selected_month, selected_dayOfMonth ->
                // below all logic what happens when date picker
                Toast.makeText(
                    this,
                    "year = $selected_year, month = $selected_month, day = $selected_dayOfMonth",
                    Toast.LENGTH_LONG
                ).show()
                /**
                 * Now logic for calculate date
                 * */
                val tv_in_minutes = findViewById<TextView>(R.id.in_minutes)
                val tv_selected_date = findViewById<TextView>(R.id.selected_date)

                //set textview date
                val selected_date = "$selected_dayOfMonth/${selected_month + 1}/$selected_year"
                tv_selected_date.text = selected_date
                tv_selected_date.visibility = View.VISIBLE


                //create simpledateFormat object with pattern and local format for eng language
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                //converted to simpleDateFormat object
                val parsed_date = sdf.parse(selected_date)

                // conver date in minutes
                // it will return time in millisecond from 1 jan 1971
                val selected_date_in_minutes = (parsed_date!!.time) / 60000

                val current_date =
                    sdf.parse(
                        sdf.format(System.currentTimeMillis())
                    )
                val current_date_in_minutes = current_date!!.time / 60000

                val diff_in_minutes = current_date_in_minutes - selected_date_in_minutes

                tv_in_minutes.visibility = View.VISIBLE
                tv_in_minutes.setText(diff_in_minutes.toString())
            },
            /***
             * these are the default values
             * that our calendar show
             * 1st -> year, 2nd month, 3rd dayOfMonth
             * */
            year,
            month,
            day,
        )

        // limit selecting date to current date maxDate will limit
        // the date picker dialog 8640000 milliseconds in a day
        datepickerDialog.datePicker.maxDate = Date().time - 8640000
        datepickerDialog.show()

    }
}