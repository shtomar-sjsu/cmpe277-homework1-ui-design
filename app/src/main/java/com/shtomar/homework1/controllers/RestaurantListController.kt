package com.shtomar.homework1.controllers

import com.shtomar.homework1.datamodels.Restaurant

class RestaurantListController {

    var restaurants = arrayListOf<Restaurant>()

    init {
        restaurants.add(Restaurant(
                name = "The Port of Peri Peri",
                address = "Fremont California",
                rating = 4.5f))

        restaurants.add(Restaurant(
            name = "Ristorante IL Porcino",
            address = "Fremont California",
            rating = 4.4f))

        restaurants.add(Restaurant(
            name = "Frodo Joe's Petit Cafe",
            address = "Fremont California",
            rating = 4.5f))

        restaurants.add(Restaurant(
            name = "Keeku ka Dhaba",
            address = "Fremont California",
            rating = 4.5f))

        restaurants.add(Restaurant(
            name = "Din Ding Dumpling house",
            address = "Fremont California",
            rating = 4.2f))

        restaurants.add(Restaurant(
            name = "Protege",
            address = "Plato Alto",
            rating = 4.7f))

        restaurants.add(Restaurant(
            name = "Chez TJ",
            address = "Mountain View",
            rating = 4.5f))

        restaurants.add(Restaurant(
            name = "Wako Japanese Restaurant",
            address = "San Francisco",
            rating = 4.8f))

        restaurants.add(Restaurant(
            name = "The Stinking Rose",
            address = "San Francisco",
            rating = 4.2f))

        restaurants.add(Restaurant(
            name = "Madras Cafe",
            address = "Sunnyvale California",
            rating = 4.2f))
    }

}