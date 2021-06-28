package net.courrielprivate.calculatrice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

import net.courrielprivate.calculatrice.databinding.ActivityMainBinding
import java.lang.ArithmeticException

private lateinit var myBinding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var lastNumeric: Boolean = false

    //Permettre seulement un dot .
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myBinding = ActivityMainBinding.inflate(layoutInflater)
        val myBinding = myBinding.root
        setContentView(myBinding)
    }

    fun onDigit(view: View) {
        Toast.makeText(this, "Button Works", Toast.LENGTH_LONG).show()
        //Obtenir le text de la touche pressée

        myBinding.tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        myBinding.tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            myBinding.tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) { //si un operateur est dejà present alors je ne l'ajoute pas
        if (lastNumeric && !isOperatorWasAdded(myBinding.tvInput.text.toString())) {
            myBinding.tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    // Recherche si un operateur + * - / est dejà present -- return true si un operateur est dejà present
    private fun isOperatorWasAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = myBinding.tvInput.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    // je recupere la valeur à partir de l'index 1 exemple: -296-26 devient 296-26
                    tvValue = tvValue.substring(1)
                }


                if (tvValue.contains("-")) {
                    // je récupère une liste
                    val splitvalue = tvValue.split("-")
                    //exemple 99-5
                    var one = splitvalue[0] // 99
                    var two = splitvalue[1] // 5

                    if(!prefix.isEmpty())
                    {
                        one = prefix + one
                    }

                    myBinding.tvInput.text = (one.toDouble() - two.toDouble()).toString()
                }

            } catch (e: ArithmeticException) {
            }
        }
    }
}