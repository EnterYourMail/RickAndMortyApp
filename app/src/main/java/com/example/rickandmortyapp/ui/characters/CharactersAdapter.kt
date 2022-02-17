package com.example.rickandmortyapp.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.databinding.CharacterItemBinding
import com.example.rickandmortyapp.model.Character
import com.squareup.picasso.Picasso
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CharactersAdapter @AssistedInject constructor(
    private val picasso: Picasso,
    @Assisted private val onClick: (Int) -> Unit
) :
    PagingDataAdapter<Character, CharactersAdapter.CharacterViewHolder>(CharactersComparator) {

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        character?.let { holder.bind(it, onClick) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            binding = CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            picasso = picasso
        )
    }

    inner class CharacterViewHolder(
        private val binding: CharacterItemBinding,
        private val picasso: Picasso
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character, onClick: (Int) -> Unit) = with(binding) {
            //TODO("Put screen metric to Picasso resize parameters.")
            picasso.load(character.image).resize(300, 300)
                .centerCrop().into(characterItemImage)
            characterItemNameText.text = character.name
            root.setOnClickListener { onClick.invoke(character.id) }
        }
    }

    @AssistedFactory
    interface AssistedAdapterFactory {
        fun create(onClick: (Int) -> Unit): CharactersAdapter
    }

    object CharactersComparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}