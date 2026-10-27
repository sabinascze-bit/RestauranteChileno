
package com.sabina.restaurantechileno

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sabina.restaurantechileno.modelo.CuentaMesa
import com.sabina.restaurantechileno.modelo.ItemMenu
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val cuenta = CuentaMesa(1)

    private val pastel = ItemMenu("Pastel de Choclo", 12000)
    private val cazuela = ItemMenu("Cazuela", 10000)

    private val formatoPesos =
        NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("es-CL")
        ).apply {
            maximumFractionDigits = 0
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cantidadPastel =
            findViewById<EditText>(R.id.etCantidadPastel)

        val cantidadCazuela =
            findViewById<EditText>(R.id.etCantidadCazuela)

        val switchPropina =
            findViewById<Switch>(R.id.switchPropina)

        val subtotalPastel =
            findViewById<TextView>(R.id.txtSubtotalPastel)

        val subtotalCazuela =
            findViewById<TextView>(R.id.txtSubtotalCazuela)

        val totalComida =
            findViewById<TextView>(R.id.txtTotalComida)

        val textoPropina =
            findViewById<TextView>(R.id.txtPropina)

        val totalFinal =
            findViewById<TextView>(R.id.txtTotalFinal)

        fun actualizarMontos() {

            val cantPastel = cantidadPastel.text.toString()
                .toIntOrNull()?.coerceAtLeast(0) ?: 0

            val cantCazuela = cantidadCazuela.text.toString()
                .toIntOrNull()?.coerceAtLeast(0) ?: 0

            cuenta.agregarItem(pastel, cantPastel)
            cuenta.agregarItem(cazuela, cantCazuela)

            cuenta.aceptarPropina = switchPropina.isChecked

            subtotalPastel.text = formatoPesos.format(
                pastel.precio.toLong() * cantPastel
            )

            subtotalCazuela.text = formatoPesos.format(
                cazuela.precio.toLong() * cantCazuela
            )

            totalComida.text = "Comida: ${
                formatoPesos.format(cuenta.calcularTotalSinPropina())
            }"

            textoPropina.text = "Propina: ${
                formatoPesos.format(cuenta.calcularPropina())
            }"

            totalFinal.text = "TOTAL: ${
                formatoPesos.format(cuenta.calcularTotalConPropina())
            }"
        }

        val observador = object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                actualizarMontos()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        cantidadPastel.addTextChangedListener(observador)
        cantidadCazuela.addTextChangedListener(observador)

        switchPropina.setOnCheckedChangeListener { _, _ ->
            actualizarMontos()
        }

        actualizarMontos()
    }
}