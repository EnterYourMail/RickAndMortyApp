package com.example.rickandmortyapp.ui.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.RickAndMortyApplication
import com.example.rickandmortyapp.base.BaseFragment
import com.example.rickandmortyapp.databinding.FragmentEpisodesBinding
import com.example.rickandmortyapp.model.Episode
import com.example.rickandmortyapp.utils.ScreenState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesFragment : BaseFragment() {

    @Inject
    internal lateinit var viewModelProvider: EpisodesViewModel.Factory
    private val viewModel by viewModels { viewModelProvider.get(args.episodesArray) }

    private val args by navArgs<EpisodesFragmentArgs>()
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
        binding.episodesList.setInserts(bottom = true)
        //TODO("Replace EpisodesAdapter to groupie")
        binding.episodesList.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collect { observeScreenState(it) }
        }
    }

    private fun observeScreenState(screenState: ScreenState<List<Episode>>) {
        with(binding) {
            episodesProgressBar.isVisible = screenState is ScreenState.Loading
            episodesErrorText.isVisible = screenState is ScreenState.Error
            episodesRetryButton.isVisible = screenState is ScreenState.Error
            episodesList.isVisible = screenState is ScreenState.Content

            when (screenState) {
                is ScreenState.Loading -> Unit
                is ScreenState.Error -> {
                    episodesErrorText.text = getString(
                        R.string.error_caption,
                        screenState.exception.localizedMessage
                    )
                }
                is ScreenState.Content -> {
                    episodesList.adapter = EpisodesAdapter(screenState.data)
                }
            }
        }
    }

}