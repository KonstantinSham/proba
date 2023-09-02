package com.example.skillbox_hw_quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.databinding.FragmentQuizBinding
import com.example.skillbox_hw_quiz.quiz.QuizStorage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var feedback1: Int = 0
    private var feedback2: Int = 0
    private var feedback3: Int = 0

    private val quiz = QuizStorage.getQuiz(QuizStorage.Locale.Ru)
    private val questionsList = quiz.questions

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeText()
        binding.radioGroupOne.setOnCheckedChangeListener { _, id ->
            feedback1 = when (id) {
                R.id.radioGroupOneButtonOne -> 0
                R.id.radioGroupOneButtonTwo -> 1
                R.id.radioGroupOneButtonThree -> 2
                R.id.radioGroupOneButtonFour -> 3
                else -> 4
            }
        }
        binding.radioGroupTwo.setOnCheckedChangeListener { _, id ->
            feedback2 = when (id) {
                R.id.radioGroupTwoButtonOne -> 0
                R.id.radioGroupTwoButtonTwo -> 1
                R.id.radioGroupTwoButtonThree -> 2
                R.id.radioGroupTwoButtonFour -> 3
                else -> 4
            }
        }
        binding.radioGroupThree.setOnCheckedChangeListener { _, id ->
            feedback3 = when (id) {
                R.id.radioGroupThreeButtonOne -> 0
                R.id.radioGroupThreeButtonTwo -> 1
                R.id.radioGroupThreeButtonThree -> 2
                R.id.radioGroupThreeButtonFour -> 3
                else -> 4
            }
        }
        binding.buttonNext.setOnClickListener {
            val bundle = Bundle().apply {
                putString("param1", questionsList[0].feedback[feedback1])
                putString("param2", questionsList[1].feedback[feedback2])
                putString("param3", questionsList[2].feedback[feedback3])
            }
            findNavController().navigate(R.id.action_quizFragment_to_resultFragment, bundle)
        }
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_mainFragment)
        }
    }

    private fun changeText() {
        binding.firstQuestion.text = questionsList[0].question
        binding.secondQuestion.text = questionsList[1].question
        binding.thirdQuestion.text = questionsList[2].question

        binding.radioGroupOneButtonOne.text = questionsList[0].answers[0]
        binding.radioGroupOneButtonTwo.text = questionsList[0].answers[1]
        binding.radioGroupOneButtonThree.text = questionsList[0].answers[2]
        binding.radioGroupOneButtonFour.text = questionsList[0].answers[3]

        binding.radioGroupTwoButtonOne.text = questionsList[1].answers[0]
        binding.radioGroupTwoButtonTwo.text = questionsList[1].answers[1]
        binding.radioGroupTwoButtonThree.text = questionsList[1].answers[2]
        binding.radioGroupTwoButtonFour.text = questionsList[1].answers[3]

        binding.radioGroupThreeButtonOne.text = questionsList[2].answers[0]
        binding.radioGroupThreeButtonTwo.text = questionsList[2].answers[1]
        binding.radioGroupThreeButtonThree.text = questionsList[2].answers[2]
        binding.radioGroupThreeButtonFour.text = questionsList[2].answers[3]
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @param param3 Parameter 3.
         * @return A new instance of fragment QuizFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuizFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                }
            }
    }

}