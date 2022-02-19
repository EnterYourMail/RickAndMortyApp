package com.example.rickandmortyapp.ui.characters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.RickAndMortyApplication
import com.example.rickandmortyapp.base.BaseFragment
import com.example.rickandmortyapp.databinding.FragmentCharactersBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


class CharactersFragment : BaseFragment() {

/*    @Inject
    internal lateinit var viewModelFactory: CharactersViewModel.Factory
    private val viewModel by viewModels<CharactersViewModel> { viewModelFactory }*/
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
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharactersBinding.bind(view)

        initToolbar(binding.charactersToolbar, false)
        initCharacterList(binding.charactersList)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.characters.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collect { pagingData ->
                    charactersAdapter.submitData(pagingData)
                }
        }

    }

    private fun initCharacterList(charactersList: RecyclerView) {
        charactersList.setInserts(bottom=true)
        charactersAdapter = CharactersAdapter { navigateToCharacterDetails(it) }

        charactersList.apply {
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