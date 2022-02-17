package com.example.rickandmortyapp.ui.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.RickAndMortyApplication
import com.example.rickandmortyapp.base.BaseFragment
import com.example.rickandmortyapp.databinding.FragmentEpisodesBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesFragment : BaseFragment() {

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var viewModelFactory: EpisodesViewModel.AssistedViewModelFactory
    private val args by navArgs<EpisodesFragmentArgs>()
    private val viewModel: EpisodesViewModel by viewModels {
        viewModelFactory.create(args.stringEpisodes)
    }
    private lateinit var binding: FragmentEpisodesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as RickAndMortyApplication)
            .appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_episodes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEpisodesBinding.bind(view)
        initToolbar(binding.episodesToolbar, false)
        binding.episodesList.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collect { episodes ->
                    binding.episodesList.adapter = EpisodesAdapter(episodes)
                }
        }
    }

}