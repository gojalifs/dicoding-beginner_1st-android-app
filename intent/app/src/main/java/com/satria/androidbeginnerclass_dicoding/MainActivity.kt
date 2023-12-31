package com.satria.androidbeginnerclass_dicoding

import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.satria.androidbeginnerclass_dicoding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var tvMoveResult: TextView
    private lateinit var binding: ActivityMainBinding

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == MoveActivityWithResult.RESULT_CODE && result.data != null) {
            val selectedValue =
                result.data?.getIntExtra(MoveActivityWithResult.EXTRA_SELECTED_VALUE, 0)
            tvMoveResult.text = getString(R.string.moveResult, "$selectedValue")
        }

    }

    companion object {
        private const val STATE_RESULT = "state_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvMoveResult = findViewById(R.id.tv_move_result)

        val btnMoveActivity: Button = findViewById(R.id.btn_move)
        val btnMoveWithData: Button = findViewById(R.id.btn_move_with_data)
        val btnMoveWithParcelable: Button = findViewById(R.id.btn_intent_parcelable)
        val btnImplicit: Button = findViewById(R.id.btn_implicit_intent)
        val btnMoveForResult: Button = findViewById(R.id.btn_move_with_result)

        btnMoveActivity.setOnClickListener(this)
        btnMoveWithData.setOnClickListener(this)
        btnMoveWithParcelable.setOnClickListener(this)
        btnImplicit.setOnClickListener(this)
        btnMoveForResult.setOnClickListener(this)


        binding.btnCalculate.setOnClickListener {
            if (it?.id == R.id.btn_calculate) {
                val inputLength = binding.edtLength.text.toString().trim()
                val inputWidth = binding.edtWidth.text.toString().trim()
                val inputHeight = binding.edtHeight.text.toString().trim()
                var isEmptyFields = false
                if (inputLength.isEmpty()) {
                    isEmptyFields = true
                    binding.edtLength.error = "Field ini tidak boleh kosong"
                }
                if (inputWidth.isEmpty()) {
                    isEmptyFields = true
                    binding.edtWidth.error = "Field ini tidak boleh kosong"
                }
                if (inputHeight.isEmpty()) {
                    isEmptyFields = true
                    binding.edtHeight.error = "Field ini tidak boleh kosong"
                }
                if (!isEmptyFields) {
                    val volume =
                        inputLength.toDouble() * inputWidth.toDouble() * inputHeight.toDouble()
                    binding.tvResult.text = volume.toString()
                }
            }
        }

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT)
            binding.tvResult.text = result
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, binding.tvResult.text.toString())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_move -> {
                val moveIntent = Intent(this@MainActivity, MoveActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.btn_move_with_data -> {
                val moveIntentWData = Intent(this@MainActivity, MoveWithDataActivity::class.java)
                moveIntentWData.putExtra(MoveWithDataActivity.EXTRA_NAME, "Fajar Sidik")
                moveIntentWData.putExtra(MoveWithDataActivity.EXTRA_AGE, 24)
                startActivity(moveIntentWData)
            }

            R.id.btn_intent_parcelable -> {
                val person = Person(
                    name = "Fajar Sidik",
                    age = 24,
                    email = "Fajarsidik1999@gmail.com",
                    city = "Bekasi"
                )
                val moveWithParcelable = Intent(this@MainActivity, MoveWithObject::class.java)
                moveWithParcelable.putExtra(MoveWithObject.EXTRA_PERSON, person)
                startActivity(moveWithParcelable)
            }

            R.id.btn_implicit_intent -> {
                val phoneNumber = "085876285280"
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                startActivity(intent)
            }

            R.id.btn_move_with_result -> {
                val intent = Intent(this@MainActivity, MoveActivityWithResult::class.java)
                resultLauncher.launch(intent)
            }
        }
    }
}