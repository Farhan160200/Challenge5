package com.farhanfarkaann.challenge5.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.farhanfarkaann.challenge5.MainViewModel
import com.farhanfarkaann.challenge5.MoviesAdapter
import com.farhanfarkaann.challenge5.databinding.FragmentHomeBinding
import com.farhanfarkaann.challenge5.model.Result
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by viewModels()

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

        recyclerViewView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        mainViewModel.isLoading.observe(viewLifecycleOwner){
            if (it){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }
        mainViewModel.dataMovies.observe(viewLifecycleOwner) {
            showListMovie(it.results)
        }
    }
    private fun showListMovie(results: List<Result>?) {
        val adapter= MoviesAdapter {

        }
        adapter.submitList(results)
        binding.recyclerViewView.adapter = adapter
    }


}