package com.farhanfarkaann.challenge5.fragment

import android.annotation.SuppressLint
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
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.isLoading.observe(viewLifecycleOwner){
            if (it){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }
        getDetail()



        }
    @SuppressLint("SetTextI18n")
    private fun getDetail() {
        val apiKey  = "a6e717a2fd3abac91324810090ae62ff"

        val bundle = arguments


        val id = bundle?.getInt(ID)
        val z = bundle?.getInt(ID2)
        val idUpComing = bundle?.getInt(ID3)
        mainViewModel.detailMovie.observe(viewLifecycleOwner) {

            val genre = arrayListOf<String>()
            val country = arrayListOf<String>()
            val compLogos = arrayListOf<String>()

            Glide.with(binding.ivImage)
                .load("https://image.tmdb.org/t/p/w500" + it.data?.body()?.posterPath)
                .into(binding.ivImage)
            binding.tvPopularityDetail.text =  "Popularity: " + it.data?.body()?.popularity.toString()
            binding.tvJudulDetail.text = it.data?.body()?.title
            binding.tvOverviewDetail.text = it.data?.body()?.overview

            it.data?.body()?.genres?.forEach {
                genre.add(it.name)
            }
            it.data?.body()?.productionCompanies?.forEach {
                country.add(it.name)
                country.add(it.originCountry)
            }
            it.data?.body()?.productionCompanies?.forEach {
                compLogos.add(it.logoPath)
            }

            binding.tvGenreDetail.text = "Genre: " + genre.joinToString()
            binding.tvReleaseDate.text = "Released Date: " + it.data?.body()?.releaseDate
            binding.tvProductionComp.text = "Production: ${country.joinToString()}"


        }
        mainViewModel.getDetailMovies(z!!,apiKey)
        mainViewModel.getDetailMovies(idUpComing!!,apiKey)
        mainViewModel.getDetailMovies(id!!,apiKey)

    }

}