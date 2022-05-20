package com.example.rickandmortyapp.utils.depricated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.databinding.EpisodeItemBinding
import com.example.rickandmortyapp.model.Episode

class EpisodesAdapter(private val episodes: List<Episode>) :
    RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            EpisodeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    class EpisodeViewHolder(
        private val binding: EpisodeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episode) {
            binding.episodeItemNameText.text = episode.name
            binding.episodeItemAirDateText.text = episode.airDate
        }
    }
}

/*class Factory @Inject constructor(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == CharactersViewModel::class.java) {
                @Suppress("UNCHECKED_CAST")
                return CharactersViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }*/