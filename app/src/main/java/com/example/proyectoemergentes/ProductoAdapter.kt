package com.example.proyectoemergentes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductoAdapter(
    private val context: Context,
    private var productosList: MutableList<productos>,
    private val onProductoSelected: (productos) -> Unit, // Callback para modificar
    private val onProductoEliminar: (productos) -> Unit   // Callback para eliminar
) : RecyclerView.Adapter<ProductoAdapter.ProductosViewHolder>() {

    inner class ProductosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageUrl: ImageView = itemView.findViewById(R.id.imageLogo)
        val titulo: TextView = itemView.findViewById(R.id.txtTitulo)
        val descripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
        val btnModificar: Button = itemView.findViewById(R.id.btnModificar)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar) // Asegúrate de que este ID exista en el layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemproduc, parent, false)
        return ProductosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productosList.size
    }

    override fun onBindViewHolder(holder: ProductosViewHolder, position: Int) {
        val producto = productosList[position]
        holder.titulo.text = producto.nombre
        holder.descripcion.text = producto.descripcion

        Glide.with(context)
            .load(producto.imagen_url)
            .centerCrop()
            .into(holder.imageUrl)

        holder.btnModificar.setOnClickListener {
            onProductoSelected(producto)
        }

        holder.btnEliminar.setOnClickListener {
            onProductoEliminar(producto)
        }
    }

    fun updateData(newProductosList: List<productos>) {
        productosList.clear()
        productosList.addAll(newProductosList)
        notifyDataSetChanged()
    }
}
