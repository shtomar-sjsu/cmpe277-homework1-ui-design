package com.shtomar.homework1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates

class EditRestaurantActivity : AppCompatActivity() {

    companion object {
        const val KEY_RESTAURANT_NAME = "restaurant_name"
        const val KEY_RESTAURANT_ADDRESS = "restaurant_address"
        const val KEY_RESTAURANT_RATING = "restaurant_rating"
    }

    lateinit var name: EditText
    lateinit var address: EditText
    lateinit var rating: RatingBar
    private var editIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_restaurant)
        setSupportActionBar(findViewById(R.id.toolbar))

        initializeInfo()

        findViewById<Button>(R.id.update_btn).setOnClickListener {
            val restaurantName = name.text.toString()
            val restaurantAddress = address.text.toString()
            val restaurantRating = rating.rating
            val intent = Intent()

            if (restaurantName.isNullOrBlank() || restaurantAddress.isNullOrBlank() || editIndex == -1) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val bundle = Bundle()
                bundle.putString(KEY_RESTAURANT_NAME, restaurantName)
                bundle.putString(KEY_RESTAURANT_ADDRESS, restaurantAddress)
                bundle.putFloat(KEY_RESTAURANT_RATING, restaurantRating)
                bundle.putInt(RestaurantListActivity.KEY_EDIT_RESTAURANT_INDEX, editIndex)
                intent.putExtras(bundle)
                setResult(Activity.RESULT_OK, intent)
            }

            finish()
        }


    }

    private fun initializeInfo() {
        name = findViewById(R.id.et_name)
        address = findViewById(R.id.et_address)
        rating = findViewById(R.id.rating)

        val bundle = intent.extras
        name.setText(bundle?.getString(KEY_RESTAURANT_NAME) ?: "", TextView.BufferType.EDITABLE)
        address.setText(
            bundle?.getString(KEY_RESTAURANT_ADDRESS) ?: "",
            TextView.BufferType.EDITABLE
        )
        rating.rating = bundle?.getFloat(KEY_RESTAURANT_RATING) ?: 0.0f
        editIndex = bundle?.getInt(RestaurantListActivity.KEY_EDIT_RESTAURANT_INDEX) ?: -1
    }
}