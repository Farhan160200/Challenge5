package com.farhanfarkaann.challenge5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farhanfarkaann.challenge5.databinding.ActivityRecyclerHorizontalPopularBinding
import com.farhanfarkaann.challenge5.model_Popular.ResultPopular

class MoviesPopularAdapter(private val onClick2:(ResultPopular)->Unit) : ListAdapter<ResultPopular, MoviesPopularAdapter.ViewHolder2>(
    ResultComparator()
) {


    class ViewHolder2(private val binding: ActivityRecyclerHorizontalPopularBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentResult: ResultPopular,
                 onClick: (ResultPopular) -> Unit){

            binding.apply {
                tvJudulHorizontal.text = currentResult.title

                Glide.with(binding.ivImageHorizontal).load("https://image.tmdb.org/t/p/w500"+currentResult.posterPath).into(binding.ivImageHorizontal)
                root.setOnClickListener{
                    onClick(currentResult)

                }
            }

        }

    }



    class ResultComparator : DiffUtil.ItemCallback<ResultPopular>() {
        override fun areItemsTheSame(oldItem: ResultPopular, newItem: ResultPopular): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ResultPopular, newItem: ResultPopular): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        val binding = ActivityRecyclerHorizontalPopularBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)

        return ViewHolder2(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        holder.bind(getItem(position), onClick2)
    }



}
