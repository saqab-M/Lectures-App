package com.example.lectures.Adapters

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lectures.Model.TopicModelClass
import com.example.lectures.databinding.ItemTopicLayoutBinding

class TopicsAdapter(private val topics: List<TopicModelClass>,private val sharedPreferences: SharedPreferences): RecyclerView.Adapter<TopicsAdapter.TopicsViewHolder>() {

    inner class TopicsViewHolder(val binding: ItemTopicLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopicsAdapter.TopicsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTopicLayoutBinding.inflate(layoutInflater, parent, false)
        return TopicsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicsAdapter.TopicsViewHolder, position: Int) {
        holder.binding.apply {
            // set values
            val topic = topics[position]
            tvTopics.text = topic.topicName
            cbDone.isChecked = sharedPreferences.getBoolean("topic_${topic.topicID}", false)

            // Set click listener on the checkbox
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                // Save the checkbox state in Shared Preferences
                val editor = sharedPreferences.edit()
                editor.putBoolean("topic_${topic.topicID}", isChecked)
                editor.apply()
            }


        }
    }

    override fun getItemCount(): Int {
        return topics.size
    }


}