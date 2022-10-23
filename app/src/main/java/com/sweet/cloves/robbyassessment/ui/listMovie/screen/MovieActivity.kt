package com.sweet.cloves.robbyassessment.ui.listMovie.screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sweet.cloves.robbyassessment.Constant
import com.sweet.cloves.robbyassessment.databinding.ActivityMovieBinding
import com.sweet.cloves.robbyassessment.enum.Status
import com.sweet.cloves.robbyassessment.model.Movie
import com.sweet.cloves.robbyassessment.network.response.MovieResponse
import com.sweet.cloves.robbyassessment.ui.listMovie.adapter.MovieAdapter
import com.sweet.cloves.robbyassessment.ui.listMovie.viewModel.MovieViewModel
import com.sweet.cloves.robbyassessment.util.EndlessRecyclerViewScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {

    private val TAG = this::class.java.name
    private lateinit var binding : ActivityMovieBinding
    private lateinit var adapter: MovieAdapter
    private var movies = ArrayList<Movie>()
    private val viewModel by viewModels<MovieViewModel>()
    private var genreID : Int? = null
    private var totalPages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val bundle: Bundle? = intent.extras
        genreID = bundle?.getInt("genreID")
        adapter = MovieAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rvMovie.layoutManager = linearLayoutManager
        binding.rvMovie.adapter = adapter
        subscribeUi(1)
        init(linearLayoutManager)
    }

    private fun init(linearLayoutManager : LinearLayoutManager){
        val scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, recyclerView: RecyclerView) {
                Log.d(TAG, "onLoadMore: page+1 = ${page+1}")
                // New data needs to be appended to the list
                if (page != totalPages) {
                    subscribeUi(page + 1)
                }
            }
        }
        binding.rvMovie.addOnScrollListener(scrollListener)
    }

    fun subscribeUi(page: Int){
        viewModel.fetchMovie(Constant.API_KEY, genreID.toString(), page).observe(this, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let { movie ->
                        updateUI(page, movie)
                    }
                }
                Status.ERROR -> {
                    result.message?.let {
                        showError(it)
                        hideProgressBar()
                    }
                }

                Status.LOADING -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun updateUI(page: Int, movieResponse: MovieResponse){
        if(page == 1) {
            Log.d(TAG, "page #${movieResponse.page} loaded successfully")
            totalPages = movieResponse.totalPages
            movies = movieResponse.results
            adapter.setData(movies)
            hideProgressBar()
        } else {
            Log.d(TAG, " page #${movieResponse.page} loaded successfully")
            totalPages = movieResponse.totalPages
            movies = movieResponse.results
            adapter.setData(movies)
            hideProgressBar()
        }
    }

    private fun showProgressBar() {
        Log.d(TAG, "::showProgressBar:")
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        Log.d(TAG, "::hideProgressBar:")
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showError(msg: String) {
        Snackbar.make(binding.lytParent, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }
}