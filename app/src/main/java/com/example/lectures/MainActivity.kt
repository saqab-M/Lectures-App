package com.example.lectures

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lectures.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //create instance of the fragment
        val fragment = LectureFragment()
        //add fragment to the container view
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit()
    }

}