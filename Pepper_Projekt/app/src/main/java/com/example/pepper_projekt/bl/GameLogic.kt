package com.example.pepper_projekt.bl

import com.example.pepper_projekt.beans.Question
import com.example.pepper_projekt.beans.Topic
import java.io.Serializable

class GameLogic(
    val topic: Topic,
    val questions: List<Question>,
    ): Serializable{
    var currentQuestionIndex: Int = 0
    var score: Int = 0
}