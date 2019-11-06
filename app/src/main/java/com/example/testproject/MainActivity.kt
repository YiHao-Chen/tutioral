package com.example.testproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.testproject.frag.f1
import com.example.testproject.frag.f2
import com.orange.blelibrary.blelibrary.BleActivity

class MainActivity : BleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ChangePage(f1(),R.id.frage,"f1",true)
    }
    fun change(view: View){
//        GoScanner(f1(),10,R.id.frage,"f1")
}
}
