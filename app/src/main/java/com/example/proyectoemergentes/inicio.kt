package com.example.proyectoemergentes

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import okhttp3.*
import java.io.IOException
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import android.content.Context


class Inicio : AppCompatActivity() {
    private val cliente = OkHttpClient()
    var cambio: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio_sesion)
    }

    fun vista_client(view: View) {
        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)

        val usuario = editTextUsername.text.toString()
        val clave = editTextPassword.text.toString()

        if (usuario.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Iniciando sesión...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val requestBody = FormBody.Builder()
            .add("usuario", usuario)
            .add("clave", clave)
            .build()

        val request = Request.Builder()

            .url("http://192.168.39.179:3000/auth/login")
            .post(requestBody)
            .build()

        cliente.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Error de conexión", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    runOnUiThread {
                        progressDialog.dismiss()
                    }
                    val responseCode = response.code.toString()
                    if (!response.isSuccessful) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Error en la respuesta: $responseCode", Toast.LENGTH_SHORT).show()
                        }
                        return
                    }

                    val responseData = response.body?.string()
                    val jsonResponse = JSONObject(responseData)
                    val token = jsonResponse.getString("token")

                    // Guardar el token en SharedPreferences
                    val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("token", token)
                    editor.apply()

                    runOnUiThread {
                        Toast.makeText(applicationContext, "Token: $token", Toast.LENGTH_SHORT).show()

                        // Llamar a otra interfaz (eliminar_producto)
                        val intent = Intent(this@Inicio, MainProductos::class.java).apply {  }
                        startActivity(intent)
                    }

                }
            }

        })


    }
}


/*

 */

