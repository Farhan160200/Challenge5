package com.farhanfarkaann.challenge5.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.farhanfarkaann.challenge5.MainViewModel
import com.farhanfarkaann.challenge5.R
import com.farhanfarkaann.challenge5.adapter.MoviesTopRatedAdapter
import com.farhanfarkaann.challenge5.adapter.MoviesPopularAdapter
import com.farhanfarkaann.challenge5.adapter.MoviesUpComingAdapter
import com.farhanfarkaann.challenge5.databinding.FragmentHomeBinding
import com.farhanfarkaann.challenge5.model_Popular.ResultPopular
import com.farhanfarkaann.challenge5.model_TopRated.Result
import com.farhanfarkaann.challenge5.model_UpComing.ResultUpComing
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by viewModels()

    companion object {
        const val ID = "ID"
    }

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

        recyclerViewView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        recyclerViewPopular.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        recyclerViewUpComing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)




        mainViewModel.isLoading.observe(viewLifecycleOwner){
            if (it){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }
        mainViewModel.dataMovies.observe(viewLifecycleOwner) {
            showListDetail(it.results)
        }
        mainViewModel.dataMoviesPopular.observe(viewLifecycleOwner) {
            showListMoviePopular(it.results)
        }
        mainViewModel.dataMoviesUpcoming.observe(viewLifecycleOwner) {
            showListMovieUpcoming(it.results)
        }

        mainViewModel.detailMovieTopRated.observe(viewLifecycleOwner){
            Toast.makeText(context, it.originalTitle, Toast.LENGTH_SHORT).show()
        }

    }

    private fun showListMoviePopular(results : List <ResultPopular>?) {
        val adapter = MoviesPopularAdapter {

        }
        adapter.submitList(results)
        binding.recyclerViewPopular.adapter = adapter
    }

    private fun showListMovie(results: List<Result>?) {
        val adapter= MoviesTopRatedAdapter {

        }
        adapter.submitList(results)
        binding.recyclerViewView.adapter = adapter
    }

    private fun showListMovieUpcoming(results: List<ResultUpComing>?) {
        val adapter= MoviesUpComingAdapter {

        }
        adapter.submitList(results)
        binding.recyclerViewUpComing.adapter = adapter
    }

    private fun showListDetail(results: List<Result>?) {


        val adapter= MoviesTopRatedAdapter {
            val mBundle = Bundle()
            mBundle.putInt( ID ,it.id)
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment,mBundle)
//            mainViewModel.getDetailMovies(it.id)

        }
        adapter.submitList(results)
        binding.recyclerViewView.adapter = adapter
    }



}