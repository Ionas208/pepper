package com.example.pepper_entertainment_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMode.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentMode : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_mode, container, false)
        val btDance: Button = view.findViewById(R.id.btDance)
        val btJoke: Button = view.findViewById(R.id.btJoke)
        val btQuiz: Button = view.findViewById(R.id.btQuiz)

        btDance.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fragmentMode_to_fragmentDance)
        }
        btJoke.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fragmentMode_to_fragmentJoke)
        }
        btQuiz.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fragmentMode_to_fragmentQuiz)
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentMode.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMode().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}