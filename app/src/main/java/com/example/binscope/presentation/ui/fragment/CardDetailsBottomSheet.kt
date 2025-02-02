package com.example.binscope.presentation.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.binscope.R
import com.example.binscope.databinding.DetailsFragmentBinding
import com.example.binscope.domain.model.CardData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CardDetailsBottomSheet : BottomSheetDialogFragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_CARD_DATA = "cardData"

        fun newInstance(cardData: CardData): CardDetailsBottomSheet {
            val fragment = CardDetailsBottomSheet()
            val args = Bundle()
            args.putParcelable(ARG_CARD_DATA, cardData)
            fragment.arguments = args
            return fragment
        }
    }

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
        showCardData()
    }

    private fun showCardData() {
        val cardData = arguments?.getParcelable<CardData>(ARG_CARD_DATA)
        cardData?.let {
            with(binding) {
                detailsContainer.visibility = View.VISIBLE
                schemeTextView.text = getString(R.string.scheme, it.scheme)
                typeTextView.text = getString(R.string.type, it.type)
                brandTextView.text = getString(R.string.brand, it.brand)
                prepaidTextView.text = getString(R.string.prepaid, it.prepaid)
                countryTextView.text =
                    getString(R.string.country, it.countryName, it.latitude, it.longitude)
                bankNameTextView.text = getString(R.string.bank_name, it.bankName)
                bankCityTextView.text = getString(R.string.bank_city, it.bankCity)
                bankUrlTextView.text = getString(R.string.bank_url, it.bankUrl)
                bankPhoneTextView.text = getString(R.string.bank_phone, it.bankPhone)
            }
            setUpListeners(it)
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