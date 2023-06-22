package uz.gita.sessiya2024.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.sessiya2024.R
import uz.gita.sessiya2024.data.model.TypeData
import uz.gita.sessiya2024.databinding.ItemUniversityBinding

class UniversityAdapter : ListAdapter<TypeData, UniversityAdapter.UniversityViewHolder>(UniverDiffUtil) {
    private lateinit var listener: (TypeData) -> Unit

    object UniverDiffUtil : DiffUtil.ItemCallback<TypeData>() {
        override fun areItemsTheSame(oldItem: TypeData, newItem: TypeData): Boolean {
            return oldItem.title  == newItem.title
        }

        override fun areContentsTheSame(oldItem: TypeData, newItem: TypeData): Boolean {
            return oldItem.list == newItem.list || oldItem.iconUrl == newItem.iconUrl
        }

    }

    inner class UniversityViewHolder(private val binding: ItemUniversityBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TypeData) {

            binding.root.setOnClickListener {
                 listener?.invoke(item)
            }

            binding.txtType.text = item.title

                Glide
                    .with(binding.root.context)
                    .load(item.iconUrl)
                    .centerCrop()
                    .into(binding.imgLogo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder  = UniversityViewHolder(
        ItemUniversityBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int)  {
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animatsiya)
        holder.bind(getItem(position))
    }
    fun setBookClickListener(block: (TypeData) -> Unit) {
        listener = block
    }
}

