package com.example.binscope.presentation.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.binscope.R
import com.example.binscope.databinding.DetailsFragmentBinding
import com.example.binscope.domain.model.CardData
import com.example.binscope.presentation.state.AppError
import com.example.binscope.presentation.state.UiState
import com.example.binscope.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels(ownerProducer = { requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is UiState.Empty -> showEmpty()
                        is UiState.Loading -> showLoading()
                        is UiState.Success -> showContent(state.data)
                        is UiState.Error -> showError(state.error)
                    }
                }
            }
        }
    }

    private fun showEmpty() {
        binding.progressBar.visibility = View.GONE
        binding.detailsContainer.visibility = View.GONE
        binding.errorState.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.detailsContainer.visibility = View.GONE
        binding.errorState.visibility = View.GONE
    }

    private fun showContent(card: CardData) {
        binding.progressBar.visibility = View.GONE
        binding.detailsContainer.visibility = View.VISIBLE
        binding.errorState.visibility = View.GONE
        showCardData(card)
    }

    fun showCardData(card: CardData) {
        binding.schemeTextView.text = getString(R.string.scheme, card.scheme)
        binding.typeTextView.text = getString(R.string.type, card.type)
        binding.brandTextView.text = getString(R.string.brand, card.brand)
        binding.prepaidTextView.text = getString(R.string.prepaid, card.prepaid)
        binding.countryTextView.text =
            getString(R.string.country, card.countryName, card.latitude, card.longitude)
        binding.bankNameTextView.text = getString(R.string.bank_name, card.bankName)
        binding.bankCityTextView.text = getString(R.string.bank_city, card.bankCity)
        binding.bankUrlTextView.text = getString(R.string.bank_url, card.bankUrl)
        binding.bankPhoneTextView.text = getString(R.string.bank_phone, card.bankPhone)

        setUpListeners(card)
    }

    private fun showError(error: AppError) {
        binding.progressBar.visibility = View.GONE
        binding.detailsContainer.visibility = View.GONE
        binding.errorState.visibility = View.VISIBLE
        binding.retryButton.setOnClickListener {
            viewModel.retry()
        }
        when (error) {
            is AppError.NetworkError -> binding.errorTextView.text =
                getString(R.string.network_error_message)

            is AppError.ServerError -> binding.errorTextView.text =
                getString(R.string.server_error_message)

            is AppError.UnknownError -> binding.errorTextView.text =
                getString(R.string.unknown_error_message)

            else -> binding.errorState.visibility = View.GONE
        }
    }

    private fun setUpListeners(card: CardData) {
        if (card.bankUrl != getString(R.string.no_data)) {
            binding.bankUrlTextView.setOnClickListener {
                openLink(card.bankUrl)
            }
        }

        if (card.bankPhone != getString(R.string.no_data)) {
            binding.bankPhoneTextView.setOnClickListener {
                openTelephone(card.bankPhone)
            }
        }

        if (card.latitude != getString(R.string.no_data) &&
            card.longitude != getString(R.string.no_data)
        ) {
            binding.countryTextView.setOnClickListener {
                openMap(card.latitude, card.longitude)
            }
        }
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun openTelephone(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        startActivity(intent)
    }

    private fun openMap(latitude: String, longitude: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:$latitude,$longitude"))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}