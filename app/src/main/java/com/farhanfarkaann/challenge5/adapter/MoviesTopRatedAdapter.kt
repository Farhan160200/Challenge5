package com.farhanfarkaann.challenge5.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farhanfarkaann.challenge5.databinding.ActivityRecyclerHorizontalTopRatedBinding
import com.farhanfarkaann.challenge5.data.api.model.model_TopRated.Result


class MoviesTopRatedAdapter(private val onClick:(Result)->Unit) : ListAdapter<Result, MoviesTopRatedAdapter.ViewHolder>(
    ResultComparator()
) {


    class ViewHolder(private val binding: ActivityRecyclerHorizontalTopRatedBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            currentResult: Result,
            onClick: (Result) -> Unit
        ){

            binding.apply {
                tvJudulHorizontal.text = currentResult.title
                Glide.with(binding.ivImageHorizontal).load("https://image.tmdb.org/t/p/w500"+currentResult.posterPath).into(binding.ivImageHorizontal)
                root.setOnClickListener{
                    onClick(currentResult)

                }
            }

        }

    }




    class ResultComparator : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActivityRecyclerHorizontalTopRatedBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }


}











//class MoviesAdapter(
//    private val onItemClick : OnClickListener)  : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
//
//    private val diffCallback  = object : DiffUtil.ItemCallback<Result>(){
//        override fun areItemsTheSame(
//            oldItem: Result,
//            newItem: Result
//        ): Boolean = oldItem.id == newItem.id
//
//        override fun areContentsTheSame(
//            oldItem: Result,
//            newItem: Result
//        ): Boolean = oldItem.hashCode() == newItem.hashCode()
//
//    }
//
//    private val differ = AsyncListDiffer(this,diffCallback)
//    fun submitData (value : List<Result>?) = differ.submitList(value)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.ViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        return ViewHolder(ActivityRecyclerBinding.inflate(inflater, parent,false)) }
//
//    override fun onBindViewHolder(holder: MoviesAdapter.ViewHolder, position: Int) {
//        val data = differ.currentList[position]
//        Log.d("MainAdapter","Image : ${data.title}")
//
//        data.let { holder.bind(data) }
//    }
//
//    override fun getItemCount(): Int = differ.currentList.size
//
//    inner class ViewHolder( val binding: ActivityRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(data: Result) {
//            binding.apply {
//                tvJudul.text = data.title
//                tvTahun.text = data.popularity.toString()
//                tvSinopsis.text = data.overview
////                Glide.with().load(data.image).into(tvJudul)
//                root.setOnClickListener{
//                    onItemClick.onClickItem(data)
//                }
//            }
//            Glide.with(binding.tvImage).load(data.backdropPath).into(binding.tvImage)
//        }
//    }
//    interface OnClickListener {
//        fun onClickItem(data : Result)
//    }
//
//}
