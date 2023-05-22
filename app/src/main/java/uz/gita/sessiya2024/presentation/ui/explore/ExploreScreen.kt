package uz.gita.sessiya2024.presentation.ui.explore

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.sessiya2024.R
import uz.gita.sessiya2024.databinding.ScreenExploreBinding
import uz.gita.sessiya2024.presentation.adapters.MiniBookAdapter
import uz.gita.sessiya2024.presentation.ui.explore.viewmodel.ExploreViewModel
import uz.gita.sessiya2024.presentation.ui.explore.viewmodel.ExploreViewModelImpl
import java.lang.reflect.Field

@AndroidEntryPoint
class ExploreScreen : Fragment(R.layout.screen_explore) {
    private val binding by viewBinding(ScreenExploreBinding::bind)
    private val viewModel: ExploreViewModel by viewModels<ExploreViewModelImpl>()
    private val adapter =  MiniBookAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadBooks()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

        binding.apply {
            recyclerExplore.adapter = adapter
            viewModel.loadBookListLiveData.observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
            viewModel.searchListLiveData.observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
            recyclerExplore.layoutManager = GridLayoutManager(context, 2)

        }

        adapter.setOpenBookClickListener {
            val action = ExploreScreenDirections.actionExploreScreenToBookInfoScreen(it)
            findNavController().navigate(action)
        }

        binding.searchBar.doAfterTextChanged {
            val text = it?.trim().toString()
            if (text.isNullOrEmpty()) {
                viewModel.loadBooks()
            } else {
                viewModel.searchFun(text)
            }
        }
    }


}