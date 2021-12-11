package com.bakigoal.guessit.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.bakigoal.guessit.R
import com.bakigoal.guessit.databinding.GameFragmentBinding
import com.bakigoal.guessit.util.BuzzType

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
        viewModel.gameFinishedEvent.observe(viewLifecycleOwner, this::onGameFinishedEvent)
        viewModel.buzzTypeEvent.observe(viewLifecycleOwner, this::onBuzzTypeEvent)

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    private fun onBuzzTypeEvent(buzzType: BuzzType) {
        if (buzzType != BuzzType.NO_BUZZ) {
            buzz(buzzType.pattern)
            viewModel.onBuzzComplete()
        }
    }

    private fun onGameFinishedEvent(gameFinished: Boolean) {
        if (gameFinished) {
            gameFinished()
            viewModel.onGameFinishComplete()
        }
    }

    private fun gameFinished() {
        val score = viewModel.score.value ?: 0
        val scoreDirection = GameFragmentDirections.actionGameToScore(score)
        findNavController(this).navigate(scoreDirection)
    }


    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()

        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }

}
