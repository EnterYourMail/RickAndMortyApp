package com.example.rickandmortyapp.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.CharacterItemBinding
import com.example.rickandmortyapp.model.Character
import com.squareup.picasso.Picasso

class CharactersAdapter(private val onClick: (Int) -> Unit) :
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
            )
        )
    }

    inner class CharacterViewHolder(
        private val binding: CharacterItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character, onClick: (Int) -> Unit) = with(binding) {
            //TODO("Put screen metric to Picasso resize parameters.")
            Picasso.get().load(character.image)
                .placeholder(R.drawable.ic_baseline_downloading_96)
                .error(R.drawable.ic_baseline_error_outline_96)
                //.resize(300, 300)
                //.fit().centerCrop()
                .into(characterItemImage)
            characterItemNameText.text = character.name
            root.setOnClickListener { onClick.invoke(character.id) }
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