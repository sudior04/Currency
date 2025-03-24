package com.example.currency1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtAmount1 = findViewById<EditText>(R.id.edtAmount1)
        val edtAmount2 = findViewById<TextView>(R.id.edtAmount2)
        val spinnerCurrency1 = findViewById<Spinner>(R.id.spinnerCurrency1)
        val spinnerCurrency2 = findViewById<Spinner>(R.id.spinnerCurrency2)
        val txtExchangeRate = findViewById<TextView>(R.id.txtExchangeRate)

        val currencyMap = mapOf(
            "United States - Dollar" to "USD",
            "Vietnam - Dong" to "VND",
            "Euro" to "EUR",
            "Japanese - Yen" to "JPY",
            "Korean - Won" to "KRW"
        )

        val currencyNames = currencyMap.keys.toList()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerCurrency1.adapter = adapter
        spinnerCurrency2.adapter = adapter

        val exchangeRates = mapOf(
            "USD-VND" to 25420.0, "VND-USD" to 0.000039,
            "JPY-VND" to 165.24, "VND-JPY" to 0.006,
            "EUR-VND" to 27083.86, "VND-EUR" to 0.000037,
            "KRW-VND" to 15.12, "VND-KRW" to 0.066,
            "USD-EUR" to 0.94, "EUR-USD" to 1.07,
            "USD-JPY" to 153.85, "JPY-USD" to 0.0065,
            "USD-KRW" to 1681.22, "KRW-USD" to 0.00060,
            "EUR-KRW" to 1791.26, "KRW-EUR" to 0.00056,
            "EUR-JPY" to 163.91, "JPY-EUR" to 0.0061,
            "KRW-JPY" to 0.092, "JPY-KRW" to 10.87
        )

        fun convertCurrency() {
            val amount = edtAmount1.text.toString().toDoubleOrNull() ?: 0.0
            val currencyFromFull = spinnerCurrency1.selectedItem.toString()
            val currencyToFull = spinnerCurrency2.selectedItem.toString()

            val currencyFrom = currencyMap[currencyFromFull] ?: ""
            val currencyTo = currencyMap[currencyToFull] ?: ""

            val key = "$currencyFrom-$currencyTo"
            val rate = exchangeRates[key] ?: 1.0

            val convertedAmount = amount * rate
            edtAmount2.text = String.format("%.2f", convertedAmount)
            txtExchangeRate.text = "1 $currencyFrom = $rate $currencyTo"
        }

        val itemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerCurrency1.onItemSelectedListener = itemSelectedListener
        spinnerCurrency2.onItemSelectedListener = itemSelectedListener

        edtAmount1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertCurrency()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}
