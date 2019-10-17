package com.rubenquadros.epicarticles.custom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rubenquadros.epicarticles.R
import com.rubenquadros.epicarticles.callbacks.IActivityCallBack
import com.rubenquadros.epicarticles.data.local.entity.WikiEntity
import com.rubenquadros.epicarticles.data.remote.model.ArticlesResponse
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val randomArticlesResponse: ArticlesResponse?, private val localData: List<WikiEntity>?,
                          listener: IActivityCallBack): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var mListener: IActivityCallBack = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.articles_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return localData?.size ?: randomArticlesResponse?.query!!.pages!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(localData == null) {

            if (randomArticlesResponse?.query!!.pages!![position].thumbnail != null) {
                Picasso.get()
                    .load(randomArticlesResponse.query!!.pages!![position].thumbnail!!.source)
                    .placeholder(R.color.colorBlack).into(holder.mImageView)
            }else {
                Picasso.get().load(R.drawable.article)
                    .placeholder(R.color.colorBlack).into(holder.mImageView)
            }

            holder.titleTv?.text = randomArticlesResponse.query!!.pages!![position].title

            if(randomArticlesResponse.query!!.pages!![position].terms != null) {
                holder.descriptionTv?.text =
                    randomArticlesResponse.query!!.pages!![position].terms!!.description!![0]
            }

            holder.mImageView?.setOnClickListener {
                mListener.onArticleClicked(randomArticlesResponse.query!!.pages!![position].title, false)
            }
        }else if(randomArticlesResponse == null) {
            if(localData[position].image != null) {
                Picasso.get().load(localData[position].image)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.color.colorBlack)
                    .into(holder.mImageView)
            }else {
                Picasso.get().load(R.drawable.article)
                    .placeholder(R.color.colorBlack).into(holder.mImageView)
            }

            holder.titleTv?.text = localData[position].title

            holder.descriptionTv?.text = localData[position].description

            holder.mImageView?.setOnClickListener {
                mListener.onArticleClicked(localData[position].url, true)
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mImageView: ImageView? = itemView.findViewById(R.id.imageView)
        val titleTv: TextView? = itemView.findViewById(R.id.title)
        val descriptionTv: TextView? = itemView.findViewById(R.id.description)
    }
}