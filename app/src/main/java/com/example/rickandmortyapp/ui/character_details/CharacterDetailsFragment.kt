package com.example.rickandmortyapp.ui.character_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.RickAndMortyApplication
import com.example.rickandmortyapp.base.BaseFragment
import com.example.rickandmortyapp.databinding.FragmentCharacterDetailsBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharacterDetailsFragment : BaseFragment() {

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var viewModelFactory: CharacterDetailsViewModel.AssistedViewModelFactory
    private val args by navArgs<CharacterDetailsFragmentArgs>()
    private val viewModel: CharacterDetailsViewModel by viewModels {
        viewModelFactory.create(args.id)
    }
    private lateinit var binding: FragmentCharacterDetailsBinding


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
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterDetailsBinding.bind(view)
        initToolbar(binding.charactersDetailsToolbar, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .filter{ it.id > -1 }.collect { viewState ->
                    with(binding) {
                        picasso.load(viewState.image).into(charactersDetailsImage)
                        charactersDetailsNameText.text = viewState.name
                        charactersDetailsLocationText.text = viewState.location.name
                        charactersDetailsTypeText.text = viewState.type
                        charactersDetailsStatusText.text = viewState.status
                        val arrayEpisodes = viewState.episodesUrls.joinToString(",") {
                            it.substringAfter("/episode/", "")
                        }
                        binding.charactersDetailsEpisodesButton.setOnClickListener {
                            navigateToEpisodes(arrayEpisodes)
                        }
                    }
                }
        }
    }

    private fun navigateToEpisodes(arrayEpisodes: String) {
        if (arrayEpisodes.isNotEmpty()) {
            val action = CharacterDetailsFragmentDirections
                .actionCharacterDetailsFragmentToEpisodesFragment(arrayEpisodes)
            findNavController().navigate(action)
        }
    }

}