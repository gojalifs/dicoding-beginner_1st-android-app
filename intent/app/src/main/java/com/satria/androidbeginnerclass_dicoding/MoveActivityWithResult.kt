package com.satria.androidbeginnerclass_dicoding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup

class MoveActivityWithResult : AppCompatActivity() {
    private lateinit var btnChoose: Button
    private lateinit var rgNumber: RadioGroup

    companion object {
        const val EXTRA_SELECTED_VALUE = "extra_selected_value"
        const val RESULT_CODE = 110
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_with_result)
        btnChoose = findViewById(R.id.btn_choose)
        rgNumber = findViewById(R.id.rg_number)


        btnChoose.setOnClickListener { v ->
            if (v.id == R.id.btn_choose) {
                if (rgNumber.checkedRadioButtonId > 0) {
                    var value = 0
                    when (rgNumber.checkedRadioButtonId) {
                        R.id.rb_50 -> value = 50
                        R.id.rb_100 -> value = 100
                        R.id.rb_150 -> value = 150
                        R.id.rb_200 -> value = 200
                    }

                    val resulIntent = Intent()
                    resulIntent.putExtra(EXTRA_SELECTED_VALUE, value)
                    setResult(RESULT_CODE, resulIntent)
                    finish()
                }
            }
        }
    }


}