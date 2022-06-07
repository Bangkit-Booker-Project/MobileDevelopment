package com.example.capstonesean.homePage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.capstonesean.R
import com.example.capstonesean.booklayout.BookLayoutActivity
import com.example.capstonesean.data.Fetch
import com.example.capstonesean.data.response.BookItem
import com.example.capstonesean.databinding.FragmentTopRatedBinding
import com.example.capstonesean.viewmodelfactory.BookModelFactory

class TopRatedFragment : Fragment() {

    private var _binding: FragmentTopRatedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomepageViewModel by viewModels {
        BookModelFactory.getInstance()
    }

    override fun onResume() {
        super.onResume()
        val genre = resources.getStringArray(R.array.genre)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genre)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       _binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvTopRated.setLayoutManager(layoutManager)

        getTopRated()
    }

    private fun getTopRated(){
        binding.autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position).toString()
//            Toast.makeText(activity, item, Toast.LENGTH_LONG).show()
            viewModel.getTopRatedBooks(item).observe(requireActivity()) { result ->
                if (result != null) {
                    when (result) {
                        is Fetch.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Fetch.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val data = result.data
                            setBookList(data)
                        }
                        is Fetch.Error -> {
                            binding.progressBar.visibility = View.GONE
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
    }

    private fun setBookList(bookItem: List<BookItem>){
        val adapter = BookListAdapter(bookItem)
        binding.rvTopRated.adapter = adapter

        adapter.setOnItemClickCallback(object : BookListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: BookItem){
                val toDetail = Intent(requireActivity(), BookLayoutActivity::class.java)
                toDetail.putExtra(BookLayoutActivity.TITLE, data.bookTitle)
                startActivity(toDetail)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}