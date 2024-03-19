package com.example.retrofit_intro.practice

//this class will hold all of our userdata

data class UserData (
    var id: Int,
    var email: String,
    var first_name: String,
    var last_name: String,
    var avatar: String
)