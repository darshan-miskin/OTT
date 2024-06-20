package com.example.ott.screens.home.presentation

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ott.databinding.LayoutRecyclerItemBinding
import com.example.ott.screens.common.getDrawable
import com.example.ott.screens.home.domain.response.Content

class ContentAdapter(private val lifecycleOwner: LifecycleOwner) :
    PagingDataAdapter<Content, ContentAdapter.ContentHolder>(ContentDiffUtil()) {

    private var searchQuery: String = ""

    inner class ContentHolder(val binding: LayoutRecyclerItemBinding) : ViewHolder(binding.root) {
        fun bind(content: Content?) {
            binding.lifecycleOwner = lifecycleOwner
            binding.content = content

            val textView = binding.tvTitle
            val spannable = SpannableString(content?.name ?: "")
            val startIndex = content?.name?.indexOf(searchQuery, ignoreCase = true) ?: -1
            if (startIndex != -1) {
                spannable.setSpan(
                    ForegroundColorSpan(Color.YELLOW),
                    startIndex,
                    startIndex + searchQuery.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            textView.text = spannable

//            content?.posterImage?.let {
//                binding.ivFrame.setImageResource(getDrawable(it))
//            }
        }
    }

    class ContentDiffUtil : DiffUtil.ItemCallback<Content>() {
        override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem == newItem
        }
    }

    fun setSearchQuery(query: String) {
        searchQuery = query
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ContentHolder, position: Int) {
        val content = getItem(position)
        holder.bind(content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentHolder {
        val binding = LayoutRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentHolder(binding)
    }
}
