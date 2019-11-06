package com.example.testproject.frag


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.testproject.R
import com.orange.blelibrary.blelibrary.RootFragement
import kotlinx.android.synthetic.main.fragment_f1.view.*

/**
 * A simple [Fragment] subclass.
 */
class f1 : RootFragement() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview=inflater.inflate(R.layout.fragment_f1, container, false)
        rootview.button3.setOnClickListener {
            act.ChangePage(f2(),R.id.frage,"f2",true)
        }
        super.onCreateView(inflater, container, savedInstanceState)
        return rootview
    }


}
