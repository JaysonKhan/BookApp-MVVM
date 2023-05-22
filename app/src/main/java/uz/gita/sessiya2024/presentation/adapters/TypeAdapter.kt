package uz.gita.sessiya2024.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.sessiya2024.R
import uz.gita.sessiya2024.data.model.BookData
import uz.gita.sessiya2024.data.model.TypeData
import uz.gita.sessiya2024.databinding.ItemContainerBinding

class TypeAdapter : ListAdapter<TypeData, TypeAdapter.TypeViewHolder>(TypeDiffUtil) {
    private lateinit var listener: (BookData) -> Unit
    private lateinit var listenerGoto: (TypeData) -> Unit

    object TypeDiffUtil : DiffUtil.ItemCallback<TypeData>() {
        override fun areItemsTheSame(oldItem: TypeData, newItem: TypeData): Boolean {
            return oldItem.title  == newItem.title
        }

        override fun areContentsTheSame(oldItem: TypeData, newItem: TypeData): Boolean {
            return oldItem.list == newItem.list || oldItem.iconUrl == newItem.iconUrl
        }

    }

    inner class TypeViewHolder(private val binding: ItemContainerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TypeData) {

            val adapter = BookAdapter()
            adapter.clickListener = listener
            binding.container.adapter = adapter
            adapter.submitList(item.list)
            binding.container.layoutManager = GridLayoutManager(binding.container.context, 1, RecyclerView.HORIZONTAL, false)

            binding.btnGoto.setOnClickListener {
                listenerGoto?.invoke(item)
            }
                binding.txtType.text = item.title
                Glide
                    .with(binding.root.context)
                    .load(item.iconUrl)
                    .centerCrop()
                    .into(binding.imgLogo)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder  = TypeViewHolder(
        ItemContainerBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int){
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim2)
        holder.bind(getItem(position))
    }
    fun setBookClickListener(block: (BookData) -> Unit) {
        listener = block
    }
    fun setGoToClickListener(block: (TypeData) -> Unit) {
        listenerGoto = block
    }
}

