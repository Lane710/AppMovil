package com.example.proyectoemergentes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class anadir_productos : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var precioEditText: EditText
    private lateinit var descripcionEditText: EditText
    private lateinit var stockEditText: EditText
    private lateinit var categoriaEditText: EditText
    private lateinit var imagenEditText: EditText

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.anadir_producto)

        nombreEditText = findViewById(R.id.nombre)
        precioEditText = findViewById(R.id.precio)
        descripcionEditText = findViewById(R.id.Descripcion)
        stockEditText = findViewById(R.id.Stock)
        categoriaEditText = findViewById(R.id.Categoria)
        imagenEditText = findViewById(R.id.imagen)

        // Recuperar el token desde SharedPreferences
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        token = sharedPreferences.getString("token", null)
    }

    fun eliminar(view: View) {
        val intent = Intent(this, MainProductos::class.java)
        startActivity(intent)
    }

    fun guardar(view: View) {
        val nombre = nombreEditText.text.toString()
        val precio = precioEditText.text.toString()
        val descripcion = descripcionEditText.text.toString()
        val stock = stockEditText.text.toString().toIntOrNull()
        val categoria = categoriaEditText.text.toString()
        val imagen = imagenEditText.text.toString()

        if (nombre.isNotEmpty() && precio.isNotEmpty() && descripcion.isNotEmpty() &&
            stock != null && categoria.isNotEmpty() && imagen.isNotEmpty()) {

            val nuevoProducto = productos(
                id = null, // Asumimos que el ID es generado por el backend
                nombre = nombre,
                precio = precio,
                descripcion = descripcion,
                stock = stock,
                categoria = categoria,
                imagen_url = imagen
            )

            enviarProducto(nuevoProducto)
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun enviarProducto(producto: productos) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.39.179:3000/") // URL base de la API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.agregarProducto("Bearer $token", producto)

        call.enqueue(object : Callback<productos> {
            override fun onResponse(call: Call<productos>, response: Response<productos>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@anadir_productos,
                        "Producto añadido exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@anadir_productos, MainProductos::class.java)
                    startActivity(intent)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(
                        this@anadir_productos,
                        "Error al añadir el producto: ${response.code()}, $errorBody",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<productos>, t: Throwable) {
                Toast.makeText(
                    this@anadir_productos,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}