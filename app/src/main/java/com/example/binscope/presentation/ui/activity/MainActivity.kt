package com.example.binscope.presentation.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.binscope.R
import com.example.binscope.databinding.ActivityMainBinding
import com.example.binscope.presentation.state.AppError
import com.example.binscope.presentation.state.UiState
import com.example.binscope.presentation.ui.fragment.DetailsFragment
import com.example.binscope.presentation.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowInsetsListener()
        addFragment()
        setupEditText()
    }

    private fun setWindowInsetsListener() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, DetailsFragment())
            .commit()
    }

    private fun setupEditText() {
        binding.binTextInputLayout.setEndIconOnClickListener {
            val bin = binding.binEditText.text.toString()
            viewModel.loadCard(bin)
        }

        binding.binEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.binTextInputLayout.error = null
                binding.binTextInputLayout.isErrorEnabled = false
            }
        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is UiState.Error -> if (state.error is AppError.ValidationError) {
                            binding.binTextInputLayout.error =
                                getString(R.string.validation_error_message)
                        }

                        else -> false
                    }
                }
            }
        }
    }
}