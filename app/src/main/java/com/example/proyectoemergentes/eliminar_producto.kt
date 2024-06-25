package com.example.proyectoemergentes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class eliminar_producto : AppCompatActivity() {

    private var token: String? = null
    private var idProducto: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.eliminar_producto)

        idProducto = intent.getIntExtra("id", -1)
        if (idProducto == -1) {
            Toast.makeText(this, "ID de producto no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        token = sharedPreferences.getString("token", null)
    }

    fun cancelar(view: View) {
        Log.d("eliminar_producto", "Cancelando eliminación de producto")
        val intent = Intent(this, MainProductos::class.java)
        startActivity(intent)
    }

    fun eliminarProducto(view: View) {
        Log.d("eliminar_producto", "Intentando eliminar producto")
        idProducto?.let {
            eliminarProductoEnServidor(it)
        } ?: run {
            Toast.makeText(this, "ID de producto no válido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarProductoEnServidor(idProducto: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.39.179:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.eliminarProducto("Bearer $token", idProducto)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@eliminar_producto,
                        "Producto eliminado exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@eliminar_producto, MainProductos::class.java)
                    startActivity(intent)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(
                        this@eliminar_producto,
                        "Error al eliminar el producto: ${response.code()}, $errorBody",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    this@eliminar_producto,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
