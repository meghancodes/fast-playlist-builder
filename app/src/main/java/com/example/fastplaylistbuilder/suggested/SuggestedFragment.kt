package com.example.fastplaylistbuilder.suggested

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.fastplaylistbuilder.R
import com.example.fastplaylistbuilder.databinding.FragmentSuggestedBinding

class SuggestedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentSuggestedBinding>(inflater,
            R.layout.fragment_suggested, container, false)

        return binding.root
    }
}