package uz.gita.sessiya2024.presentation.ui.newbooks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.sessiya2024.R
import uz.gita.sessiya2024.databinding.ScreenListBinding
import uz.gita.sessiya2024.presentation.adapters.MiniBookAdapter
import uz.gita.sessiya2024.presentation.adapters.NotifyBooksAdapter
import uz.gita.sessiya2024.presentation.ui.list.viewmodel.ScreenListViewModelImpl
import uz.gita.sessiya2024.presentation.ui.newbooks.viewmodel.NewBooksViewModelImpl

@AndroidEntryPoint
class NewBooksList:Fragment(R.layout.screen_list) {
    private val binding by viewBinding(ScreenListBinding::bind)
    private val viewModel by viewModels<NewBooksViewModelImpl>()
    private val adapter = NotifyBooksAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadBooks()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onProgress.observe(viewLifecycleOwner){
            if (it){
                binding.progress.visibility = View.VISIBLE
            }else{
                binding.progress.visibility = View.GONE
            }
        }
        viewModel.onExeption.observe(viewLifecycleOwner){
            if (it){
                binding.emptyList.visibility = View.VISIBLE
            }else{
                binding.emptyList.visibility = View.GONE
            }
        }
        viewModel.onExeptionString.observe(viewLifecycleOwner){
            binding.imgError.setImageResource(R.drawable.emptylist)
            binding.txtError.text = it
        }

        binding.picsmile.visibility = View.GONE

            binding.controlView1.visibility = View.GONE
            binding.controlView2.visibility = View.GONE
            binding.txtUnvName.text = ""

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.apply {
            recyclerList.adapter = adapter
            viewModel.loadBookListLiveData.observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
            recyclerList.layoutManager = GridLayoutManager(context, 1)
        }

        adapter.setBookClickListener {
            it.isnewbookread = 0
            viewModel.updateBook(it)
            val action = NewBooksListDirections.actionNewBooksListToBookInfoScreen(it.toBookData())
            findNavController().navigate(action)
        }


    }
}