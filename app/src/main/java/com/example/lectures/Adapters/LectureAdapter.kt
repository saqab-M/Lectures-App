package com.example.lectures.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.FragmentManager
import com.example.lectures.Model.LectureModelClass
import com.example.lectures.R
import com.example.lectures.TopicsFragment
import com.example.lectures.databinding.ItemLectureLayoutBinding
import com.squareup.picasso.Picasso

class LectureAdapter(val fragmentManager:FragmentManager ,var lectures:List<LectureModelClass>):
RecyclerView.Adapter<LectureAdapter.LectureViewHolder>(){


    class LectureViewHolder(val binding: ItemLectureLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLectureLayoutBinding.inflate(layoutInflater, parent, false)
        return LectureViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return lectures.size
    }

    override fun onBindViewHolder(holder: LectureViewHolder, position: Int) {
        holder.binding.apply {
            // set values
            tvLectureName.text = lectures[position].lectureName
            tvLectureDuration.text = formatTime(lectures[position].lectureDurationMinutes)
            Picasso.get()
                .load(lectures[position].lectureIconURL)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imgLecture)

            //set click listener on item view
            root.setOnClickListener{
                val selectedLecture = lectures[position]
                val topicsFragment = TopicsFragment.newInstance(selectedLecture.lectureName,
                    selectedLecture.topics as ArrayList<Int>
                )
                fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, topicsFragment)
                    .addToBackStack(null)
                    .commit()

            }

        }
    }

    fun formatTime(min: Int): String{
        // format min into a string (00h:00m)
        val hours = min /60
        val min = min % 60

        val h = if (hours == 0) "0h" else "$hours"+"h"
        val m = if (min == 0) "00m" else String.format("%02d"+"m" , min)

        return ("$h:$m")

    }
}