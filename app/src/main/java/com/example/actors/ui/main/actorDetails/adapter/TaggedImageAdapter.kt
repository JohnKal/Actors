package com.example.actors.ui.main.actorDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actors.BuildConfig
import com.example.actors.databinding.ActorTagImageLayoutBinding
import com.example.data.businessmodel.ResultModel
import com.facebook.drawee.view.SimpleDraweeView

class TaggedImageAdapter (
    private val taggedImages: List<ResultModel>
) : RecyclerView.Adapter<TaggedImageAdapter.TaggedImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaggedImageViewHolder =
        TaggedImageViewHolder(
            ActorTagImageLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TaggedImageViewHolder, position: Int) {

        val taggedImage = taggedImages[position]

        val taggedImageUrl = "${BuildConfig.BASE_URL_IMAGES}${taggedImage.filePath}"
        holder.taggedImageView?.setImageURI(taggedImageUrl)
    }

    override fun getItemCount(): Int = taggedImages.size

    class TaggedImageViewHolder(binding: ActorTagImageLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val taggedImageView: SimpleDraweeView? = binding.taggedImageView
    }
}