package com.example.pepper_entertainment_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation


class FragmentStart : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        val btStart: Button = view.findViewById(R.id.btStart)

        btStart.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fragmentStart_to_fragmentMode)
        }
        return view
    }
}