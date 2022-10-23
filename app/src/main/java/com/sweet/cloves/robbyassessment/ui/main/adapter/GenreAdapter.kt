package com.sweet.cloves.robbyassessment.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweet.cloves.robbyassessment.databinding.ItemGenreBinding
import com.sweet.cloves.robbyassessment.model.Genre
import com.sweet.cloves.robbyassessment.ui.listMovie.screen.MovieActivity


class GenreAdapter(private val context: Context) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    private val genres = ArrayList<Genre>()

    class ViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = genres[position]
        holder.binding.tvTitleMenuItem.text = item.name
        holder.binding.mcvItemDashboard.setOnClickListener {
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra("genreID", item.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(genreList: ArrayList<Genre>) {
        genres.addAll(genreList)
        notifyDataSetChanged()
    }
}

