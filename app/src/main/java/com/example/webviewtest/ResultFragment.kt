package com.example.webviewtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.webviewtest.databinding.ResultFragmentBinding

class ResultFragment : Fragment() {

    private var _binding: ResultFragmentBinding? = null
    private val binding get() = _binding!!
    private val modelProvider: MainViewModel by lazy { MainViewModel.viewModelWithFragment(this@ResultFragment.requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ResultFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.score.text = modelProvider.score.toString() + "/10"
        if (modelProvider.score < 5) binding.message.text = this.getString(R.string.Congrats3)
        else if (modelProvider.score in 6..8) binding.message.text =
            this.getString(R.string.Congrats2)
        if (modelProvider.score > 8) binding.message.text = this.getString(R.string.Congrats1)
        binding.tryAgain.setOnClickListener {
            findNavController().navigate(R.id.action_ResultFragment_to_QuizStartFragment)
        }
    }

    private val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback.remove()
        _binding = null
    }

}