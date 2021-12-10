package com.bakigoal.guessit.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.bakigoal.guessit.R
import com.bakigoal.guessit.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )

        Log.i("GameFragment", "Called ViewModelProvider(this).get")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        viewModel.score.observe(viewLifecycleOwner, { updateScoreText(it) })
        viewModel.word.observe(viewLifecycleOwner, { updateWordText(it) })

        setListeners()

        return binding.root

    }

    private fun setListeners() {
        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
            if (viewModel.gameFinished) gameFinished()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
            if (viewModel.gameFinished) gameFinished()
        }
    }

    private fun gameFinished() =
        findNavController(this).navigate(GameFragmentDirections.actionGameToScore(viewModel.score.value as Int))

    private fun updateWordText(word: String) {
        binding.wordText.text = word
    }

    private fun updateScoreText(newScore: Int) {
        binding.scoreText.text = newScore.toString()
    }
}
