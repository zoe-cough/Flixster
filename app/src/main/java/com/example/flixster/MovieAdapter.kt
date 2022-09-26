package com.example.flixster

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

//import com.bumptech.glide.load.resource.bitmap.RoundedCorners

//import com.bumptech.glide.load.resource.bitmap.RoundedCorners

const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val TAG = "MovieAdapter"
class MovieAdapter(private val context: Context, private val movies: List<Movie>)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener {

        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            val radius = 30
            val orientation = context.resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(context).load(movie.posterImageUrl).centerCrop().transform(RoundedCorners(radius)).into(ivPoster)
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ivPoster.getLayoutParams().height = 180
                ivPoster.getLayoutParams().width = 320
                Glide.with(context).load(movie.backdropImageUrl).centerCrop().transform(RoundedCorners(radius)).into(ivPoster)
            }

        }

        override fun onClick(p0: View?) {
            //see which movie was clicked
            val movie = movies[adapterPosition]
            //navigate to new activity
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("movie_title", movie.title)
            intent.putExtra(MOVIE_EXTRA, movie)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
