package com.example.rickandmortyapp.ui.characters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.RickAndMortyApplication
import com.example.rickandmortyapp.base.BaseFragment
import com.example.rickandmortyapp.databinding.FragmentCharactersBinding
import com.example.rickandmortyapp.utils.setSystemInserts
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


class CharactersFragment : BaseFragment() {

    @Inject
    internal lateinit var viewModelProvider: Provider<CharactersViewModel>
    private val viewModel by viewModels { viewModelProvider.get() }

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as RickAndMortyApplication)
            .appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharactersBinding.bind(view)

        initToolbar(binding.charactersToolbar)
        initCharactersList()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.characters.collect { pagingData ->
                    //viewModel.setContentState()
                    charactersAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun initCharactersList() {
        charactersAdapter = CharactersAdapter { navigateToCharacterDetails(it) }
        binding.charactersRetryButton.setOnClickListener { charactersAdapter.retry() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                charactersAdapter.loadStateFlow.collect {
                    binding.charactersProgressBar.isVisible = it.refresh is LoadState.Loading
                    binding.charactersRetryButton.isVisible = it.refresh is LoadState.Error
                    binding.charactersErrorText.isVisible = it.refresh is LoadState.Error
                    if (it.refresh is LoadState.Error) {
                        binding.charactersErrorText.text =
                            (it.refresh as LoadState.Error).error.localizedMessage
                    }
                }
            }
        }

        with(binding.charactersList) {
            setSystemInserts(bottom = true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = charactersAdapter.withLoadStateHeaderAndFooter(
                header = CharactersLoadStateAdapter { charactersAdapter.retry() },
                footer = CharactersLoadStateAdapter { charactersAdapter.retry() }
            )
        }
    }

    private fun navigateToCharacterDetails(id: Int) {
        if (id > 0) {
            val action = CharactersFragmentDirections
                .actionCharactersFragmentToCharacterDetailsFragment(id)
            findNavController().navigate(action)
        }
    }
}