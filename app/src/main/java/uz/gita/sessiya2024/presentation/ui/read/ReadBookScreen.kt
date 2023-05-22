package uz.gita.sessiya2024.presentation.ui.read

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.sessiya2024.R
import uz.gita.sessiya2024.databinding.ScreenReadBookBinding
import uz.gita.sessiya2024.presentation.ui.read.viewmodel.ReadViewModel
import uz.gita.sessiya2024.presentation.ui.read.viewmodel.ReadViewModelImpl
import java.io.File
import kotlin.properties.Delegates

@AndroidEntryPoint
class ReadBookScreen:
    com.github.barteksc.pdfviewer.listener.OnPageChangeListener, Fragment(R.layout.screen_read_book) {
    private val binding by viewBinding(ScreenReadBookBinding::bind)
    private val args: ReadBookScreenArgs by navArgs()
    var pageNumber= 0
    var totalPage= 0

    private val viewModel: ReadViewModel by viewModels<ReadViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        openBook()
        val b = requireActivity().filesDir
        b?.let {
            val file = File(it, "${args.data.reference}.pdf")

            if (file.exists()) {

                binding.pageInfo.text = "$pageNumber/$totalPage"
                binding.pdfView.fromFile(file)
                    .enableSwipe(true)
                    .defaultPage(pageNumber)
                    .swipeHorizontal(false)
                    .pageSnap(true)
                    .autoSpacing(true)
                    .pageFling(true)
                    .enableDoubletap(true)
                    .enableAnnotationRendering(false)
                    .scrollHandle(DefaultScrollHandle(requireContext()))
                    .onPageChange(this)
                    .enableAntialiasing(true)
                    .spacing(10)
                    .nightMode(false)
                    .pageFitPolicy(FitPolicy.BOTH)
                    .load()
            } else {
                Toast.makeText(requireContext(), "Book is not downloaded", Toast.LENGTH_SHORT)
                    .show()
            }
        }
       binding.btnBack.setOnClickListener {
           findNavController().navigateUp()
       }
    }

    private fun openBook() {
        binding.pdfView.fromFile(File(requireContext().filesDir, "${args.data.reference}.pdf")).load()
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveBookAsRead(args.data, binding.pdfView.currentPage)
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        binding.pageInfo.text = String.format("%s / %s", page + 1, pageCount)
        pageNumber = page
        totalPage = pageCount
    }
}