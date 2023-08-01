package com.h2bet.sportsapp.quiz

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.h2bet.sportsapp.MainViewModel
import com.h2bet.sportsapp.R
import com.h2bet.sportsapp.databinding.QuizLayoutBinding


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
            if (modelProvider.checkCorrect(currentQuestion, 1)) {
                binding.scorePlus.setTextColor(resources.getColor(R.color.correct))
                binding.scorePlus.text = resources.getString(R.string.plusScore)
                binding.answer1Text.setBackgroundColor(resources.getColor(R.color.correct))
            } else {
                binding.scorePlus.setTextColor(resources.getColor(R.color.wrong))
                binding.scorePlus.text = resources.getString(R.string.noScore)
                binding.answer1Text.setBackgroundColor(resources.getColor(R.color.wrong))
            }
            binding.scorePlus.visibility = View.VISIBLE
            currentQuestion++
            binding.answer1.isClickable = false
            binding.answer2.isClickable = false
            binding.answer3.isClickable = false
            binding.answer4.isClickable = false
            Handler().postDelayed({ nextQuestion() }, 2000)
        }
        binding.answer2.setOnClickListener {
            if (modelProvider.checkCorrect(currentQuestion, 2)) {
                binding.scorePlus.setTextColor(resources.getColor(R.color.correct))
                binding.scorePlus.text = resources.getString(R.string.plusScore)
                binding.answer2Text.setBackgroundColor(resources.getColor(R.color.correct))
            } else {
                binding.scorePlus.setTextColor(resources.getColor(R.color.wrong))
                binding.scorePlus.text = resources.getString(R.string.noScore)
                binding.answer2Text.setBackgroundColor(resources.getColor(R.color.wrong))
            }
            binding.scorePlus.visibility = View.VISIBLE
            currentQuestion++
            binding.answer1.isClickable = false
            binding.answer2.isClickable = false
            binding.answer3.isClickable = false
            binding.answer4.isClickable = false
            Handler().postDelayed({ nextQuestion() }, 2000)
        }
        binding.answer3.setOnClickListener {
            if (modelProvider.checkCorrect(currentQuestion, 3)) {
                binding.scorePlus.setTextColor(resources.getColor(R.color.correct))
                binding.scorePlus.text = resources.getString(R.string.plusScore)
                binding.answer3Text.setBackgroundColor(resources.getColor(R.color.correct))
            } else {
                binding.scorePlus.setTextColor(resources.getColor(R.color.wrong))
                binding.scorePlus.text = resources.getString(R.string.noScore)
                binding.answer3Text.setBackgroundColor(resources.getColor(R.color.wrong))
            }
            binding.scorePlus.visibility = View.VISIBLE
            currentQuestion++
            binding.answer1.isClickable = false
            binding.answer2.isClickable = false
            binding.answer3.isClickable = false
            binding.answer4.isClickable = false
            Handler().postDelayed({ nextQuestion() }, 2000)
        }
        binding.answer4.setOnClickListener {
            if (modelProvider.checkCorrect(currentQuestion, 4)) {
                binding.scorePlus.setTextColor(resources.getColor(R.color.correct))
                binding.scorePlus.text = resources.getString(R.string.plusScore)
                binding.answer4Text.setBackgroundColor(resources.getColor(R.color.correct))
            } else {
                binding.scorePlus.setTextColor(resources.getColor(R.color.wrong))
                binding.scorePlus.text = resources.getString(R.string.noScore)
                binding.answer4Text.setBackgroundColor(resources.getColor(R.color.wrong))
            }
            binding.scorePlus.visibility = View.VISIBLE
            currentQuestion++
            binding.answer1.isClickable = false
            binding.answer2.isClickable = false
            binding.answer3.isClickable = false
            binding.answer4.isClickable = false
            Handler().postDelayed({ nextQuestion() }, 2000)
        }

    }

    private fun nextQuestion() {

        var textQuestion = ""
        var answer1 = ""
        var answer2 = ""
        var answer3 = ""
        var answer4 = ""
        var imageId = 0

        when (currentQuestion) {
            2 -> {
                textQuestion = this.getString(R.string.Question2)
                answer1 = this.getString(R.string.q2a1)
                answer2 = this.getString(R.string.q2a2)
                answer3 = this.getString(R.string.q2a3)
                answer4 = this.getString(R.string.q2a4)
                imageId = R.drawable.quest2
            }

            3 -> {
                textQuestion = this.getString(R.string.Question3)
                answer1 = this.getString(R.string.q3a1)
                answer2 = this.getString(R.string.q3a2)
                answer3 = this.getString(R.string.q3a3)
                answer4 = this.getString(R.string.q3a4)
                imageId = R.drawable.quest3
            }

            4 -> {
                textQuestion = this.getString(R.string.Question4)
                answer1 = this.getString(R.string.q4a1)
                answer2 = this.getString(R.string.q4a2)
                answer3 = this.getString(R.string.q4a3)
                answer4 = this.getString(R.string.q4a4)
                imageId = R.drawable.quest4
            }

            5 -> {
                textQuestion = this.getString(R.string.Question5)
                answer1 = this.getString(R.string.q5a1)
                answer2 = this.getString(R.string.q5a2)
                answer3 = this.getString(R.string.q5a3)
                answer4 = this.getString(R.string.q5a4)
                imageId = R.drawable.quest5
            }

            6 -> {
                textQuestion = this.getString(R.string.Question6)
                answer1 = this.getString(R.string.q6a1)
                answer2 = this.getString(R.string.q6a2)
                answer3 = this.getString(R.string.q6a3)
                answer4 = this.getString(R.string.q6a4)
                imageId = R.drawable.quest6
            }

            7 -> {
                textQuestion = this.getString(R.string.Question7)
                answer1 = this.getString(R.string.q7a1)
                answer2 = this.getString(R.string.q7a2)
                answer3 = this.getString(R.string.q7a3)
                answer4 = this.getString(R.string.q7a4)
                imageId = R.drawable.quest7
            }

            8 -> {
                textQuestion = this.getString(R.string.Question8)
                answer1 = this.getString(R.string.q8a1)
                answer2 = this.getString(R.string.q8a2)
                answer3 = this.getString(R.string.q8a3)
                answer4 = this.getString(R.string.q8a4)
                imageId = R.drawable.quest8
            }

            9 -> {
                textQuestion = this.getString(R.string.Question9)
                answer1 = this.getString(R.string.q9a1)
                answer2 = this.getString(R.string.q9a2)
                answer3 = this.getString(R.string.q9a3)
                answer4 = this.getString(R.string.q9a4)
                imageId = R.drawable.quest9
            }

            10 -> {
                textQuestion = this.getString(R.string.Question10)
                answer1 = this.getString(R.string.q10a1)
                answer2 = this.getString(R.string.q10a2)
                answer3 = this.getString(R.string.q10a3)
                answer4 = this.getString(R.string.q10a4)
                imageId = R.drawable.quest10
            }

            else -> findNavController().navigate(R.id.action_QuizFragment_to_ResultFragment)
        }

        binding.answer1Text.setBackgroundColor(resources.getColor(R.color.textBoxColor))
        binding.answer2Text.setBackgroundColor(resources.getColor(R.color.textBoxColor))
        binding.answer3Text.setBackgroundColor(resources.getColor(R.color.textBoxColor))
        binding.answer4Text.setBackgroundColor(resources.getColor(R.color.textBoxColor))



        binding.answer1.isClickable = true
        binding.answer2.isClickable = true
        binding.answer3.isClickable = true
        binding.answer4.isClickable = true

        binding.questionText.text = textQuestion
        binding.answer1Text.text = answer1
        binding.answer2Text.text = answer2
        binding.answer3Text.text = answer3
        binding.answer4Text.text = answer4
        binding.mainImage.setImageResource(imageId)
        binding.scoreText.text = "Score " + modelProvider.score + "0"
        binding.questNumber.text = currentQuestion.toString() + "/10"
        binding.scorePlus.visibility = View.INVISIBLE
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