package uz.gita.sessiya2024.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.sessiya2024.R
import uz.gita.sessiya2024.data.local.entity.BookEntity
import uz.gita.sessiya2024.databinding.ItemNewsBinding

class NotifyBooksAdapter: ListAdapter<BookEntity, NotifyBooksAdapter.NotifyViewHolder>(NotifyDiffUtil) {
    private lateinit var listener: (BookEntity) -> Unit

    object NotifyDiffUtil : DiffUtil.ItemCallback<BookEntity>() {
        override fun areItemsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
            return oldItem.id  == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookEntity, newItem: BookEntity): Boolean {
            return oldItem.name == newItem.name || oldItem.isnewbookread == newItem.isnewbookread
        }

    }

    inner class NotifyViewHolder(private val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookEntity) {

            binding.root.setOnClickListener {
                item.isnewbookread = 0
                listener?.invoke(item)
            }

           binding.txtFan.text = item.name

                Glide
                    .with(binding.root.context)
                    .load(item.unv_logo)
                    .centerCrop()
                    .into(binding.imgLogo)

                Glide
                    .with(binding.root.context)
                    .load(item.cover_reference)
                    .centerCrop()
                    .into(binding.imgCover)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifyViewHolder  = NotifyViewHolder(
        ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: NotifyViewHolder, position: Int)  {
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim2)
        holder.bind(getItem(position))
    }
    fun setBookClickListener(block: (BookEntity) -> Unit) {
        listener = block
    }
}

