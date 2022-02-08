package com.example.fastplaylistbuilder.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fastplaylistbuilder.adapters.MovieAdapter
import com.example.fastplaylistbuilder.R
import com.example.fastplaylistbuilder.databinding.FragmentPlaylistBinding

class PlaylistFragment : Fragment() {
    private lateinit var vm: PlaylistViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentPlaylistBinding>(inflater,
            R.layout.fragment_playlist, container, false)

        // attach the vm
        val factory = PlaylistViewModelFactory(requireActivity().application)
        vm = ViewModelProvider(this, factory).get(PlaylistViewModel::class.java)
        binding.vm = vm

        // set the lifecycle owner so we can listen for Livedata changes
        binding.lifecycleOwner = this

        vm.populatePlaylist()

        vm.emptyPlaylist.observe(viewLifecycleOwner) {
            Toast.makeText(this.context, "Playlist is empty", Toast.LENGTH_LONG).show()
        }

        binding.adapter = MovieAdapter(listOf(), vm)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        vm.populatePlaylist()
    }
}