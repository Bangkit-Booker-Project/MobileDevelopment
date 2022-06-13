package com.example.capstonesean.homePage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonesean.R
import com.example.capstonesean.data.Fetch
import com.example.capstonesean.data.response.BookItem
import com.example.capstonesean.databinding.FragmentMyBooksBinding
import com.example.capstonesean.model.UserModel
import com.example.capstonesean.model.UserPreferences
import com.example.capstonesean.viewmodelfactory.BookModelFactory

class MyBooksFragment : Fragment() {

    private lateinit var binding: FragmentMyBooksBinding
    private lateinit var userModel: UserModel
    private lateinit var userPreferences: UserPreferences
    private val viewModel: HomepageViewModel by viewModels {
        BookModelFactory.getInstance()
    }

    override fun onResume() {
        super.onResume()
        val genre = resources.getStringArray(R.array.newest)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genre)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyBooksBinding.inflate(layoutInflater)
        getData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvMyBooks.layoutManager = layoutManager
        binding.progressBar.visibility = View.INVISIBLE
//        binding.shimmerLayout.visibility = View.GONE
        binding.swiperefresh.setOnRefreshListener {
            getData()
        }

        binding.textNoRated.visibility = View.INVISIBLE

        getData()
    }

    private fun getData(){
        userPreferences = UserPreferences(requireActivity())
        userModel = userPreferences.getUser()
        viewModel.getMyBooks(userModel.token.toString()).observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Fetch.Loading -> {
                        binding.swiperefresh.isRefreshing = true
                        binding.progressBar.visibility = View.GONE
                    }
                    is Fetch.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.swiperefresh.isRefreshing = false
                        val data = result.data
                        setView(data)
                    }
                    is Fetch.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.swiperefresh.isRefreshing = false
                        Toast.makeText(
                            activity,
                            result.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun setView(data: List<BookItem>){
        val adapter = MyBooksAdapter(data)
        val tes = data.map { it.userRating }
        Log.i("SUM", tes.sum().toString())
        if (tes.sum() == 15.030001.toFloat()){
            binding.textNoRated.visibility = View.VISIBLE

        } else {
            binding.textNoRated.visibility = View.INVISIBLE
        }
        Log.i("mybooksdata", tes.toString())
        binding.rvMyBooks.adapter = adapter
    }

}