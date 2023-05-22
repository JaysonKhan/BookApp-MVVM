package uz.gita.sessiya2024.presentation.ui.saved

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import uz.gita.sessiya2024.R
import uz.gita.sessiya2024.data.model.TypeData
import uz.gita.sessiya2024.databinding.ScreenSavedBinding
import uz.gita.sessiya2024.presentation.adapters.UniversityAdapter
import uz.gita.sessiya2024.presentation.ui.home.HomeScreenDirections
import uz.gita.sessiya2024.presentation.ui.saved.viewmodel.SavedViewModel
import uz.gita.sessiya2024.presentation.ui.saved.viewmodel.SavedViewModelImpl

@AndroidEntryPoint
class SavedScreen : Fragment(R.layout.screen_saved) {
    private val binding by viewBinding(ScreenSavedBinding::bind)
    private val viewModel: SavedViewModel by viewModels<SavedViewModelImpl>()
    val adapter = UniversityAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUniversities()
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

        viewModel.univerLiveData.observe(viewLifecycleOwner){
            it.sortedBy { univer ->
                univer.id
            }
            adapter.submitList(it)
        }

        adapter.setBookClickListener {
            val action = SavedScreenDirections.actionSavedScreenToScreenList(it)
            findNavController().navigate(action)
        }
        binding.apply {
            recycler.adapter = adapter
            recycler.layoutManager = GridLayoutManager(context, 1)
        }
    }
}