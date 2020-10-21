package com.example.madlevel4task2v20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.madlevel4task2v20.databinding.FragmentGameBinding



import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {

    private lateinit var rockId: Number
    private lateinit var paperId: Number
    private lateinit var scissorId: Number
    private lateinit var result: Result

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var binding: FragmentGameBinding

    private var win = 0
    private var draw = 0
    private var lose = 0


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_game, container, false)
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentGameBinding.inflate(layoutInflater)
        gameRepository = GameRepository(requireContext())

        countStats()


        rockId = resources.getIdentifier("rock", "drawable", context?.packageName)
        paperId = resources.getIdentifier("paper", "drawable", context?.packageName)
        scissorId = resources.getIdentifier("scissors", "drawable", context?.packageName)

        IBrock.setOnClickListener{
            val computerImage = computerDraw()
            val timestamp = Date()
            val result = resultDeterminer(rockId, computerImage)
            ivResult.text = result.result
            onAddHistory(timestamp,result,computerImage,rockId)
        }

        IBpaper.setOnClickListener {
            val computerImage = computerDraw()
            val timestamp = Date()
            val result = resultDeterminer(R.drawable.paper, computerImage)
            ivResult.text = result.result
            onAddHistory(timestamp,result,computerImage,paperId)
        }

        IBscissors.setOnClickListener {
            val computerImage = computerDraw()
            val timestamp = Date()
            val result = resultDeterminer(R.drawable.scissors, computerImage)
            ivResult.text = result.result
            onAddHistory(timestamp,result,computerImage,scissorId)
        }
    }

    private fun resultDeterminer(userImage: Number, computerImage: Number): Result{

        ivComputer.setImageResource(computerImage as Int)
        ivUser.setImageResource(userImage as Int)


        if (computerImage == userImage){
            result = Result.DRAW
            draw++
        }else if (computerImage == rockId){
            if (userImage == scissorId) {
                result = Result.COMPUTER_WINS
                lose++
            }
            else if (userImage == paperId) {
                result = Result.USER_WINS
                win++
            }
        }else if (computerImage == paperId){
            if (userImage == rockId) {
                result = Result.COMPUTER_WINS
                lose++
            }
            else if (userImage == scissorId) {
                Result.USER_WINS
                win++
            }

        }else if (computerImage == scissorId){
            if (userImage == paperId) {
                result = Result.COMPUTER_WINS
                lose++
            }
            else if (userImage == rockId) {
                result = Result.USER_WINS
                win++
            }
        }
        return result
    }

    private fun computerDraw(): Number {
        val images = arrayListOf<Number>(rockId,paperId,scissorId)
        return images[(0..2).random()]
    }

    private fun onAddHistory(timestamp: Date, result: Result, computerImage: Number, userImage: Number){

        mainScope.launch {
            val game = Game(timestamp,result,computerImage,userImage)

            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }
        countStats()
    }

    private fun countStats(){
        mainScope.launch {
            withContext(Dispatchers.IO){
                draw = gameRepository.countDraw()
                lose = gameRepository.countLosses()
                win = gameRepository.countWinnings()

            }
            statisticsValue.text = getString(R.string.sub_statistics,win,draw,lose)

        }

    }



}