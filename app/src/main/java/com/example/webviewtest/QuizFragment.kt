package com.example.webviewtest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.webviewtest.databinding.QuizLayoutBinding


class QuizFragment : Fragment() {

    private var _binding: QuizLayoutBinding? = null
    private val modelProvider: MainViewModel by lazy { MainViewModel.viewModelWithFragment(this@QuizFragment.requireActivity()) }
    private val binding get() = _binding!!

    private var currentQuestion: Int = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = QuizLayoutBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.answer1.setOnClickListener {
            modelProvider.checkCorrect(currentQuestion, 1)
            currentQuestion++
            nextQuestion()
        }
        binding.answer2.setOnClickListener {
            modelProvider.checkCorrect(currentQuestion, 2)
            currentQuestion++
            nextQuestion()
        }
        binding.answer3.setOnClickListener {
            modelProvider.checkCorrect(currentQuestion, 3)
            currentQuestion++
            nextQuestion()
        }
        binding.answer4.setOnClickListener {
            modelProvider.checkCorrect(currentQuestion, 4)
            currentQuestion++
            nextQuestion()
        }

    }

    private fun nextQuestion() {
        var textQuestion = ""
        var answer1 = ""
        var answer2 = ""
        var answer3 = ""
        var answer4 = ""

        when (currentQuestion) {
            2 -> {
                textQuestion = this.getString(R.string.Question2)
                answer1 = this.getString(R.string.q2a1)
                answer2 = this.getString(R.string.q2a2)
                answer3 = this.getString(R.string.q2a3)
                answer4 = this.getString(R.string.q2a4)
            }

            3 -> {
                textQuestion = this.getString(R.string.Question3)
                answer1 = this.getString(R.string.q3a1)
                answer2 = this.getString(R.string.q3a2)
                answer3 = this.getString(R.string.q3a3)
                answer4 = this.getString(R.string.q3a4)
            }

            4 -> {
                textQuestion = this.getString(R.string.Question4)
                answer1 = this.getString(R.string.q4a1)
                answer2 = this.getString(R.string.q4a2)
                answer3 = this.getString(R.string.q4a3)
                answer4 = this.getString(R.string.q4a4)
            }

            5 -> {
                textQuestion = this.getString(R.string.Question5)
                answer1 = this.getString(R.string.q5a1)
                answer2 = this.getString(R.string.q5a2)
                answer3 = this.getString(R.string.q5a3)
                answer4 = this.getString(R.string.q5a4)
            }

            6 -> {
                textQuestion = this.getString(R.string.Question6)
                answer1 = this.getString(R.string.q6a1)
                answer2 = this.getString(R.string.q6a2)
                answer3 = this.getString(R.string.q6a3)
                answer4 = this.getString(R.string.q6a4)
            }

            7 -> {
                textQuestion = this.getString(R.string.Question7)
                answer1 = this.getString(R.string.q7a1)
                answer2 = this.getString(R.string.q7a2)
                answer3 = this.getString(R.string.q7a3)
                answer4 = this.getString(R.string.q7a4)
            }

            8 -> {
                textQuestion = this.getString(R.string.Question8)
                answer1 = this.getString(R.string.q8a1)
                answer2 = this.getString(R.string.q8a2)
                answer3 = this.getString(R.string.q8a3)
                answer4 = this.getString(R.string.q8a4)
            }

            9 -> {
                textQuestion = this.getString(R.string.Question9)
                answer1 = this.getString(R.string.q9a1)
                answer2 = this.getString(R.string.q9a2)
                answer3 = this.getString(R.string.q9a3)
                answer4 = this.getString(R.string.q9a4)
            }

            10 -> {
                textQuestion = this.getString(R.string.Question10)
                answer1 = this.getString(R.string.q10a1)
                answer2 = this.getString(R.string.q10a2)
                answer3 = this.getString(R.string.q10a3)
                answer4 = this.getString(R.string.q10a4)
            }

            else -> findNavController().navigate(R.id.action_QuizFragment_to_ResultFragment)
        }

        binding.question.text = textQuestion
        binding.answer1Text.text = answer1
        binding.answer2Text.text = answer2
        binding.answer3Text.text = answer3
        binding.answer4Text.text = answer4
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