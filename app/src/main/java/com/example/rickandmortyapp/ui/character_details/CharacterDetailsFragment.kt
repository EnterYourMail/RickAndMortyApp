package com.example.rickandmortyapp.ui.character_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.RickAndMortyApplication
import com.example.rickandmortyapp.base.BaseFragment
import com.example.rickandmortyapp.databinding.FragmentCharacterDetailsBinding
import com.example.rickandmortyapp.model.CharacterDetails
import com.example.rickandmortyapp.utils.ScreenState
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharacterDetailsFragment : BaseFragment() {

    @Inject
    internal lateinit var viewModelProvider: CharacterDetailsViewModel.Factory
    private val viewModel by viewModels { viewModelProvider.get(args.id) }

    private val args by navArgs<CharacterDetailsFragmentArgs>()
    private lateinit var binding: FragmentCharacterDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as RickAndMortyApplication)
            .appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterDetailsBinding.bind(view)
        initToolbar(binding.charactersDetailsToolbar, false)

        binding.characterDetailsRetryButton.setOnClickListener { viewModel.retry() }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collect { observeScreenState(it) }
        }
    }

    private fun observeScreenState(screenState: ScreenState<CharacterDetails>) {
        with(binding) {
            characterDetailsProgressBar.isVisible = screenState is ScreenState.Loading
            characterDetailsErrorText.isVisible = screenState is ScreenState.Error
            characterDetailsRetryButton.isVisible = screenState is ScreenState.Error
            charactersDetailsImage.isVisible = screenState is ScreenState.Content
            charactersDetailsNameText.isVisible = screenState is ScreenState.Content
            charactersDetailsLocationText.isVisible = screenState is ScreenState.Content
            charactersDetailsTypeText.isVisible = screenState is ScreenState.Content
            charactersDetailsStatusText.isVisible = screenState is ScreenState.Content
            charactersDetailsEpisodesButton.isVisible = screenState is ScreenState.Content
            when (screenState) {
                is ScreenState.Error -> {
                    characterDetailsErrorText.text = getString(
                        R.string.error_caption,
                        screenState.exception.localizedMessage
                    )
                }

                is ScreenState.Loading -> Unit

                is ScreenState.Content -> {
                    Picasso.get().load(screenState.data.image).into(charactersDetailsImage)
                    charactersDetailsNameText.text = screenState.data.name
                    charactersDetailsLocationText.text = screenState.data.location.name
                    charactersDetailsTypeText.text = screenState.data.type
                    charactersDetailsStatusText.text = screenState.data.status
                    val episodesArray = screenState.data.episodesUrls.map {
                        it.substringAfter("/episode/", "")
                            .toInt()
                    }.toIntArray()
                    binding.charactersDetailsEpisodesButton.setOnClickListener {
                        navigateToEpisodes(episodesArray)
                    }
                }
            }
        }
    }

    private fun navigateToEpisodes(episodesArray: IntArray) {
        if (episodesArray.isNotEmpty()) {
            val action = CharacterDetailsFragmentDirections
                .actionCharacterDetailsFragmentToEpisodesFragment(episodesArray)
            findNavController().navigate(action)
        }
    }

}