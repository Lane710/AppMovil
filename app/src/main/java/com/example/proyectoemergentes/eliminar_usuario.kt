package com.example.proyectoemergentes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class eliminar_usuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.eliminar_usuario)

    }
    fun eliminar(view: View){
        val intent= Intent(this,MainProductos::class.java).apply {  }
        startActivity(intent)
    }

    fun guardar(view: View){
        val intent= Intent(this,MainProductos::class.java).apply {  }
        startActivity(intent)
    }
}