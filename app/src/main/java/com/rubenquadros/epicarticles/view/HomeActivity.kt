package com.rubenquadros.epicarticles.view

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.rubenquadros.epicarticles.R
import com.rubenquadros.epicarticles.custom.adapter.RecyclerViewAdapter
import com.rubenquadros.epicarticles.base.BaseActivity
import com.rubenquadros.epicarticles.callbacks.IActivityCallBack
import com.rubenquadros.epicarticles.data.local.entity.WikiEntity
import com.rubenquadros.epicarticles.data.remote.model.ArticlesResponse
import com.rubenquadros.epicarticles.utils.ApplicationConstants
import com.rubenquadros.epicarticles.utils.ApplicationUtility
import com.rubenquadros.epicarticles.viewmodel.HomeViewModel

class HomeActivity : BaseActivity(), IActivityCallBack {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: RecyclerViewAdapter

    @BindView(R.id.parentView) lateinit var parent: ConstraintLayout
    @BindView(R.id.progressBar) lateinit var mProgressBar: ProgressBar
    @BindView(R.id.searchView) lateinit var mSearchView: SearchView
    @BindView(R.id.recyclerView) lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)
        this.configureDesign()
    }

    private fun configureDesign() {
        this.setupViewModel()
        this.observeData()
        this.getRandomArticles()
        this.setupSearchView()
        this.setupRecyclerView()
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]
    }

    private fun observeData() {
        this.homeViewModel.isLoading.observe(this, Observer { it?.let { shouldShowProgress(it) } })
        this.homeViewModel.getRandomArticlesResponse().observe(this, Observer { it?.let { updateUIWithRandomArticles(it) } })
        this.homeViewModel.getRandomArticlesError().observe(this, Observer { it?.let { handleError(it) } })
    }

    private fun getRandomArticles() {
        this.homeViewModel.getArticles(ApplicationUtility.getRandomTopic(), ApplicationConstants.RANDOM_ARTICLES)
    }

    private fun setupSearchView() {
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(!query.isNullOrEmpty()) {
                    homeViewModel.getArticles(query, ApplicationConstants.SEARCH_ARTICLE)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = layoutManager
    }

    private fun updateUIWithRandomArticles(randomArticles: ArticlesResponse) {
        shouldShowProgress(false)
        if(randomArticles.query != null) {
            this.homeViewModel.deleteArticles()
            this.homeViewModel.saveArticles(randomArticles)
            adapter = RecyclerViewAdapter(randomArticles, null,this)
            mRecyclerView.adapter = adapter
        }else {
            ApplicationUtility.showSnack(getString(R.string.search_err), parent, getString(R.string.ok))
        }
    }

    private fun handleError(error: String) {
        if(error == ApplicationConstants.RANDOM_ARTICLES_ERR) {
            getLocalData()
        }else if (error == ApplicationConstants.SEARCH_ARTICLE_ERR){
            //error when searching
            ApplicationUtility.showSnack(getString(R.string.search_err), parent, getString(R.string.ok))
        }
    }

    private fun getLocalData() {
        this.homeViewModel.initLocalData()
        this.homeViewModel.getArticlesFromDB().observe(this, Observer<List<WikiEntity>> { t ->
            if(t != null && t.isNotEmpty()) {
                updateUIWithLocalData(t)
            }else {
                // No data available
                ApplicationUtility.showDialog(this, getString(R.string.no_net_title), getString(R.string.no_net), getString(R.string.ok))
                shouldShowProgress(false)
            }
        })
    }

    private fun updateUIWithLocalData(localData: List<WikiEntity>) {
        shouldShowProgress(false)
        disableSearch()
        ApplicationUtility.showSnack(getString(R.string.offline), parent, getString(R.string.ok))
        adapter = RecyclerViewAdapter(null, localData,this)
        mRecyclerView.adapter = adapter
    }

    private fun disableSearch() {
        mSearchView.isClickable = false
        mSearchView.isEnabled = false
        mSearchView.isSubmitButtonEnabled = false
        mSearchView.inputType = InputType.TYPE_NULL
    }

    private fun shouldShowProgress(isLoading: Boolean) {
        if(isLoading) {
            mProgressBar.visibility = View.VISIBLE
        }else {
            mProgressBar.visibility = View.GONE
        }
    }

    override fun onArticleClicked(title: String?, isOffline: Boolean) {
        if(title != null) {
            val intent = Intent(this, ArticleActivity::class.java)
            if(isOffline) {
                intent.putExtra(ApplicationConstants.OFFLINE, ApplicationConstants.OFFLINE)
            }else {
                intent.putExtra(ApplicationConstants.OFFLINE, ApplicationConstants.ONLINE)
            }
            intent.putExtra(ApplicationConstants.STRING_FOR_URL, ApplicationUtility.getStringForUrl(title))
            startActivity(intent)
        }
    }
}
