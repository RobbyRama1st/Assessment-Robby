package com.sweet.cloves.robbyassessment.ui.listMovie.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sweet.cloves.robbyassessment.Constant
import com.sweet.cloves.robbyassessment.databinding.ItemMovieBinding
import com.sweet.cloves.robbyassessment.model.Movie
import com.sweet.cloves.robbyassessment.ui.detailMovie.screen.MovieDetailActivity

class MovieAdapter(private val context: Context) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private val movies = ArrayList<Movie>()

    class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = movies[position]

        holder.binding.lytParent.setOnClickListener {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("movieID", item.id)
            context.startActivity(intent)
        }

        Glide.with(context)
            .load(Constant.IMAGE_URL + item.posterPath)
            .centerCrop()
            .into(holder.binding.ivThumbMovie)
        holder.binding.tvTitle.text = item.title
        val rating =  5 * ((item.voteAverage?.div(Constant.MAX_RATING)!!))
        holder.binding.rbMovie.rating = rating.toFloat()
        holder.binding.tvVoteAverage.text = "${item.voteCount} votes"
        holder.binding.tvMovieLanguage.text = item.originalLanguage
        holder.binding.tvReleaseDate.text = item.releaseDate
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(genreList: ArrayList<Movie>) {
        movies.addAll(genreList)
        notifyDataSetChanged()
    }
}