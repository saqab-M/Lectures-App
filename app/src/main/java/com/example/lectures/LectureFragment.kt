package com.example.lectures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lectures.Adapters.LectureAdapter
import com.example.lectures.Model.LectureModelClass
import com.example.lectures.Utils.ItemDecoration
import com.example.lectures.databinding.FragmentLectureBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader


class LectureFragment : Fragment() {

    private lateinit var binding: FragmentLectureBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLectureBinding.inflate(inflater, container, false)
        binding.rvLectures.layoutManager = LinearLayoutManager(requireContext())

        //add padding to each item
        val paddingBetweenItemsInPixels = resources.getDimensionPixelSize(R.dimen.padding_between_items)
        val itemDecoration = ItemDecoration(paddingBetweenItemsInPixels)
        binding.rvLectures.addItemDecoration(itemDecoration)

        //read json from the resource file
        val json = readJsonFile()
        //parse jason into a list
        val lectures = parseLecturesFromJson(json)

        //create adapter instance
        val adapter =LectureAdapter(requireActivity().supportFragmentManager,lectures)
        binding.rvLectures.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLectures.adapter = adapter

        return binding.root
    }

    private fun readJsonFile(): String{
        //read jason file
        val inputStream = requireContext().assets.open("lectures.json")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        return  bufferedReader.use {it.readText()}
    }

    private fun parseLecturesFromJson(json: String): List<LectureModelClass>{
        val typeToken = object : TypeToken<DataModel>() {}.type
        val dataModel = Gson().fromJson<DataModel>(json, typeToken)
        return dataModel.lectures
    }

    data class DataModel(val lectures: List<LectureModelClass>)



}