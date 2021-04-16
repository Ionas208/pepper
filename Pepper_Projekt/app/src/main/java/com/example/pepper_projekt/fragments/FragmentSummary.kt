package com.example.pepper_projekt.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.pepper_entertainment_app.FragmentQuizArgs
import com.example.pepper_projekt.R
import com.example.pepper_projekt.databinding.FragmentStartBinding
import com.example.pepper_projekt.databinding.FragmentSummaryBinding

class FragmentSummary : Fragment() {

    private lateinit var binding : FragmentSummaryBinding
    val args: FragmentSummaryArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_summary, container, false)

        binding.tvScore.text = args.score.toString()
        binding.tvMaxScore.text = args.maxScore.toString()

        binding.btBack.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_fragmentSummary_to_fragmentMode)
        }

        return binding.root
    }
}