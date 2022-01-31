package com.sbr.tipcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.sbr.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

lateinit var binding:ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        displayTip(0.0)
        binding.btnCalculate.setOnClickListener { calculate() }
        binding.etCostOfService.setOnKeyListener{view,keyCode,_ -> handleKeyEvent(view, keyCode)}
    }

    private fun calculate() {
        val inputCost = binding.etCostOfService.text.toString()
        val cost = inputCost.toDoubleOrNull()
        val tvTip = binding.tvTip
        if(cost == null || cost === 0.0){
            displayTip(0.0)
            return
        }
        val percent = when(binding.rgPercents.checkedRadioButtonId){
            R.id.rb_great -> .2
            R.id.rb_good -> .15
            else -> {.1}
        }
        var tipTotal = cost * percent

        if(binding.swRoundup.isChecked) tipTotal = kotlin.math.ceil(tipTotal)
        displayTip(tipTotal)


    }

    private fun displayTip(tipTotal: Double) {
        val tip:String = NumberFormat.getCurrencyInstance().format(tipTotal)
        binding.tvTip.text = getString(R.string.tip_amount,tip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}