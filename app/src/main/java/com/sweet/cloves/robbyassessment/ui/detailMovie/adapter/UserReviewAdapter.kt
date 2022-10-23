package com.sweet.cloves.robbyassessment.ui.detailMovie.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sweet.cloves.robbyassessment.Constant
import com.sweet.cloves.robbyassessment.databinding.ItemReviewBinding
import com.sweet.cloves.robbyassessment.model.review.Review

class UserReviewAdapter(private val context: Context) : RecyclerView.Adapter<UserReviewAdapter.ViewHolder>() {

    private val reviews = ArrayList<Review>()

    class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = reviews[position]

        val avatar = item.authorDetails.avatarPath
        val name  = item.authorDetails.name
        Glide.with(context)
            .load(Constant.IMAGE_URL + avatar)
            .centerCrop()
            .into(holder.binding.ciImageAuthor)

        holder.binding.tvAuthor.text = name
        holder.binding.tvContent.text = item.content
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(reviewList : ArrayList<Review>){
        reviews.addAll(reviewList)
        notifyDataSetChanged()
    }
}