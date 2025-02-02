package com.example.binscope.presentation.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.binscope.R
import com.example.binscope.databinding.ActivityHistoryBinding
import com.example.binscope.domain.model.CardData
import com.example.binscope.presentation.adapter.HistoryAdapter
import com.example.binscope.presentation.state.UiState
import com.example.binscope.presentation.ui.fragment.CardDetailsBottomSheet
import com.example.binscope.presentation.viewmodel.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var historyAdapter: HistoryAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, HistoryActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        initObservers()
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter(
            showDetails = { cardData ->
                val bottomSheet = CardDetailsBottomSheet.newInstance(cardData)
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            }
        )
        binding.historyRecyclerView.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(this@HistoryActivity)
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is UiState.Loading -> showLoading()
                        is UiState.Empty -> showEmpty()
                        is UiState.Success -> showContent(state.data)
                        is UiState.Error -> showError()
                        else -> false
                    }
                }
            }
        }
    }

    private fun showError() {
        binding.messageTextView.text = getString(R.string.unknown_error_message)
        binding.historyRecyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.emptyState.visibility = View.VISIBLE
        binding.goToMainButton.setOnClickListener {
            finish()
        }
    }

    private fun showLoading() {
        binding.historyRecyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.emptyState.visibility = View.GONE
    }

    private fun showContent(data: List<CardData>) {
        historyAdapter.setItems(data)
        binding.historyRecyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.emptyState.visibility = View.GONE
    }

    private fun showEmpty() {
        binding.historyRecyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.emptyState.visibility = View.VISIBLE
        binding.goToMainButton.setOnClickListener {
            finish()
        }
    }
}