package com.example.testproject.frag


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.testproject.R
import com.orange.blelibrary.blelibrary.RootFragement

/**
 * A simple [Fragment] subclass.
 */
class f2 : RootFragement() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview=inflater.inflate(R.layout.fragment_f2, container, false)
        super.onCreateView(inflater, container, savedInstanceState)
        return rootview
    }


}
