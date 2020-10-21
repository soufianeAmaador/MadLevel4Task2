package com.example.madlevel4task2v20

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HistoryFragment : Fragment() {

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())
        getGameHistoryFromDatabase()

        initRv()


    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete -> {
                deleteHistory()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteHistory(){
        mainScope.launch {
            withContext(Dispatchers.IO){
                gameRepository.deleteAllGames()
            }
            getGameHistoryFromDatabase()
        }
    }
    
    private fun getGameHistoryFromDatabase(){
        mainScope.launch { 
            val gameHistory = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            println("DISPATCHERS GAMES AMOUNT: " + gameRepository.getAllGames().size)

            this@HistoryFragment.games.clear()
            this@HistoryFragment.games.addAll(gameHistory)
            this@HistoryFragment.gameAdapter.notifyDataSetChanged()
        }
    }

    private fun initRv(){
        rvHistory.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvHistory.adapter = gameAdapter
        rvHistory.addItemDecoration(
            DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL)
        )
    }


}