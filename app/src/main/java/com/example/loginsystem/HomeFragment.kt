package com.example.loginsystem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the button view by its ID
        val buttonGoToPetProfiling: Button = view.findViewById(R.id.buttonProfiling)

        // Set OnClickListener to the button
        buttonGoToPetProfiling.setOnClickListener {
            // Navigate to fragment_pet_profiling
            findNavController().navigate(R.id.PetProfilingFragment)
        }
    }
}
