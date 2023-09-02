package com.example.recycler.photolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.bumptech.glide.Glide
import com.example.recycler.R
import com.example.recycler.databinding.FragmentSecondBinding

private const val ARG_PARAM1 = "param1"

class SecondFragment : Fragment() {
    private var param1: String? = null

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = param1?.removePrefix("http://mars.jpl.nasa.gov")
        Glide.with(binding.largeImage)
            .load("https://mars.nasa.gov$url")
            .into(binding.largeImage)

        binding.largeImage.setOnClickListener {
            parentFragmentManager.commit {
                replace<MainFragment>(R.id.fragment_container)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}