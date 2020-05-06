package com.android.albumlist.presentation.component.photo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.albumlist.R
import com.android.albumlist.domain.Photo
import com.android.albumlist.framework.db.PhotoEntity
import com.android.albumlist.presentation.base.listeners.RecyclerItemListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.innerlayout.view.*
import java.util.*


/* Adapter class of photo*/
class PhotoAdapter(
    private val photoListViewModel: PhotoViewModel,
    private var photosMutableList: MutableList<Photo> = mutableListOf()
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>(), Filterable {

    private var searchPhotosMutableList: MutableList<Photo> = mutableListOf()

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(photoEntity: PhotoEntity) {
            photoListViewModel.openPhotosDetails(photoEntity)
        }
    }

    init {
        this.searchPhotosMutableList = photosMutableList
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photoImageView: ImageView = view.ivPhoto
        val titleTextView: TextView = view.tvTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.innerlayout, parent, false)
        )
    }

    override fun getItemCount() = searchPhotosMutableList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.run {

        if (searchPhotosMutableList.size > 0) {
            Picasso.get().load(searchPhotosMutableList[position].url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background).into(holder.photoImageView)
            holder.titleTextView.text = searchPhotosMutableList[position].title
            holder.itemView.setOnClickListener {
                //itemClickListener.invoke(searchPhotosMutableList[position])

                val photo = searchPhotosMutableList[position]
                val photoEntity =
                    PhotoEntity(photo.id, photo.albumId, photo.title, photo.url, photo.thumbnailUrl)
                onItemClickListener.onItemSelected(photoEntity)
            }
        }
    }

    fun update(newPhotos: List<Photo>) {
        if (newPhotos.size > 0) {
            searchPhotosMutableList.clear()
            newPhotos.let { searchPhotosMutableList.addAll(it) }
            notifyDataSetChanged()
        }
    }


    /////  method to filter list with title ///////
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                searchPhotosMutableList = if (charString.isEmpty()) {
                    photosMutableList
                } else {
                    val filteredList: MutableList<Photo> = mutableListOf()
                    for (row in photosMutableList) {
                        if (row.title.toLowerCase(Locale.ROOT).contains(
                                charString.toLowerCase(
                                    Locale.ROOT
                                )
                            )
                        ) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = searchPhotosMutableList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                searchPhotosMutableList = filterResults.values as MutableList<Photo>
                notifyDataSetChanged()
            }
        }
    }
}