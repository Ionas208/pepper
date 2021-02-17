package com.example.pepper_entertainment_app_old_android_studio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.pepper_entertainment_app_old_android_studio.databinding.FragmentDanceBinding

class FragmentDance : Fragment() {

    private lateinit var binding : FragmentDanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dance, container, false)
        return binding.root
    }
}