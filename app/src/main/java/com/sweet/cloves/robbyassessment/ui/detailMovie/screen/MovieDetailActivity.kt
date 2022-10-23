package com.sweet.cloves.robbyassessment.ui.detailMovie.screen

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sweet.cloves.robbyassessment.Constant
import com.sweet.cloves.robbyassessment.databinding.ActivityMovieDetailBinding
import com.sweet.cloves.robbyassessment.ui.detailMovie.viewModel.MovieDetailViewModel
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.sweet.cloves.robbyassessment.R
import com.sweet.cloves.robbyassessment.model.MovieDetails
import com.sweet.cloves.robbyassessment.enum.Status
import com.sweet.cloves.robbyassessment.network.response.TrailerResponse
import com.sweet.cloves.robbyassessment.ui.detailMovie.adapter.UserReviewAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var adapter : UserReviewAdapter
    private val viewModel by viewModels<MovieDetailViewModel>()
    private lateinit var progressDialog: ProgressDialog
    private var movieID : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val bundle: Bundle? = intent.extras
        progressDialog = ProgressDialog(this)
        movieID = bundle?.getInt("movieID")
        println("Movie ID: $movieID")
        init()
        subscribeUi()
    }

    fun init(){
        adapter = UserReviewAdapter(this)
        binding.rvUserReviews.layoutManager = LinearLayoutManager(this)
        binding.rvUserReviews.adapter = adapter
    }

    private fun subscribeUi(){
        viewModel.fetchMovieReview(movieID.toString(), Constant.API_KEY, Constant.LANGUAGE, Constant.FIRST_PAGE.toString())
            .observe(this, Observer { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        result.data?.let {
                            result.data.results.let { list ->
                                if (list.size == 0){
                                    Toast.makeText(this, "Nothing review for this movie", Toast.LENGTH_SHORT).show()
                                } else {
                                    adapter.setData(list)
                                }

                            }
                        }
                    }

                    Status.ERROR -> {
                        Toast.makeText(this, "Error fetching review", Toast.LENGTH_SHORT).show()
                    }

                    Status.LOADING -> {

                    }
                }
            })

        viewModel.fetchMovieDetail(movieID.toString(), Constant.API_KEY, Constant.LANGUAGE, Constant.FIRST_PAGE.toString())
            .observe(this, Observer { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        result.data?.let {
                            updatePrimaryInformationUI(it)
                        }
                        progressDialog.dismiss()
                    }

                    Status.ERROR -> {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                    }

                    Status.LOADING -> {
                        progressDialog.setTitle("Please Wait")
                        progressDialog.setMessage("Fetching data")
                        progressDialog.show()
                    }
                }
        })

        viewModel.fetchMovieTrailer(movieID.toString(), Constant.API_KEY)
            .observe(this, Observer { result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        result.data?.let {
                            updateMovieTrailer(it)
                        }
                    }

                    Status.ERROR -> {
                        Toast.makeText(this, "Error fetching trailer", Toast.LENGTH_SHORT).show()
                    }

                    Status.LOADING -> {

                    }
                }
            })


    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun updatePrimaryInformationUI(movieDetails: MovieDetails) {
        Glide.with(this)
            .load(Constant.IMAGE_URL + movieDetails.posterPath)
            .centerCrop()
            .into(binding.ivPosterImage)
        Glide.with(this)
            .load(Constant.IMAGE_URL + movieDetails.backdropPath)
            .centerCrop()
            .into(binding.ivBackdropImage)

        binding.tvMovieTitle.text = movieDetails.title
        collapseTitle(movieDetails.title.toString())
        val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-d", Locale.ENGLISH)
        val targetFormat: DateFormat = SimpleDateFormat("MMMM dd, yyyy")
        val date: Date = movieDetails.releaseDate?.let { originalFormat.parse(it) } as Date
        val formattedDate: String = targetFormat.format(date)
        binding.tvReleaseDate.text = "$formattedDate  ●"
        binding.tvRuntime.text = "${movieDetails.runtime.toString()} minutes"
        binding.tvStatus.text = movieDetails.status
        var genreName = ""
        for (genre in movieDetails.genres!!){
            genreName += ("● " + genre.name + "  ")
        }
        binding.tvGenre.text = genreName
        binding.tvOverview.text = movieDetails.overview
    }

    private fun updateMovieTrailer(trailerResponse: TrailerResponse){
        var key = ""
        for (j in trailerResponse.results){
            key = j.key
            println("Key : $key")
        }
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(key, 0f)
            }
        })
    }

    private fun collapseTitle(title: String) {
        var isShow = true
        var scrollRange = -1
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                binding.collapsingToolbarLayout.title = title
                binding.collapsingToolbarLayout.setCollapsedTitleTextColor(resources.getColor(R.color.purple_700))
                isShow = true
            } else if (isShow) {
                binding.collapsingToolbarLayout.title = " "
                isShow = false
            }
        })
    }

    private fun showError(msg: String) {
        Snackbar.make(binding.lytParent, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }
}