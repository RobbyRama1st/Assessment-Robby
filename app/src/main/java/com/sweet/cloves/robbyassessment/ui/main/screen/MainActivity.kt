package com.sweet.cloves.robbyassessment.ui.main.screen

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sweet.cloves.robbyassessment.Constant
import com.sweet.cloves.robbyassessment.R
import com.sweet.cloves.robbyassessment.databinding.ActivityMainBinding
import com.sweet.cloves.robbyassessment.enum.Status
import com.sweet.cloves.robbyassessment.ui.main.adapter.GenreAdapter
import com.sweet.cloves.robbyassessment.util.MarginItemDecoration
import com.sweet.cloves.robbyassessment.ui.main.viewModel.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.java.simpleName
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : GenreAdapter
    private lateinit var progressDialog: ProgressDialog
    private val viewModel by viewModels<GenreViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        progressDialog = ProgressDialog(this)
        init()
        subscribeUi()
    }

    private fun init() {
        val spaceCount = 3;
        val layoutManager = GridLayoutManager(this, spaceCount)
        adapter = GenreAdapter(this)
        binding.rvGenre.layoutManager = layoutManager
        binding.rvGenre.addItemDecoration(
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin_10), spaceCount)
        )
        binding.rvGenre.adapter = adapter
    }

    private fun subscribeUi() {
        viewModel.fetchGenre(Constant.API_KEY, Constant.LANGUAGE).observe(this, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.genres?.let { list ->
                        adapter.setData(list)
                    }
                    progressDialog.dismiss()
                }

                Status.ERROR -> {
                    result.message?.let {
                        showError(it)
                    }
                    progressDialog.dismiss()
                }

                Status.LOADING -> {
                    progressDialog.setTitle("Please Wait")
                    progressDialog.setMessage("Fetching data")
                    progressDialog.show()
                }
            }
        })
    }

    private fun showError(msg: String) {
        Snackbar.make(binding.lytParent, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }
}