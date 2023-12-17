package com.example.affirmagic.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.affirmagic.databinding.FragmentHomeBinding
import com.example.affirmagic.fragments.adapters.AffirmationsAdapter
import com.example.affirmagic.utils.ModalBottomSheet
import com.example.affirmagic.utils.Resource
import com.example.affirmagic.utils.autoCleared
import com.example.affirmagic.viewmodel.AffirmationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding by autoCleared()
    private val viewModel: AffirmationsViewModel by viewModels()
    private lateinit var adapter: AffirmationsAdapter
    private var today = LocalDateTime.now()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentHomeBinding.inflate(inflater, container, false)

        setObservers()

        setupRecyclerView(today)

        with(binding){
            backBTN.setOnClickListener {
                setupRecyclerView(today.minusDays(1))
                today = today.minusDays(1)
            }
            nextBTN.setOnClickListener {
                setupRecyclerView(today.plusDays(1))
                today = today.plusDays(1)
            }
        }


        return binding.root
    }
    private fun setObservers() {
        adapter = AffirmationsAdapter{
            affirmationsEntity ->

            val modalBottomSheet = ModalBottomSheet(requireContext(),affirmationsEntity)
            modalBottomSheet.show(parentFragmentManager, ModalBottomSheet.TAG)
        }
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupRecyclerView(dateTime: LocalDateTime){

        binding.backBTN.isVisible = !isSixDaysBack(dateTime)
        binding.nextBTN.isVisible = !isToday(dateTime)
        setTitle(dateTime)

        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        val formatted = dateTime.format(formatter)

        viewModel.getAffirmations(formatted).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { affirmations -> adapter.setAffirmationItems(affirmations)
                        adapter.notifyDataSetChanged()
                    }
                    Log.d("YourFragment", "Data updated in RecyclerView")
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
                    binding.textViewError.text = it.message
                }
            }
        }
    }

    private fun isSixDaysBack(dateTime: LocalDateTime): Boolean {
        return LocalDateTime.now().minusDays(6).isAfter(dateTime)
    }

    private fun isToday(dateTime: LocalDateTime): Boolean {
        return dateTime.toLocalDate() == LocalDateTime.now().toLocalDate()
    }

    private fun setTitle(dateTime: LocalDateTime){
        when (dateTime) {
            LocalDateTime.now() -> {
                binding.title.text = "Today"
            }
            LocalDateTime.now().minusDays(1) -> {
                binding.title.text = "Yesterday"}
            else -> {
                binding.title.text =  when (dateTime.dayOfMonth) {
                    1 -> {
                        dateTime.month.toString() + " " + dateTime.dayOfMonth + "st"
                    }
                    2 -> {
                        dateTime.month.toString() + " " + dateTime.dayOfMonth + "nd"
                    }
                    3 -> {
                        dateTime.month.toString() + " " + dateTime.dayOfMonth + "rd"
                    }
                    else -> {
                        dateTime.month.toString() + " " + dateTime.dayOfMonth + "th"
                    }
                }
            }
        }
    }

}