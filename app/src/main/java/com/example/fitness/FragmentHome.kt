package com.example.fitness

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitness.databinding.FragmentHomeBinding

class FragmentHome : Fragment(), View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

//        val gaugeView = view.findViewById<SemiCircleGaugeView>(R.id.gaugeView)
//        gaugeView.setProgress(0.5f)
//        binding.waterProgressBar.setProgressCompat(9, true)
        binding.stepProgressBar.progress = 50
        binding.waterProgressBar.progress = 90
//        val progress = binding.waterProgressBar.progress
//        binding.waterProgressBar.setProgress(progress, true)
        binding.waterCardView.setOnClickListener(this)
        binding.stepCardView.setOnClickListener(this)
        return view
    }

    companion object {
        fun newInstance() = FragmentHome()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.waterCardView -> {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }

            R.id.stepCardView -> {
//                Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(activity, StepTrackerActivity::class.java)
                startActivity(intent)
            }
        }
    }
}