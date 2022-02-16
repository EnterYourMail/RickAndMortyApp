package com.example.rickandmortyapp.ui.characters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.RickAndMortyApplication
import com.example.rickandmortyapp.base.BaseFragment
import com.example.rickandmortyapp.databinding.FragmentCharactersBinding
import javax.inject.Inject


class CharactersFragment : BaseFragment() {

    @Inject
    lateinit var charactersAdapter: CharactersAdapter
    @Inject
    lateinit var viewModelFactory: CharactersViewModel.Factory
    private val viewModel: CharactersViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentCharactersBinding


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
        binding.charactersList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = charactersAdapter
        }
    }
}