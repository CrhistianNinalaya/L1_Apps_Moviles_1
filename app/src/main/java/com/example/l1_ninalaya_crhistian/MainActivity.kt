package com.example.l1_ninalaya_crhistian

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.l1_ninalaya_crhistian.databinding.ActivityMainBinding
import com.example.l1_ninalaya_crhistian.model.Carrera

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var carreras = arrayOf(
        Carrera(0, "Computacion e Informatica", 780.0),
        Carrera(1, "Administracion de redes y comunicaciones", 700.0),
        Carrera(2, "Administracion de sistemas", 640.0)
    )

    lateinit var inputDni: AppCompatEditText
    lateinit var inputAlumno: AppCompatEditText
    lateinit var inputCarrera: AutoCompleteTextView
    lateinit var descuentoPorcentajeGroup: RadioGroup
    lateinit var btnCalcular: AppCompatButton
    lateinit var btnImprimir: AppCompatButton

    lateinit var pension: AppCompatTextView
    lateinit var desc1: AppCompatTextView
    lateinit var desc2: AppCompatTextView
    lateinit var totalDesc: AppCompatTextView
    lateinit var totalAPagar: AppCompatTextView

    var descuentoElegido: Float = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ArrayAdapter(this, android.R.layout.select_dialog_item, carreras)
        binding.autoCompleteCarrera.threshold = 1
        binding.autoCompleteCarrera.setAdapter(adapter)
//        ProductoAdapter(this,carreras).also{
//            binding.autoCompleteCarrera.setAdapter(it)
//        }

        inputDni = findViewById(R.id.inputDNI)
        inputAlumno = findViewById(R.id.inputAlumno)
        inputCarrera = findViewById(R.id.autoCompleteCarrera)
        btnCalcular = findViewById(R.id.btnCalcular)
        btnImprimir = findViewById(R.id.btnImprimir)
        descuentoPorcentajeGroup = findViewById(R.id.rgDescuento)
        pension = findViewById(R.id.textPension)
        desc1 = findViewById(R.id.textDesc1)
        desc2 = findViewById(R.id.textDesc2)
        totalDesc = findViewById(R.id.textTDesc)
        totalAPagar =  findViewById(R.id.textTPagar)

        btnCalcular.setOnClickListener {
            calcularPago()
//            val dniText = inputDni.text.toString() // Obtiene el texto ingresado por el usuario
//            val carreraText = inputCarrera.text.toString()
//
//            Log.e("EL ERROR ENCONTRADO ES:", "EL DNI ES $carreraText")
        }

        btnImprimir.setOnClickListener{
            imprimir()
        }
    }

    @SuppressLint("DefaultLocale")
    private fun calcularPago() {
        var descuentoElegidoId = descuentoPorcentajeGroup.checkedRadioButtonId
        if (descuentoElegidoId == -1) {
            Toast.makeText(this, "Seleccione un Descuento", Toast.LENGTH_SHORT).show()
            return
        }
        val idElegidoRB = findViewById<AppCompatRadioButton>(descuentoElegidoId)
        when (idElegidoRB.id) {
            R.id.rbCincoPorCiento -> {
                descuentoElegido = 0.05f
            }
            R.id.rbDiezPorCiento -> {
                descuentoElegido = 0.1f
            }
            R.id.rbDocePorCiento -> {
                descuentoElegido = 0.12f
            }
        }
        val carreraElegida: Carrera? = carreras.find { it.nomCarrera == inputCarrera.text.toString() }

//        val nomCarrera:String = carreraElegida?.nomCarrera ?: "Carrera no encontrada"
//
//        val carreraText = inputCarrera.text.toString()
//
//        Log.e("CARRERA_MATCH_ARRAY","Carrera: $nomCarrera")
//        Log.e("CARRERA_DESDE_EL_INPUT", "DelINPUT: $carreraText")

        if (carreraElegida != null) { //Validamos que la carrera elegida este en el Array
            val precioCarrera = carreraElegida.pension
            val montoDescuento1 = precioCarrera * descuentoElegido
            val montoDescuento2 = if (carreraElegida.id <2) 50f else 0f
            val descuentoTotal = montoDescuento1 + montoDescuento2
            val pagoTotal = precioCarrera - descuentoTotal

            pension.text = String.format("%.2f", precioCarrera)
            desc1.text = String.format("%.2f", montoDescuento1)
            desc2.text = String.format("%.2f", montoDescuento2)
            totalDesc.text = String.format("%.2f", descuentoTotal)
            totalAPagar.text = String.format("%.2f", pagoTotal)

            btnImprimir.isEnabled =true
        } else { //En caso no se encuentre la carrera
            Toast.makeText(this,"No contamos con esa carrera",Toast.LENGTH_SHORT).show()
        }
    }

    private fun imprimir(){
        if(inputDni.text.toString().isNotEmpty() && inputAlumno.text.toString().isNotEmpty() && inputCarrera.text.toString().isNotEmpty()){
            val alumnText :String= inputAlumno.text.toString()
            Log.e("TRAIDO_CON_INTENT", alumnText)


            val intent = Intent(this,PrintActivity::class.java)
            intent.putExtra("alumno",inputAlumno.text.toString())
            intent.putExtra("dni",inputDni.text.toString())
            intent.putExtra("carrera",inputCarrera.text.toString())
            intent.putExtra("pension",pension.text.toString())
            intent.putExtra("descuento1",desc1.text.toString())
            intent.putExtra("descuento2",desc2.text.toString())
            intent.putExtra("totalDescuento",totalDesc.text.toString())
            intent.putExtra("totalAPagar",totalAPagar.text.toString())

            startActivity(intent)
        } else{
            Toast.makeText(this,"Debe llenar todos los input", Toast.LENGTH_SHORT).show()
        }


    }
}