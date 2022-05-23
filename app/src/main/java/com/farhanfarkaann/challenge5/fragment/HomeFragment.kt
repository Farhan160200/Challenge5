package com.farhanfarkaann.challenge5.fragment

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
import com.farhanfarkaann.challenge5.adapter.MoviesPopularAdapter
import com.farhanfarkaann.challenge5.adapter.MoviesTopRatedAdapter
import com.farhanfarkaann.challenge5.adapter.MoviesUpComingAdapter
import com.farhanfarkaann.challenge5.data.api.Status
import com.farhanfarkaann.challenge5.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by viewModels()


    companion object {
        const val ID = "ID"
        const val ID3 = "ID2"
        const val ID2 = "ID2"
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getDataUser()
        getUser()
        mainViewModel.user.observe(viewLifecycleOwner){
            binding.tvUser.text = it.username
        }

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
        getMoviesTopRated()
        getMoviesPopular()
        getMoviesUpComing()

    }

    private fun getMoviesTopRated() {
        val apiKey  = "a6e717a2fd3abac91324810090ae62ff"
        mainViewModel.dataMovieTopRated.observe(viewLifecycleOwner){
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    when (it.data?.code()) {
                        200 -> if (it.data.body() != null) {
                            binding.progressBar.visibility = View.GONE
                            val adapter = MoviesTopRatedAdapter {
                                val mBundle = Bundle()
                                mBundle.putInt(ID, it.id)
                                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, mBundle)
                            }
                            adapter.submitList(it.data?.body()!!.results)
                            binding.recyclerViewView.adapter = adapter

                        }
                        400 -> {
                            Toast.makeText(
                                requireContext(),
                                "Your API key was missing from the request, or wasn't correct.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        429 -> {
                            Toast.makeText(
                                requireContext(),
                                "You made too many requests",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        500 -> {
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong on our side.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                Status.ERROR -> {}
            }
        }
        mainViewModel.getMoviesTopRated(apiKey)
        }
    private fun getMoviesPopular() {
        val apiKey  = "a6e717a2fd3abac91324810090ae62ff"
        mainViewModel.dataMoviePopular.observe(viewLifecycleOwner){
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    when (it.data?.code()) {
                        200 -> if (it.data.body() != null) {
                            binding.progressBar.visibility = View.GONE
                            val adapter = MoviesPopularAdapter {
                                val mBundle = Bundle()
                                mBundle.putInt(ID2, it.id)
                                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, mBundle)
                            }
                            adapter.submitList(it.data?.body()!!.results)
                            binding.recyclerViewPopular.adapter = adapter

                        }
                        400 -> {
                            Toast.makeText(
                                requireContext(),
                                "Your API key was missing from the request, or wasn't correct.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        429 -> {
                            Toast.makeText(
                                requireContext(),
                                "You made too many requests",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        500 -> {
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong on our side.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                Status.ERROR -> {}
            }
        }
        mainViewModel.getMoviesPopular(apiKey)
    }
    private fun getMoviesUpComing() {
        val apiKey  = "a6e717a2fd3abac91324810090ae62ff"
        mainViewModel.dataMovieUpComing.observe(viewLifecycleOwner){
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    when (it.data?.code()) {
                        200 -> if (it.data.body() != null) {
                            binding.progressBar.visibility = View.GONE
                            val adapter = MoviesUpComingAdapter {
                                val mBundle = Bundle()
                                mBundle.putInt(ID3, it.id)
                                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, mBundle)
                            }
                            adapter.submitList(it.data?.body()!!.results)
                            binding.recyclerViewUpComing.adapter = adapter

                        }
                        400 -> {
                            Toast.makeText(
                                requireContext(),
                                "Your API key was missing from the request, or wasn't correct.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        429 -> {
                            Toast.makeText(
                                requireContext(),
                                "You made too many requests",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        500 -> {
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong on our side.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                Status.ERROR -> {}
            }
        }
        mainViewModel.getMoviesUpcoming(apiKey)

    }
    private fun getUser() {
        mainViewModel.user.observe(viewLifecycleOwner){
            val navigateUpdate =
                HomeFragmentDirections.actionHomeFragmentToProfileFragment(it)
            binding.btnLogout.setOnClickListener {
                findNavController().navigate(navigateUpdate)
            }
        }
    }
}
