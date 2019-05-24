package com.rdt.rdtminer

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.rdt.rdtminer.MyConfig.Companion.sFragmentId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val mHomeFrag = HomeFragment()
    private val mDashboardFrag = DashboardFragment()
    private val mTransactionFrag = TransactionFragment()

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener{ item ->
        when (item.itemId) {
            R.id.x_nav_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.x_frame, mHomeFrag).commit()
                sFragmentId = FragId.HOME.i
                return@OnNavigationItemSelectedListener true
            }
            R.id.x_nav_dashboard -> {
                supportFragmentManager.beginTransaction().replace(R.id.x_frame, mDashboardFrag).commit()
                sFragmentId = FragId.DASHBOARD.i
                return@OnNavigationItemSelectedListener true
            }
            R.id.x_nav_transaction -> {
                supportFragmentManager.beginTransaction().replace(R.id.x_frame, mTransactionFrag).commit()
                sFragmentId = FragId.TRANSACTION.i
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        x_nav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        when (sFragmentId) {
            FragId.HOME.i -> supportFragmentManager.beginTransaction().replace(R.id.x_frame, mHomeFrag).commit()
            FragId.DASHBOARD.i -> supportFragmentManager.beginTransaction().replace(R.id.x_frame, mDashboardFrag).commit()
            FragId.TRANSACTION.i -> supportFragmentManager.beginTransaction().replace(R.id.x_frame, mTransactionFrag).commit()
        }
    }

}

/* EOF */
