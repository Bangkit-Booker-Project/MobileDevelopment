package com.example.capstonesean.homePage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.capstonesean.booklayout.BookLayoutActivity
import com.example.capstonesean.data.Fetch
import com.example.capstonesean.data.response.BookItem
import com.example.capstonesean.databinding.FragmentForYouBinding
import com.example.capstonesean.model.UserModel
import com.example.capstonesean.model.UserPreferences
import com.example.capstonesean.viewmodelfactory.BookModelFactory


class ForYouFragment : Fragment() {

    private var _binding: FragmentForYouBinding? = null
    private val binding get() = _binding!!
    private lateinit var userModel: UserModel
    private lateinit var userPreferences: UserPreferences
    private val viewModel: HomepageViewModel by viewModels {
        BookModelFactory.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForYouBinding.inflate(inflater, container, false)

        userPreferences = UserPreferences(requireActivity())
        userModel = userPreferences.getUser()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvForYou.setLayoutManager(layoutManager)
        binding.swiperefresh.setOnRefreshListener {
            getData()
        }

        getData()
    }

    private fun getData(){
        viewModel.getRecommendedBooks(userModel.token.toString()).observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Fetch.Loading -> {
                        binding.shimmerLayout.visibility = View.VISIBLE
                        binding.rvForYou.visibility = View.GONE
                    }
                    is Fetch.Success -> {
                        binding.shimmerLayout.visibility = View.GONE
                        binding.rvForYou.visibility = View.VISIBLE
                        binding.swiperefresh.isRefreshing = false
                        val data = result.data
                        setView(data)
                    }
                    is Fetch.Error -> {
                        binding.shimmerLayout.visibility = View.GONE
                        binding.swiperefresh.isRefreshing = false
                        Toast.makeText(
                            activity,
                            result.error,
                            Toast.LENGTH_LONG
                        ).show()
                        AlertDialog.Builder(requireActivity()).apply {
                            setTitle("Error " + result.error)
                            setPositiveButton("Retry") { _, _ ->
                                getData()
                            }
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }

    private fun setView(bookItem: List<BookItem>){
        val adapter = BookListAdapter(bookItem)
        binding.rvForYou.adapter = adapter
        Log.i("foryoudata", bookItem.toString())

        adapter.setOnItemClickCallback(object : BookListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: BookItem){
                val toDetail = Intent(requireActivity(), BookLayoutActivity::class.java)
                toDetail.putExtra(BookLayoutActivity.TITLE, data.bookTitle)
                startActivity(toDetail)
            }
        })
    }
}