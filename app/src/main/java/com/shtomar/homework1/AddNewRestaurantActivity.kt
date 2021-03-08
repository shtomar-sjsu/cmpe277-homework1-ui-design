package com.shtomar.homework1

import android.app.Activity
import android.content.Intent
import android.media.Rating
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class AddNewRestaurantActivity : AppCompatActivity() {

    companion object{
        const val KEY_RESTAURANT_NAME = "restaurant_name"
        const val KEY_RESTAURANT_ADDRESS = "restaurant_address"
        const val KEY_RESTAURANT_RATING = "restaurant_rating"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_restaurant)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<Button>(R.id.add_btn).setOnClickListener {

            val restaurantName =  findViewById<EditText>(R.id.et_name).text.toString()
            val restaurantAddress = findViewById<EditText>(R.id.et_address).text.toString()
            val restaurantRating = findViewById<RatingBar>(R.id.rating).rating
            val intent = Intent()

            if (restaurantName.isNullOrBlank() || restaurantAddress.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val bundle = Bundle()
                bundle.putString(KEY_RESTAURANT_NAME, restaurantName)
                bundle.putString(KEY_RESTAURANT_ADDRESS, restaurantAddress)
                bundle.putFloat(KEY_RESTAURANT_RATING, restaurantRating)
                intent.putExtras(bundle)
                setResult(Activity.RESULT_OK, intent)
            }

            finish()
        }
    }
}