package com.gorothschild.myplantdiary.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gorothschild.myplantdiary.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // any time plants changes, this is the logic I want to happen!!!
        viewModel.plants.observe(viewLifecycleOwner, Observer {
            plants -> actPlantName.setAdapter(ArrayAdapter( // reminder actPlantName is from main_fragment. XML
                context!!,R.layout.support_simple_spinner_dropdown_item, plants))

        })
    }

}