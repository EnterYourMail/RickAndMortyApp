package com.example.rickandmortyapp.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.CharactersLoadStateItemBinding
import com.example.rickandmortyapp.utils.setSystemInserts

class CharactersLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<CharactersLoadStateAdapter.CharactersLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: CharactersLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CharactersLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.characters_load_state_item, parent, false)
        val binding = CharactersLoadStateItemBinding.bind(view)
        return CharactersLoadStateViewHolder(binding, retry)
    }


    class CharactersLoadStateViewHolder(
        private val binding: CharactersLoadStateItemBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.charactersLoadStateRetryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.charactersLoadStateErrorText.text =
                    loadState.error.localizedMessage
            }
            binding.charactersLoadStateProgressBar.isVisible = loadState is LoadState.Loading
            binding.charactersLoadStateRetryButton.isVisible = loadState is LoadState.Error
            binding.charactersLoadStateErrorText.isVisible = loadState is LoadState.Error
            binding.root.setSystemInserts(bottom = true)
        }

    }


}