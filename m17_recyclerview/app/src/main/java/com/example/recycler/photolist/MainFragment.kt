package com.example.recycler.photolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.recycler.R
import com.example.recycler.databinding.FragmentMainBinding
import com.example.recycler.models.Photo
import com.example.recycler.pagedlist.PhotoPagedListAdapter
import com.example.recycler.pagedlist.PhotoPagedViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PhotoPagedViewModel by viewModels()


    private val pagedAdapter = PhotoPagedListAdapter { photo -> onItemClick(photo) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.adapter = pagedAdapter
        viewModel.pagedPhotos.onEach {
            pagedAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onItemClick(item: Photo) {
        val bundle = Bundle().apply {
            putString("param1", item.img_src)
        }
        parentFragmentManager.commit {
            replace<SecondFragment>(containerViewId = R.id.fragment_container, args = bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}