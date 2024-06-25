package com.example.proyectoemergentes


data class productos(
    var id: Int?,
    var nombre: String?,
    var precio: String?,
    var descripcion: String?,
    var stock: Int? ,
    var categoria: String?,
    var imagen_url: String?
) {
    // Constructor primario con valores por defecto
    constructor(
        id: Int,
        nombre: String,
        precio: String,
        descripcion: String,
        stock: Int,
        categoria: String
    ) : this(id, nombre, precio, descripcion, stock, categoria, null)

    // Override del método toString para personalizar la representación en texto
    override fun toString(): String {
        return nombre ?: ""
    }
}