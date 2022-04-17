package com.farhanfarkaann.challenge5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farhanfarkaann.challenge5.databinding.ActivityRecyclerHorizontalBinding
import com.farhanfarkaann.challenge5.databinding.ActivityRecyclerHorizontalUpComingBinding
import com.farhanfarkaann.challenge5.model_TopRated.Result
import com.farhanfarkaann.challenge5.model_UpComing.ResultUpComing

class MoviesUpComingAdapter(private val onClick3:(ResultUpComing)->Unit) : ListAdapter<ResultUpComing, MoviesUpComingAdapter.ViewHolder3>(
    ResultComparator()
) {


    class ViewHolder3(private val binding: ActivityRecyclerHorizontalUpComingBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            currentResult: ResultUpComing,
            onClick: (ResultUpComing) -> Unit
        ){

            binding.apply {
                tvJudulHorizontal.text = currentResult.title
//                tvTahun.text = currentResult.popularity.toString()
//                tvSinopsis.text = currentResult.overview
                Glide.with(binding.ivImageHorizontal).load("https://image.tmdb.org/t/p/w500"+currentResult.posterPath).into(binding.ivImageHorizontal)
                root.setOnClickListener{
                    onClick(currentResult)

                }
            }

        }

    }



    class ResultComparator : DiffUtil.ItemCallback<ResultUpComing>() {
        override fun areItemsTheSame(oldItem: ResultUpComing, newItem: ResultUpComing): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ResultUpComing, newItem: ResultUpComing): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder3 {
        val binding = ActivityRecyclerHorizontalUpComingBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)

        return ViewHolder3(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder3, position: Int) {
        holder.bind(getItem(position), onClick3)
    }


}