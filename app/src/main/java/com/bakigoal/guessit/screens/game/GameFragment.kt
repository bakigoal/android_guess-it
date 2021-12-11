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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)

        Log.i("GameFragment", "Called ViewModelProvider(this).get")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel

        viewModel.score.observe(viewLifecycleOwner, { binding.scoreText.text = it.toString() })
        viewModel.word.observe(viewLifecycleOwner, { binding.wordText.text = it })
        viewModel.time.observe(viewLifecycleOwner, { binding.timerText.text = it })
        viewModel.gameFinished.observe(viewLifecycleOwner, { gameFinished ->
            if (gameFinished) {
                gameFinished()
                viewModel.onGameFinishComplete()
            }
        })

        return binding.root
    }

    private fun gameFinished() {
        val score = viewModel.score.value ?: 0
        val scoreDirection = GameFragmentDirections.actionGameToScore(score)
        findNavController(this).navigate(scoreDirection)
    }

}
