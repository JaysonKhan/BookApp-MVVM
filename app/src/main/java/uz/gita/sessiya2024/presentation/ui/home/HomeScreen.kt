package uz.gita.sessiya2024.presentation.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.sessiya2024.R
import uz.gita.sessiya2024.databinding.ScreenHomeBinding
import uz.gita.sessiya2024.presentation.adapters.TypeAdapter
import uz.gita.sessiya2024.presentation.ui.home.viewmodel.HomeViewModel
import uz.gita.sessiya2024.presentation.ui.home.viewmodel.HomeViewModelImpl
@AndroidEntryPoint
class HomeScreen:Fragment(R.layout.screen_home) {
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()

    private val adapter: TypeAdapter = TypeAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getRecommendedBooks()
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
            binding.imgError.setImageResource(R.drawable.nointernet)
            binding.txtError.text = "Server yoki internetga ulanish xatosi :("
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        binding.notifications.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreen_to_newBooksList)
        }
        adapter.setBookClickListener {
            val action = HomeScreenDirections.actionHomeScreenToBookInfoScreen(it)
            findNavController().navigate(action)
        }

        adapter.setGoToClickListener {
            val action = HomeScreenDirections.actionHomeScreenToScreenList(it)
            findNavController().navigate(action)
        }

        binding.recomendationRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = this@HomeScreen.adapter
        }

        viewModel.recommendedBooksLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }
}