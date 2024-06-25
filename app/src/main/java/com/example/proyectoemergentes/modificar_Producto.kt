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

class modificar_Producto : AppCompatActivity() {

    private lateinit var idEditText: EditText
    private lateinit var nombreEditText: EditText
    private lateinit var precioEditText: EditText
    private lateinit var descripcionEditText: EditText
    private lateinit var stockEditText: EditText
    private lateinit var categoriaEditText: EditText
    private lateinit var imagenEditText: EditText

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modificar_producto)

        // Referencias a los EditText en el layout
        idEditText = findViewById(R.id.id_producto)
        nombreEditText = findViewById(R.id.nombre)
        precioEditText = findViewById(R.id.precio)
        descripcionEditText = findViewById(R.id.Descripcion)
        stockEditText = findViewById(R.id.Stock)
        categoriaEditText = findViewById(R.id.Categoria)
        imagenEditText = findViewById(R.id.imagen)

        // Obtener los datos del Intent
        val intent = intent
        val id = intent.getIntExtra("id", -1)
        val nombre = intent.getStringExtra("nombre")
        val precio = intent.getStringExtra("precio")
        val descripcion = intent.getStringExtra("descripcion")
        val stock = intent.getIntExtra("stock", -1)
        val categoria = intent.getStringExtra("categoria")
        val imagen_url = intent.getStringExtra("imagen_url")

        // Llenar los campos con los datos recibidos
        idEditText.setText(id.toString())
        nombreEditText.setText(nombre)
        precioEditText.setText(precio)
        descripcionEditText.setText(descripcion)
        stockEditText.setText(stock.toString())
        categoriaEditText.setText(categoria)
        imagenEditText.setText(imagen_url)

        // Recuperar el token desde SharedPreferences
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        token = sharedPreferences.getString("token", null)

        // Verificar si el token no es null antes de usarlo
        if (token != null) {

        } else {
            Toast.makeText(this, "Token no encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    fun eliminar(view: View) {
        val intent = Intent(this, MainProductos::class.java)
        startActivity(intent)
    }

    fun guardar(view: View) {
        val id = idEditText.text.toString().toIntOrNull()
        val nombre = nombreEditText.text.toString()
        val precio = precioEditText.text.toString()
        val descripcion = descripcionEditText.text.toString()
        val stock = stockEditText.text.toString().toIntOrNull()
        val categoria = categoriaEditText.text.toString()
        val imagen = imagenEditText.text.toString()

        if (id != null && stock != null) {
            // Mostrar información de depuración
            println("ID: $id, Nombre: $nombre, Precio: $precio, Descripción: $descripcion, Stock: $stock, Categoría: $categoria, Imagen: $imagen")

            val productoModificado = productos(id, nombre, precio, descripcion, stock, categoria, imagen)
            enviarProductoModificado(productoModificado)
        } else {
            Toast.makeText(this, "ID y Stock deben ser números válidos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun enviarProductoModificado(producto: productos) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.39.179:3000/") // URL base de la API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = producto.id?.let {
            apiService.actualizarProducto("Bearer $token", it, producto)
        }

        call?.enqueue(object : Callback<productos> {
            override fun onResponse(call: Call<productos>, response: Response<productos>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@modificar_Producto,
                        "Producto modificado exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@modificar_Producto, MainProductos::class.java)
                    startActivity(intent)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(
                        this@modificar_Producto,
                        "Error al modificar el producto: ${response.code()}, $errorBody",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<productos>, t: Throwable) {
                Toast.makeText(
                    this@modificar_Producto,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}