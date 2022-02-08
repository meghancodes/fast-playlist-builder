package com.example.fastplaylistbuilder.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.fastplaylistbuilder.databinding.FragmentResultsBinding
import com.example.fastplaylistbuilder.results.AlertDialogServerAction.NoResults
import com.example.fastplaylistbuilder.results.AlertDialogServerAction.ServerFailure
import com.example.fastplaylistbuilder.results.AlertDialogServerAction.DbAddFailure
import com.example.fastplaylistbuilder.adapters.MovieAdapter
import com.example.fastplaylistbuilder.R

class ResultsFragment : Fragment() {
    private lateinit var vm: ResultsViewModel
    private val args: ResultsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentResultsBinding>(inflater,
            R.layout.fragment_results, container, false)

        // attach the vm
        val factory = ResultsViewModelFactory(requireActivity().application)
        vm = ViewModelProvider(this, factory).get(ResultsViewModel::class.java)
        binding.vm = vm

        // set the lifecycle owner so we can listen for Livedata changes
        binding.lifecycleOwner = this

        // set the recyclerview adapter
        binding.adapter = MovieAdapter(listOf(), vm)

        vm.populateMovieList(args.searchQuery)

        vm.alertDialogAction.observe(viewLifecycleOwner) {
            when (it) {
                is NoResults -> showServerResponseDialog("No results found. Please try a different search.")
                is ServerFailure -> showServerResponseDialog("Server request failed. Please try again later.")
                is DbAddFailure -> showServerResponseDialog("Failed to add to your playlist. Please try again later.")
            }
        }
        vm.movieClicked.observe(viewLifecycleOwner) {
            Toast.makeText(this.context, "You've added $it to your playlist!\n " +
                    "My favorite color is black.", Toast.LENGTH_LONG).show()
        }
        return binding.root
    }

    private fun showServerResponseDialog(msg: String) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setMessage(msg)
                setPositiveButton("OK"
                ) { _, _ ->
                    /* no-op */
                }
            }
            builder.create()
        }
        alertDialog?.show()
    }
}