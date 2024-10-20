package com.example.trabajopractico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ActorAdapter(var actores: List<Actor>, var context: Context) :
    RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    class ActorViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val txtName: TextView
        val ivActor: ImageView;

        init {
            ivActor = view.findViewById(R.id.ivActor)
            txtName = view.findViewById(R.id.tvNameActor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_actor, parent, false)
        return ActorViewHolder(view)
    }

    override fun getItemCount(): Int = actores.size

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val item = actores[position]
        holder.txtName.text = item.name
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/original/${item.profile_path}")
            .placeholder(R.drawable.actor_fallback)
            .into(holder.ivActor)
    }

}