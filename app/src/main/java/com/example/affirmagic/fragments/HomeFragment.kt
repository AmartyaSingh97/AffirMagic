package com.example.affirmagic.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.affirmagic.databinding.FragmentHomeBinding
import com.example.affirmagic.fragments.adapters.AffirmationsAdapter
import com.example.affirmagic.utils.Resource
import com.example.affirmagic.utils.autoCleared
import com.example.affirmagic.viewmodel.AffirmationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding by autoCleared()
    private lateinit var viewModel: AffirmationsViewModel
    private lateinit var adapter: AffirmationsAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setObservers() {
        adapter = AffirmationsAdapter()
        binding.recyclerView.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupRecyclerView(){

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        val formatted = current.format(formatter)

        viewModel = ViewModelProvider(this)[AffirmationsViewModel::class.java]

        viewModel.getAffirmations(formatted).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    setObservers()
                    it.data?.let { affirmations -> adapter.setAffirmationItems(affirmations) }
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }

                Resource.Status.ERROR -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.textViewError.visibility = View.VISIBLE
                }
            }
        }
    }

}