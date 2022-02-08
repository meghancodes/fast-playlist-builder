package com.example.fastplaylistbuilder.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fastplaylistbuilder.R
import com.example.fastplaylistbuilder.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var vm: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentSearchBinding>(inflater,
            R.layout.fragment_search, container, false)

        // attach the vm
        vm = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.vm = vm

        // set the lifecycle owner so we can listen for Livedata changes
        binding.lifecycleOwner = this

        binding.searchButton.setOnClickListener {
            val searchQuery = binding.searchInput.text.toString()
            // pass query as arg to result fragment
            val action = SearchFragmentDirections.actionSearchToResults().setSearchQuery(searchQuery)
            findNavController().navigate(action)
        }

        return binding.root
    }
}