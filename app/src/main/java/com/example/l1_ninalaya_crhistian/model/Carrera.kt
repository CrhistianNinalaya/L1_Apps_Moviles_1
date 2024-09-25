package com.example.l1_ninalaya_crhistian.model

data class Carrera(
    val id: Int,
    val nomCarrera: String,
    val pension: Double
){
    override fun toString(): String {
        return nomCarrera
    }
}
