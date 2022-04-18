package com.farhanfarkaann.challenge5.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.farhanfarkaann.challenge5.MainViewModel
import com.farhanfarkaann.challenge5.databinding.FragmentDetailBinding
import com.farhanfarkaann.challenge5.fragment.HomeFragment.Companion.ID
import com.farhanfarkaann.challenge5.fragment.HomeFragment.Companion.ID2
import com.farhanfarkaann.challenge5.fragment.HomeFragment.Companion.ID3

class DetailFragment : Fragment() {
     private lateinit var _binding: FragmentDetailBinding
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        val id = bundle?.getInt(ID)
        val z = bundle?.getInt(ID2)
        val idUpComing = bundle?.getInt(ID3)

        mainViewModel.detailMovie.observe(viewLifecycleOwner){
            Glide.with(binding.ivImage)
                .load("https://image.tmdb.org/t/p/w500"+it.posterPath)
                .into(binding.ivImage)
            binding.tvPopularityDetail.text = "Popularity " + it.popularity.toString()
            binding.tvJudulDetail.text = it.title
            binding.tvOverviewDetail.text = it.overview
            binding.tvReleaseDate.text = it.releaseDate
        }
//        mainViewModel.detailMovie.observe(viewLifecycleOwner){
//            Glide.with(binding.ivImage)
//                .load("https://image.tmdb.org/t/p/w500"+it.posterPath)
//                .into(binding.ivImage)
//            binding.tvPopularityDetail.text = "Popularity " + it.popularity.toString()
//            binding.tvJudulDetail.text = it.title
//            binding.tvOverviewDetail.text = it.overview
//            binding.tvReleaseDate.text = it.releaseDate
//        }
//        mainViewModel.detailMovie.observe(viewLifecycleOwner){
//            Glide.with(binding.ivImage)
//                .load("https://image.tmdb.org/t/p/w500"+it.posterPath)
//                .into(binding.ivImage)
//            binding.tvPopularityDetail.text = "Popularity " + it.popularity.toString()
//            binding.tvJudulDetail.text = it.title
//            binding.tvOverviewDetail.text = it.overview
//            binding.tvReleaseDate.text = it.releaseDate
//        }



        mainViewModel.getDetailMovies(z!!)
        mainViewModel.getDetailMovies(idUpComing!!)
        mainViewModel.getDetailMovies(id!!)



    }



}