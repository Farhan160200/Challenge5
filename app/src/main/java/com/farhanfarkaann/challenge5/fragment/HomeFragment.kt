package com.farhanfarkaann.challenge5.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farhanfarkaann.challenge5.R
import com.farhanfarkaann.challenge5.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

//    private var mDB : MoviesDatabase? = null

    lateinit var prefFile : SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

//    override fun onResume() {
//        super.onResume()
//        fetchData()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefFile = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val username = prefFile.getString("USERNAME", "")
        binding.tvUser.setText(username)
    }

}