package uz.gita.sessiya2024.presentation.ui.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.sessiya2024.R
import uz.gita.sessiya2024.data.model.MessageData
import uz.gita.sessiya2024.databinding.ScreenProfileBinding
import uz.gita.sessiya2024.presentation.ui.profile.viewmodel.ProfileViewModel
import uz.gita.sessiya2024.presentation.ui.profile.viewmodel.ProfileViewModelImpl
import java.net.NetworkInterface
import kotlin.properties.Delegates

@AndroidEntryPoint
class ProfileScreen : Fragment(R.layout.screen_profile) {
    private val binding by viewBinding(ScreenProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels<ProfileViewModelImpl>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getOwnerInfo()


    }

    @RequiresApi(Build.VERSION_CODES.O)
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
        viewModel.messageString.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.profilLiveData.observe(viewLifecycleOwner) { data ->
            binding.profilDescription.text = data.description
            binding.profilTitle.text = data.title

            Glide.with(requireContext())
                .load(data.image_url)
                .into(binding.profileImage)
            binding.apply {
                profilTelegram.setOnClickListener { view ->
                    val uri = Uri.parse(data.telegram)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                profilInstagram.setOnClickListener { view ->
                    val uri = Uri.parse(data.instagram)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }

                profileSendMessage.setOnClickListener {
                    if (editTextText.text.isNullOrEmpty() || editTextText2.text.isNullOrEmpty()){
                        Toast.makeText(requireContext(), "Ma'lumotlarni to'liq kiriting", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    val mac = getMacAddress()
                    val name = editTextText.text.toString()
                    val comment = editTextText2.text.toString()

                    if (true){
                        viewModel.addComment(MessageData(mac, name, comment))
                        profileSendMessage.visibility = View.GONE
                        editTextText.isEnabled = false
                        editTextText2.isEnabled = false
                        editTextText.isCursorVisible = false
                        editTextText2.isCursorVisible = false
                        viewModel.boolean = true
                    }
                }
            }
        }


        binding.apply {
            if (viewModel.boolean){
                editTextText.isEnabled = false
                editTextText2.isEnabled = false
                editTextText.isCursorVisible = false
                editTextText2.isCursorVisible = false
                profileSendMessage.visibility = View.GONE
            }
        }

    }
    fun getMacAddress(): String {
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()

        while (networkInterfaces.hasMoreElements()) {
            val networkInterface = networkInterfaces.nextElement()
            val macAddress = networkInterface.hardwareAddress

            if (macAddress != null && macAddress.isNotEmpty()) {
                val stringBuilder = StringBuilder()

                for (byte in macAddress) {
                    stringBuilder.append(String.format("%02X:", byte))
                }

                if (stringBuilder.isNotEmpty()) {
                    stringBuilder.deleteCharAt(stringBuilder.length - 1)
                }

                return stringBuilder.toString()
            }
        }
        return "${Build.BOOTLOADER} va  ${Build.FINGERPRINT }  qurilma..."
    }
}