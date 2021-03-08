package com.shtomar.homework1

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.shtomar.homework1.controllers.RestaurantListController
import com.shtomar.homework1.datamodels.Restaurant

class RestaurantListActivity : AppCompatActivity() {

    private val restaurantListController = RestaurantListController()
    lateinit var recyclerView: RecyclerView

    companion object {
        const val REQUEST_CODE_NEW_RESTAURANT_ACTIVITY = 1
        const val REQUEST_CODE_EDIT_RESTAURANT_ACTIVITY = 2
        const val KEY_EDIT_RESTAURANT_INDEX = "edit_restaurant_index"
    }

    private var deletionHandler = fun (position: Int) {
        val restaurant = restaurantListController.restaurants[position]

        restaurantListController.restaurants.removeAt(position)
        recyclerView.adapter?.notifyItemRemoved(position)

        Snackbar.make(findViewById(R.id.fab), "Undo", Snackbar.LENGTH_INDEFINITE).setAction(R.string.confirm_button_positive) {
            restaurantListController.restaurants.add(position, restaurant)
            recyclerView.adapter?.notifyItemInserted(position)
        }.show()
    }

    private val restaurantEditHandler = fun(position: Int) {
        val restaurant = restaurantListController.restaurants[position]
        val editRestaurantIntent = Intent(this, EditRestaurantActivity::class.java)
        val bundle = Bundle()
        bundle.putInt(KEY_EDIT_RESTAURANT_INDEX, position)
        bundle.putString(EditRestaurantActivity.KEY_RESTAURANT_NAME, restaurant.name)
        bundle.putString(EditRestaurantActivity.KEY_RESTAURANT_ADDRESS, restaurant.address)
        bundle.putFloat(EditRestaurantActivity.KEY_RESTAURANT_RATING, restaurant.rating)
        editRestaurantIntent.putExtras(bundle)
        startActivityForResult(
            editRestaurantIntent,
            REQUEST_CODE_EDIT_RESTAURANT_ACTIVITY
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, R.string.new_restaurant_add_confirm, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.confirm_button_positive) {
                    val addNewRestaurantIntent = Intent(this, AddNewRestaurantActivity::class.java)
                    startActivityForResult(
                        addNewRestaurantIntent,
                        REQUEST_CODE_NEW_RESTAURANT_ACTIVITY
                    )
                }.show();
        }

        setUpRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view_restaurant_list)
        var restaurantListAdapter = RestaurantListAdapter(restaurantListController.restaurants)
        restaurantListAdapter.clickHandler = restaurantEditHandler
        recyclerView.adapter = restaurantListAdapter
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.set(0, 0, 0, 5)
            }
        })

        var swipeCallback = SwipeToDeleteCallback()
        swipeCallback.deletionCallback = deletionHandler

        var itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_NEW_RESTAURANT_ACTIVITY) {
                restaurantListController.restaurants.add(
                    Restaurant(
                        name = (data?.extras?.get(AddNewRestaurantActivity.KEY_RESTAURANT_NAME)
                            ?: "") as String,
                        address = (data?.extras?.get(AddNewRestaurantActivity.KEY_RESTAURANT_ADDRESS)
                            ?: "") as String,
                        rating = (data?.extras?.get(AddNewRestaurantActivity.KEY_RESTAURANT_RATING)
                            ?: 1.0f) as Float,
                    )
                )

                recyclerView.adapter?.notifyItemInserted(restaurantListController.restaurants.size - 1)
            } else if (requestCode == REQUEST_CODE_EDIT_RESTAURANT_ACTIVITY) {

                data?.extras?.also {
                    restaurantListController.restaurants[it.getInt(KEY_EDIT_RESTAURANT_INDEX)] =
                        Restaurant(
                            name = it.getString(EditRestaurantActivity.KEY_RESTAURANT_NAME)
                                ?: "",
                            address = it.getString(EditRestaurantActivity.KEY_RESTAURANT_ADDRESS)
                                ?: "",
                            rating = it.getFloat(EditRestaurantActivity.KEY_RESTAURANT_RATING),
                        )

                    recyclerView.adapter?.notifyItemChanged(it.getInt(KEY_EDIT_RESTAURANT_INDEX))
                }
            }
        }
    }
}

class SwipeToDeleteCallback : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    lateinit var deletionCallback: (Int) -> Unit

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val deletedPosition = viewHolder.adapterPosition
        deletionCallback?.also {
            it(deletedPosition)
        }
    }
}