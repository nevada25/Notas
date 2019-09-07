package com.example.notas

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast


fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_LONG)= Toast.makeText(this, message, duration).show()
fun Activity.toast(resourceId: Int, duration: Int = Toast.LENGTH_LONG)= Toast.makeText(this, resourceId, duration).show()
fun ViewGroup.inflate(layoutId: Int)= LayoutInflater.from(context).inflate(layoutId,this,false)!!

//fun ImageView.loadByURL(url: String)= Picasso.get().load(url).into(this)
//fun ImageView.loadByResource(Resource: Int)= Picasso.get().load(Resource).fit().into(this)

inline  fun  <reified T: Activity> Activity.goToActivity(noinline init: Intent.() -> Unit ={}){
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
}