package com.example.l1_ninalaya_crhistian

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView

class PrintActivity : AppCompatActivity(){
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print)

        val txtViewContenido: AppCompatTextView = findViewById(R.id.txtViewContenido)

        //Obtener los datos del intent
        val alumno = intent.getStringExtra("alumno")
        val dni = intent.getStringExtra("dni")
        val carrera = intent.getStringExtra("carrera")
        val pension = intent.getStringExtra("pension")
        val descuento1 = intent.getStringExtra("descuento1")
        val descuento2 = intent.getStringExtra("descuento2")
        val totalDescuento = intent.getStringExtra("totalDescuento")
        val totalAPagar = intent.getStringExtra("totalAPagar")

        val alumnText :String= alumno.toString()
        Log.e("DESDE_PRINT_ACTIVITY", alumnText)

        //Mostrar la información en el TextView
        txtViewContenido.text = """
            Nombre: $alumno
            DNI: $dni
            Carrera: $carrera
            Pensión: $pension
            Descuento 1: $descuento1
            Descuento 2: $descuento2
            Total Descuento: $totalDescuento
            Total a Pagar: $totalAPagar
        """.trimIndent()
    }
}