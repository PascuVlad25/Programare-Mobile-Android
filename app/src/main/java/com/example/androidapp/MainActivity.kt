package com.example.androidapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("Create new message")
//            val inflater = this.layoutInflater;
//
//            builder.setView(inflater.inflate(R.layout.create_dialog, null))
//
//            builder.setPositiveButton(
//                "Create"
//            ) { dialog, which ->
//
//            }
//            builder.setNegativeButton(
//                "Cancel"
//            ) { dialog, which -> dialog.cancel() }
//
//            builder.show()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}