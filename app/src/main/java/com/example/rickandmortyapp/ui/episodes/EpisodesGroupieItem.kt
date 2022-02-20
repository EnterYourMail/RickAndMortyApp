package com.example.rickandmortyapp.ui.episodes

import android.view.View
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.EpisodeItemBinding
import com.example.rickandmortyapp.model.Episode
import com.xwray.groupie.viewbinding.BindableItem

class EpisodesGroupieItem(private val episode: Episode): BindableItem<EpisodeItemBinding>() {
    override fun initializeViewBinding(view: View): EpisodeItemBinding {
        return EpisodeItemBinding.bind(view)
    }

    override fun bind(binding: EpisodeItemBinding, position: Int) {
        binding.episodeItemNameText.text = episode.name
        binding.episodeItemAirDateText.text = episode.airDate
    }

    override fun getLayout(): Int {
        return R.layout.episode_item
    }
}