package uz.gita.sessiya2024.presentation.ui.list

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
import uz.gita.sessiya2024.presentation.ui.list.viewmodel.ScreenListViewModelImpl
@AndroidEntryPoint
class ScreenList:Fragment(R.layout.screen_list) {
    private val binding by viewBinding(ScreenListBinding::bind)
    private val viewModel by viewModels<ScreenListViewModelImpl>()
    private val args:ScreenListArgs by navArgs()
    private val adapter = MiniBookAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadBooks(args.data.list)
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

            binding.linearUnv.visibility = View.VISIBLE
            binding.txtUnvName.text = args.data.title

            Glide.with(requireContext())
                .load(args.data.iconUrl)
                .into(binding.imgUnvLogo)

        binding.apply {
            recyclerList.adapter = adapter
            viewModel.loadBookListLiveData.observe(viewLifecycleOwner){
                var list = it.sortedBy { book ->
                    book.kurs
                }
                adapter.submitList(list)
            }
            recyclerList.layoutManager = GridLayoutManager(context, 2)
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        adapter.setOpenBookClickListener {
            val action = ScreenListDirections.actionScreenListToBookInfoScreen(it)
            findNavController().navigate(action)
        }


    }
}