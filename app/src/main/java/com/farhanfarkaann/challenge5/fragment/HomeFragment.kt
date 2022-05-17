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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
import com.farhanfarkaann.challenge5.viewmodeluser.HomeViewModel
import com.farhanfarkaann.challenge5.viewmodeluser.UserManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by viewModels()

    private lateinit var pref: UserManager
    lateinit var homeViewModel: HomeViewModel
    private lateinit var userManager: UserManager

    companion object {
        const val ID = "ID"
        const val ID3 = "ID2"
        const val ID2 = "ID2"
    }


    lateinit var prefFile : SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        prefFile = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
//        val username = prefFile.getString("USERNAME", "")
//        binding.tvUser.setText(username)
        pref = UserManager(requireActivity())
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        userManager = UserManager(requireActivity())
        getUser()
        homeViewModel.getDataUser().observe(viewLifecycleOwner){
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


    private fun showListDetailTopRated(results: List<Result>?) {
//lifecycleScope.launch(Dispatchers.IO) {


    val adapter = MoviesTopRatedAdapter {
        val mBundle = Bundle()
        mBundle.putInt(ID, it.id)
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, mBundle)

    }
    adapter.submitList(results)
    binding.recyclerViewView.adapter = adapter
//}
    }

    private fun showListDetailPopular(results: List<ResultPopular>?) {
//       lifecycleScope.launch(Dispatchers.Main){

            val adapter = MoviesPopularAdapter {
                val mBundle = Bundle()
                mBundle.putInt(ID2, it.id)
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, mBundle)

            }
            adapter.submitList(results)
            binding.recyclerViewPopular.adapter = adapter
//        }
    }

    private fun showListDetailUpComing(results: List<ResultUpComing>?) {
/*        lifecycleScope.launch(Dispatchers.IO) {*/


            val adapter = MoviesUpComingAdapter {
                val mBundle = Bundle()
                mBundle.putInt(ID3, it.id)
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, mBundle)

            }
            adapter.submitList(results)
            binding.recyclerViewUpComing.adapter = adapter
        }

//    }
private fun getUser() {
    homeViewModel.getDataUser().observe(viewLifecycleOwner){
        val navigateUpdate =
            HomeFragmentDirections.actionHomeFragmentToProfileFragment(it)
        binding.btnLogout.setOnClickListener {
            findNavController().navigate(navigateUpdate)
        }
    }
}


}