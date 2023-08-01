package com.h2bet.sportsapp.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.h2bet.sportsapp.MainViewModel
import com.h2bet.sportsapp.R
import com.h2bet.sportsapp.databinding.QuizStartFragmentBinding

class QuizStartFragment : Fragment() {

    private var _binding: QuizStartFragmentBinding? = null
    private val binding get() = _binding!!
    private val modelProvider: MainViewModel by lazy { MainViewModel.viewModelWithFragment(this@QuizStartFragment.requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        modelProvider.score = 0
        _binding = QuizStartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tryAgain.setOnClickListener {
            findNavController().navigate(R.id.action_Quiz_Start_Fragment_to_Quiz_Fragment)
        }
        binding.exit.setOnClickListener{
            this@QuizStartFragment.requireActivity().finishAndRemoveTask()
        }
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}