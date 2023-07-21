package com.example.lectures

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lectures.Adapters.TopicsAdapter
import com.example.lectures.Model.TopicModelClass
import com.example.lectures.Utils.ItemDecoration
import com.example.lectures.databinding.FragmentTopicsBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader


class TopicsFragment() : Fragment() {

    private lateinit var binding: FragmentTopicsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopicsBinding.inflate(inflater,container,false)

        val args = arguments
        val lectureName = args?.getString(ARG_LECTURE_NAME)
        val topicsArray = args?.getIntegerArrayList(ARG_TOPICS_ARRAY)

        //add padding to each item
        val paddingBetweenItemsInPixels = resources.getDimensionPixelSize(R.dimen.padding_between_items)
        val itemDecoration = ItemDecoration(paddingBetweenItemsInPixels)
        binding.rvTopics.addItemDecoration(itemDecoration)

        //read json from res
        val json = readJsonFile()
        // get topics selected lecture
        val topics = getTopicsFromJson(json, topicsArray)


        val sharedPreferences = requireContext().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        // setup adapter
        val adapter = TopicsAdapter(topics, sharedPreferences)
        binding.rvTopics.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTopics.adapter = adapter
        return binding.root
    }

    private fun readJsonFile(): String{
        val inputStream = requireContext().assets.open("lectures.json")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        return  bufferedReader.use {it.readText()}
    }

    private fun getTopicsFromJson(json: String, topic_IDs: List<Int>?): List<TopicModelClass>{
        val typeToken = object : TypeToken<DataModel>() {}.type
        val dataModel = Gson().fromJson<DataModel>(json, typeToken)
        // filter topics using IDs
        if (topic_IDs != null) {
            return dataModel.topics.filter { topic -> topic_IDs.contains(topic.topicID)?: false }
        }
        return emptyList()

    }

    data class DataModel(val topics: List<TopicModelClass>)

    companion object {
        private const val ARG_LECTURE_NAME = "lecture_name"
        private const val ARG_TOPICS_ARRAY = "topics_array"
        fun newInstance(lectureName: String, topicsArray: ArrayList<Int>): TopicsFragment{
            val fragment = TopicsFragment()
            val args = Bundle()
            args.putString(ARG_LECTURE_NAME, lectureName)
            args.putIntegerArrayList(ARG_TOPICS_ARRAY, topicsArray)
            fragment.arguments = args
            return fragment
        }
    }
}