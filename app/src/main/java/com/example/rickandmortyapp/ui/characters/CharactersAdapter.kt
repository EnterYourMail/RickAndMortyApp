package com.example.rickandmortyapp.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.databinding.CharacterItemBinding
import com.example.rickandmortyapp.model.Character
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CharactersAdapter @Inject constructor(private val picasso: Picasso) :
    PagingDataAdapter<Character, CharactersAdapter.CharacterViewHolder>(CharactersComparator) {

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        character?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return  CharacterViewHolder(
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
        fun bind(character: Character) = with(binding) {
            //TODO("Put screen metric to Picasso resize parameters.")
            picasso.load(character.image).resize(100, 100)
                .centerCrop().into(characterItemImage)
            characterItemNameText.text = character.name

        }
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