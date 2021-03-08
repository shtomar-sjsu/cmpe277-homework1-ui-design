package com.shtomar.homework1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shtomar.homework1.datamodels.Restaurant

class RestaurantListAdapter(var restaurants: List<Restaurant>) :
    RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {

    internal var clickHandler: ((Int) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var address: TextView = itemView.findViewById(R.id.address)
        var rating: RatingBar = itemView.findViewById(R.id.rating)

        init {
            itemView.setOnClickListener {
                clickHandler?.also {
                    it(adapterPosition)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_details_view, parent, false)
        return ViewHolder(itemView = view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.name.text = restaurant.name
        holder.address.text = restaurant.address
        holder.rating.rating = restaurant.rating
    }

    override fun getItemCount() = restaurants.size
}