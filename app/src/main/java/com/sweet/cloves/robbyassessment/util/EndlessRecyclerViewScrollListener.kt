package com.sweet.cloves.robbyassessment.util

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerViewScrollListener(layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener()
{
    companion object
    {
        private val TAG = this::class.java.name
    }

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private var visibleThreshold = 20
    // The total number of items in the dataset after the last load
    private var previousTotalItemCount = 0
    // True if we are still waiting for the last set of data to load.
    private var loading = false
    // Sets the starting page index
    private val startingPageIndex = 1
    // The current offset index of data you have loaded
    private var currentPage = startingPageIndex

    private val mLayoutManager: RecyclerView.LayoutManager = layoutManager

    init
    {
        Log.d(TAG, "::init")
    }

    /**
     * Callback method to be invoked when the RecyclerView has been scrolled.
     * This will be called after the scroll has completed.
     * This callback will also be called if visible item range changes after a layout calculation.
     * In that case, dx and dy will be 0.
     *
     * This happens many times a second during a scroll, so be wary of the code you place here.
     * We are given a few useful parameters to help us work out if we need to load some more data,
     * but first we check if we are waiting for the previous load to finish.
     *
     * @param recyclerView The RecyclerView which scrolled
     * @param dx The amount of horizontal scroll
     * @param dy The amount of vertical scroll
     */
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)
    {
        val lastVisibleItemPosition: Int = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        val totalItemCount = mLayoutManager.itemCount
        Log.d(TAG, "::onScrolled: lastVisibleItemPosition = $lastVisibleItemPosition")
        Log.d(TAG, "::onScrolled: previousTotalItemCount = $previousTotalItemCount")
        Log.d(TAG, "::onScrolled: totalItemCount = $totalItemCount")
        Log.d(TAG, "::onScrolled: visibleThreshold = $visibleThreshold")
        Log.d(TAG, "::onScrolled: currentPage = $currentPage")
        Log.d(TAG, "::onScrolled: loading = $loading")

        /**
         * If the total item count is zero and the previous isn't, assume the
         * list is invalidated and should be reset back to initial state
         *
         * If it’s still loading, we check to see if the dataset count has
         * changed, if so we conclude it has finished loading and update the current page
         * number and total item count.
         *
         * If it isn’t currently loading, we check to see if we have breached
         * the visibleThreshold and need to reload more data.
         * If we do need to reload some more data, we execute onLoadMore to fetch the data.
         * threshold should reflect how many total columns there are too
         *
         * If the total item count is zero and the previous isn't, assume the
         * list is invalidated and should be reset back to initial state
         */
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        /**
         * If it’s still loading, we check to see if the dataset count has
         * changed, if so we conclude it has finished loading and update the current page
         * number and total item count.
         */
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        /**
         * If it isn’t currently loading, we check to see if we have breached
         * the visibleThreshold and need to reload more data.
         * If we do need to reload some more data, we execute onLoadMore to fetch the data.
         */


        if (!loading) {
            if (lastVisibleItemPosition == totalItemCount - 1){
                Log.d(TAG, "::onScrolled: (!loading && (lastVisibleItemPosition + visibleThreshold > totalItemCount)) = true")
                currentPage++
                onLoadMore(currentPage, recyclerView)
                loading = true
            }
           // && (lastVisibleItemPosition + visibleThreshold > totalItemCount)

        }
    }

    // Call this method whenever performing new searches
    fun resetState()
    {
        Log.d(TAG, ":resetState")
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
        this.loading = true
    }

    // Defines the process for actually loading more data based on page
    abstract fun onLoadMore(page: Int, recyclerView: RecyclerView)
}
