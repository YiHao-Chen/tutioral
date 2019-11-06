package com.example.testproject.frag


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.testproject.R
import com.orange.blelibrary.blelibrary.RootFragement
import kotlinx.android.synthetic.main.fragment_f3.view.*

/**
 * A simple [Fragment] subclass.
 */
class f3 : RootFragement() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     rootview=inflater.inflate(R.layout.fragment_f3, container, false)
        super.onCreateView(inflater, container, savedInstanceState)
        rootview.imageView5.setOnClickListener {
            act.GoScanner(f4(),10,R.id.frage,"f4")
//            act.ShowDaiLog(R.layout.error,false,true)
//            act.mDialog!!.findViewById<TextView>(R.id.textView10).setOnClickListener {
//                act.Toast("error")
//            }
        }
        return rootview
    }


}
