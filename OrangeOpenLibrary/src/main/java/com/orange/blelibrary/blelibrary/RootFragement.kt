package com.orange.blelibrary.blelibrary

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orange.blelibrary.blelibrary.BleActivity

open class RootFragement : Fragment() {
    lateinit var rootview: View
    lateinit var act:BleActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act=activity!! as BleActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview.setOnClickListener { act.HideKeyBoard() }
        return rootview
    }
    var handler= Handler()
}
