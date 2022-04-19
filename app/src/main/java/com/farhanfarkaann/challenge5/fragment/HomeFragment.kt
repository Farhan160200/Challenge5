package com.farhanfarkaann.challenge5.fragment

import android.app.AlertDialog
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
        const val ID3 = "ID2"
        const val ID2 = "ID2"
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



        binding.btnLogout.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

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
            showListDetailTopRated(it.results)
        }
        mainViewModel.dataMoviesPopular.observe(viewLifecycleOwner) {
            showListDetailPopular(it.results)
        }
        mainViewModel.dataMoviesUpcoming.observe(viewLifecycleOwner) {
            showListDetailUpComing(it.results)
        }
    }


//    private fun showListMoviePopular(results : List <ResultPopular>?) {
//        val adapter = MoviesPopularAdapter {
//
//        }
//        adapter.submitList(results)
//        binding.recyclerViewPopular.adapter = adapter
//    }

//    private fun showListMovie(results: List<Result>?) {
//        val adapter= MoviesTopRatedAdapter {
//
//        }
//        adapter.submitList(results)
//        binding.recyclerViewView.adapter = adapter
//    }

//    private fun showListMovieUpcoming(results: List<ResultUpComing>?) {
//        val adapter= MoviesUpComingAdapter {
//
//        }
//        adapter.submitList(results)
//        binding.recyclerViewUpComing.adapter = adapter
//    }





    private fun showListDetailTopRated(results: List<Result>?) {


        val adapter= MoviesTopRatedAdapter {
            val mBundle = Bundle()
            mBundle.putInt( ID ,it.id)
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment,mBundle)
//            mainViewModel.getDetailMovies(it.id)

        }
        adapter.submitList(results)
        binding.recyclerViewView.adapter = adapter
    }

    private fun showListDetailPopular(results: List<ResultPopular>?) {


        val adapter= MoviesPopularAdapter {
            val mBundle = Bundle()
            mBundle.putInt( ID2 ,it.id)
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment,mBundle)
//            mainViewModel.getDetailMovies(it.id)

        }
        adapter.submitList(results)
        binding.recyclerViewPopular.adapter = adapter
    }

    private fun showListDetailUpComing(results: List<ResultUpComing>?) {


        val adapter= MoviesUpComingAdapter {
            val mBundle = Bundle()
            mBundle.putInt( ID3 ,it.id)
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment,mBundle)
//            mainViewModel.getDetailMovies(it.id)

        }
        adapter.submitList(results)
        binding.recyclerViewUpComing.adapter = adapter
    }



}