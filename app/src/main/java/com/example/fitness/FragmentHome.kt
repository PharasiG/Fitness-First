package com.example.fitness

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentHome : Fragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

//        val gaugeView = view.findViewById<SemiCircleGaugeView>(R.id.gaugeView)
//        gaugeView.setProgress(0.5f)
        view.findViewById<View>(R.id.water_progressBar).setOnClickListener(this)
        return view
    }

    companion object {
        fun newInstance() = FragmentHome()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.water_progressBar -> {
                val intent = Intent(activity, WaterActivity::class.java)
                startActivity(intent)
            }
        }
    }
}