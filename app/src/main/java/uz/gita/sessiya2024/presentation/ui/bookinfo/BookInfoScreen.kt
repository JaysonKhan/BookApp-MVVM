package uz.gita.sessiya2024.presentation.ui.bookinfo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.sessiya2024.R
import uz.gita.sessiya2024.databinding.ScreenBookInfoBinding
import uz.gita.sessiya2024.presentation.ui.bookinfo.viewmodel.BookInfoViewmodel
import uz.gita.sessiya2024.presentation.ui.bookinfo.viewmodel.BookInfoViewmodelImpl
import java.io.File

@AndroidEntryPoint
class BookInfoScreen:Fragment(R.layout.screen_book_info) {
    private val binding by viewBinding(ScreenBookInfoBinding::bind)
    private val viewModel:BookInfoViewmodel by viewModels<BookInfoViewmodelImpl>()

    private val args: BookInfoScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.progress.visibility = View.INVISIBLE
        viewModel.progressLiveData.observe(viewLifecycleOwner){
            if (it){
                Toast.makeText(context, "Yuklanish jarayonda⚡...", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = View.VISIBLE
            }else{
                Toast.makeText(context, "Yuklandi🌩️...", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = View.INVISIBLE
                binding.downloadBtn.animate()
                    .translationX(500f)
                    .setDuration(2000)
                    .start()
            }
        }
        binding.apply {
            val data = args.book

            if (File(context?.filesDir, "${args.book.reference}.pdf").exists()){
                downloadBtn.setImageResource(R.drawable.done)
                linearLayout.visibility = View.VISIBLE
                downloadBtn.isClickable = false
                binding.downloadBtn.animate()
                    .translationX(500f)
                    .setDuration(0)
                    .start()
            }else{
                linearLayout.visibility = View.GONE
                downloadBtn.isClickable = true
            }

            txtTitle.text = data.name
            txtYear.text = "${data.year}-yilni ba'zasi"
            txtDate.text = "${data.date} sanada qo'shildi"
            if (data.kurs>=5){
                txtKurs.text = "${data.kurs}-sinflar uchun"
            }else{
                txtKurs.text = "${data.kurs}-kurslar uchun"
            }
            txtDescription.text = data.description

            Glide
                .with(requireContext())
                .load(data.cover_reference)
                .into(bookImage)

            downloadBtn.setOnClickListener {
                viewModel.downloadBook(requireContext(), data)
            }


            linearLayout.setOnClickListener {
                val action = BookInfoScreenDirections.actionBookInfoScreenToReadBookScreen(data)
                findNavController().navigate(action)
            }
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            viewModel.fileDownloadedLiveData.observe(viewLifecycleOwner) {
                downloadBtn.setImageResource(R.drawable.done)
                linearLayout.visibility = View.VISIBLE
            }

            viewModel.errorDownloadLiveData.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}