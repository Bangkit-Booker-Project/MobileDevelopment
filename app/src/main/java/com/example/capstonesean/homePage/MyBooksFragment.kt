package com.example.capstonesean.homePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.capstonesean.R
import com.example.capstonesean.databinding.FragmentMyBooksBinding

class MyBooksFragment : Fragment() {
    private var _binding: FragmentMyBooksBinding? = null
    private val binding get() = _binding!!


    override fun onResume() {
        super.onResume()
        val genre = resources.getStringArray(R.array.newest)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genre)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMyBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}