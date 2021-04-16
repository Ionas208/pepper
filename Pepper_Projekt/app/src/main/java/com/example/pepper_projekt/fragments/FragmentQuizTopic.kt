package com.example.pepper_projekt.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.pepper_entertainment_app.FragmentJokeDirections
import com.example.pepper_projekt.R
import com.example.pepper_projekt.beans.Topic
import com.example.pepper_projekt.databinding.FragmentQuizTopicBinding


class FragmentQuizTopic : Fragment() {

    private lateinit var binding : FragmentQuizTopicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_topic, container, false)

        binding.btTopic1.setOnClickListener{
            nav2Quiz(Topic.TOPIC1)
        }

        binding.btTopic2.setOnClickListener{
            nav2Quiz(Topic.TOPIC2)
        }

        binding.btTopic3.setOnClickListener{
            nav2Quiz(Topic.TOPIC3)
        }

        return binding.root
    }

    private fun nav2Quiz(topic: Topic){
        val action = FragmentQuizTopicDirections.actionFragmentQuizTopicToFragmentQuiz(topic)
        Navigation.findNavController(binding.root).navigate(action)
    }
}