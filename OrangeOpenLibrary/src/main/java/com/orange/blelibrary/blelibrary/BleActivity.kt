package com.orange.blelibrary.blelibrary;

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.orange.blelibrary.R
import com.orange.blelibrary.blelibrary.EventBus.ConnectBle
import com.orange.blelibrary.blelibrary.Server.BleServiceControl
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

open class BleActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {
    override fun onBackStackChanged() {
        val tag = supportFragmentManager.fragments[supportFragmentManager.fragments.size - 1].tag
        NowFrage=tag!!
        ChangePageListener(tag!!,supportFragmentManager.fragments[supportFragmentManager.fragments.size - 1])
    }
    var RXDATA=""
    lateinit var Translation:Fragment
    var ConnectDelay=10
    var bleServiceControl = BleServiceControl()
    var id=0
    var NowFrage=""
    var tag=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }
    fun init(){
        EventBus.getDefault().register(this)
        supportFragmentManager.addOnBackStackChangedListener(this)
        bleServiceControl.act=this
    }
    public override fun onDestroy() {
        if(!bleServiceControl.first){
            unbindService(bleServiceControl.mServiceConnection)
        }
        super.onDestroy()
    }

    fun GoBack() {
        supportFragmentManager!!.popBackStack()
        Log.d("frag", "${supportFragmentManager.backStackEntryCount}");
    }

    fun GoBack(a: Int) {
        supportFragmentManager!!.popBackStack(a, 1)
        Log.d("frag", "${supportFragmentManager.backStackEntryCount}");
    }
     var handler= Handler()

    open fun LoadingUI(a:String,pass:Int){

    }
    open fun LoadingSuccessUI(){

    }
    fun HideKeyBoard() {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun Event(a: ConnectBle) {
        LoadingUI(resources.getString(R.string.paired_with_your_device),0)
        bleServiceControl.connect(a.reback)
        Thread{
            var fal=0
            while(true){
                if(bleServiceControl.isconnect||fal==ConnectDelay){break}
                Thread.sleep(1000)
                fal++
            }
            handler.post {LoadingSuccessUI()
                if(bleServiceControl.isconnect){
                    val transaction = supportFragmentManager!!.beginTransaction()
                    transaction.replace(id, Translation,tag)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                        .addToBackStack(null)
                        .commit()
                }
            }
        }.start()
    }
    open fun ConnectSituation(boolean: Boolean){
        if (boolean) {
            Log.d("連線","連線ok")
        } else {
            LoadingSuccessUI()
            Log.d("連線","Bluetooth is disconnected")
            handler.post {
                Toast.makeText(this,"Bluetooth is disconnected",Toast.LENGTH_SHORT).show()
            }
        }
    }
    open fun RX(a:String){
        try {
            Log.w("BLEDATA", "RX:$a")
        } catch (e: Exception) {
            Log.w("error", e.message)
        }
    }
    open fun TX(a:String){
        try {
            Log.w("BLEDATA", "TX:$a")
        } catch (e: Exception) {
            Log.w("error", e.message)
        }
    }
    open fun GoScanner(Translation:Fragment,DelayTime:Int,id:Int,tag:String){
        ConnectDelay=DelayTime
        this.Translation=Translation
        this.id=id
        this.tag=tag;
        if(bleServiceControl.isconnect){
            val transaction = supportFragmentManager!!.beginTransaction()
            transaction.replace(id, Translation,tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                    .addToBackStack(null)
                    .commit()
        }else{
            startActivity(Intent(this, ScanBle::class.java))
        }
    }
    open fun ChangePage(Translation:Fragment,id:Int,tag:String,goback:Boolean){
        if(goback){
            val transaction = supportFragmentManager!!.beginTransaction()
            transaction.replace(id, Translation,tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                .addToBackStack(tag)
                .commit()
        }else{  val transaction = supportFragmentManager!!.beginTransaction()
            transaction.replace(id, Translation,tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)//設定動畫
                .commit()}
    }
    open fun ChangePageListener(tag:String,frag:Fragment){

    }
    open fun Toast(a:String){
        Toast.makeText(this,a,Toast.LENGTH_SHORT).show()
    }
    var mDialog: Dialog? = null
    fun ShowDaiLog(Layout:Int,touchcancel:Boolean,style:Int){
        try {
            if (mDialog == null) {
                mDialog = Dialog(this, style)
                mDialog!!.setContentView(Layout)
                mDialog!!.getWindow()!!.setLayout(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT)
                mDialog!!.setCancelable(true)
                mDialog!!.setCanceledOnTouchOutside(touchcancel)
                mDialog!!.show()
                if(touchcancel){getAllChildViews(mDialog!!.getWindow().getDecorView())}
            } else {
                if (!mDialog!!.isShowing()) {
                    mDialog = Dialog(this,style)
                    mDialog!!.setContentView(Layout)
                    mDialog!!.getWindow()!!.setLayout(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                    mDialog!!.setCancelable(true)
                    mDialog!!.setCanceledOnTouchOutside(touchcancel)
                    mDialog!!.show()
                    if(touchcancel){getAllChildViews(mDialog!!.getWindow().getDecorView())}
                }
            }
        } catch (e: Exception) {
            Thread.sleep(1000)
            e.printStackTrace()
        }
    }
    fun ShowDaiLog(Layout:Int,touchcancel:Boolean,swip:Boolean){
        try {
            if (mDialog == null) {
                mDialog = Dialog(this, if(swip) R.style.SwipTheme else R.style.MyDialog)
                mDialog!!.setContentView(Layout)
                mDialog!!.getWindow()!!.setLayout(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT)
                mDialog!!.setCancelable(true)
                mDialog!!.setCanceledOnTouchOutside(touchcancel)
                mDialog!!.show()
                if(touchcancel){getAllChildViews(mDialog!!.getWindow().getDecorView())}
            } else {
                if (!mDialog!!.isShowing()) {
                    mDialog = Dialog(this,if(swip) R.style.SwipTheme else R.style.MyDialog)
                    mDialog!!.setContentView(Layout)
                    mDialog!!.getWindow()!!.setLayout(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                    mDialog!!.setCancelable(true)
                    mDialog!!.setCanceledOnTouchOutside(touchcancel)
                    mDialog!!.show()
                    if(touchcancel){getAllChildViews(mDialog!!.getWindow().getDecorView())}
                }
            }
        } catch (e: Exception) {
            Thread.sleep(1000)
            e.printStackTrace()
        }
    }

    private fun getAllChildViews(view: View): List<View> {
        val allchildren = ArrayList<View>()
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val viewchild = view.getChildAt(i)
                allchildren.add(viewchild)
                Log.d("ChildView","$viewchild")
                allchildren.addAll(getAllChildViews(viewchild))
                if("$viewchild".contains("RelativeLayout")){  viewchild.setOnClickListener { mDialog!!.dismiss() }
                    return allchildren}
            }
        }
        return allchildren
    }
    fun DaiLogDismiss() {
        try {
            mDialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    var CanGoBack=true
    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }
}
