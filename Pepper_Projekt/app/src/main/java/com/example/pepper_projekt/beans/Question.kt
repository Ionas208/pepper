package com.example.pepper_projekt.beans

data class Question(val question: String, val answers: List<Answer>, val topic: Topic){
    companion object
}
