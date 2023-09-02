package com.example.yandexnav.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.yandexnav.R
import com.example.yandexnav.api.OpenStreetMapApiService
import com.example.yandexnav.databinding.FragmentSightBinding
import kotlinx.coroutines.launch

private const val API_KEY = "a9489bda-d071-4d55-9a12-067fae42f626"
class SightFragment : Fragment() {

    private lateinit var binding: FragmentSightBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSightBinding.inflate(inflater, container, false)



        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_sightFragment_to_mapFragment)
        }
        return binding.root
    }

}