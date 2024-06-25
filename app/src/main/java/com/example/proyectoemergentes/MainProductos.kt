package com.example.proyectoemergentes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainProductos : AppCompatActivity() {

    private lateinit var r: RecyclerView
    private lateinit var r2: RecyclerView
    private lateinit var r3: RecyclerView
    private lateinit var personasAdapter: ProductoAdapter
    private lateinit var personasAdapter2: ProductoAdapter
    private lateinit var personasAdapter3: ProductoAdapter
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prodcutos)

        r = findViewById(R.id.recycler1)
        r2 = findViewById(R.id.recycler2)
        r3 = findViewById(R.id.recycler3)

        personasAdapter = ProductoAdapter(applicationContext, mutableListOf(), { producto ->
            val intent = Intent(this@MainProductos, modificar_Producto::class.java).apply {
                putExtra("id", producto.id)
                putExtra("nombre", producto.nombre)
                putExtra("precio", producto.precio)
                putExtra("descripcion", producto.descripcion)
                putExtra("stock", producto.stock)
                putExtra("categoria", producto.categoria)
                putExtra("imagen_url", producto.imagen_url)
            }
            startActivity(intent)
        }, { producto ->
            val intent = Intent(this@MainProductos, eliminar_producto::class.java).apply {
                putExtra("id", producto.id)
            }
            startActivity(intent)
        })

        personasAdapter2 = ProductoAdapter(applicationContext, mutableListOf(), { producto ->
            val intent = Intent(this@MainProductos, modificar_Producto::class.java).apply {
                putExtra("id", producto.id)
                putExtra("nombre", producto.nombre)
                putExtra("precio", producto.precio)
                putExtra("descripcion", producto.descripcion)
                putExtra("stock", producto.stock)
                putExtra("categoria", producto.categoria)
                putExtra("imagen_url", producto.imagen_url)
            }
            startActivity(intent)
        }, { producto ->
            val intent = Intent(this@MainProductos, eliminar_producto::class.java).apply {
                putExtra("id", producto.id)
            }
            startActivity(intent)
        })

        personasAdapter3 = ProductoAdapter(applicationContext, mutableListOf(), { producto ->
            val intent = Intent(this@MainProductos, modificar_Producto::class.java).apply {
                putExtra("id", producto.id)
                putExtra("nombre", producto.nombre)
                putExtra("precio", producto.precio)
                putExtra("descripcion", producto.descripcion)
                putExtra("stock", producto.stock)
                putExtra("categoria", producto.categoria)
                putExtra("imagen_url", producto.imagen_url)
            }
            startActivity(intent)
        }, { producto ->
            val intent = Intent(this@MainProductos, eliminar_producto::class.java).apply {
                putExtra("id", producto.id)
            }
            startActivity(intent)
        })

        r.adapter = personasAdapter
        r.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)

        r2.adapter = personasAdapter2
        r2.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)

        r3.adapter = personasAdapter3
        r3.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)

        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        token = sharedPreferences.getString("token", null)

        if (token != null) {
            obtenerProductos()
        } else {
            Toast.makeText(this, "Token no encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun obtenerProductos() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.39.179:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getProductos("Bearer $token")

        call.enqueue(object : Callback<List<productos>> {
            override fun onResponse(call: Call<List<productos>>, response: Response<List<productos>>) {
                if (response.isSuccessful) {
                    val productosList = response.body() ?: emptyList()
                    actualizarRecyclerViews(productosList)
                } else {
                    Toast.makeText(this@MainProductos, "Error al obtener los productos: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<productos>>, t: Throwable) {
                Toast.makeText(this@MainProductos, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun actualizarRecyclerViews(productosList: List<productos>) {
        val productosList1 = productosList.filter { it.categoria == "cocinas" }
        val productosList2 = productosList.filter { it.categoria == "Electrodomesticos" }
        val productosList3 = productosList.filter { it.categoria == "Televisores" }

        personasAdapter.updateData(productosList1)
        personasAdapter2.updateData(productosList2)
        personasAdapter3.updateData(productosList3)
    }
    fun usuarios(view: View) {
        val intent = Intent(this@MainProductos, usuariosInterface::class.java)
        startActivity(intent)
    }
}
