package com.example.proyectoemergentes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class usuariosInterface  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crud_usuario)
    }
    fun anadirUsuarios(view: View){

        val intent= Intent(this,nuevo_usuario::class.java).apply {  }
        startActivity(intent)
    }
    fun ModUsuarios(view: View){

        val intent= Intent(this,modificar_usuario::class.java).apply {  }
        startActivity(intent)
    }
    fun EliUsuarios(view: View){

        val intent= Intent(this,eliminar_usuario::class.java).apply {  }
        startActivity(intent)
    }
    fun AddProducto(view: View){

        val intent= Intent(this,anadir_productos::class.java).apply {  }
        startActivity(intent)
    }
}