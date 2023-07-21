package com.example.lectures.Model

// data model class for the lectures
data class LectureModelClass (
    var lectureID: Int,
    var lectureName: String,
    var lectureIconURL: String,
    var lectureDurationMinutes: Int,
    var topics: List<Int>
        )