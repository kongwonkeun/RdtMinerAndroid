package com.rdt.rdtminer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.app.Fragment
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rdt.rdtminer.MyConfig.Companion.sUser
import com.rdt.rdtminer.MyProtocol.Companion.generateWallet
import com.rdt.rdtminer.MyProtocol.Companion.neighbor
import com.rdt.rdtminer.MyProtocol.Companion.saveWallet
import com.rdt.rdtminer.MyProtocol.Companion.showWallet
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.json.JSONArray

class DashboardFragment : Fragment() {

    private val TAG = DashboardFragment::class.java.simpleName

    private val HANDLER = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                RestResponseMsg.NEIGHBOR_FAILED.i -> x_message.text = "get neighbor info failed\n"
                RestResponseMsg.NEIGHBOR_OK.i -> x_message.text = "get neighbor info ok\n"
                RestResponseMsg.NEIGHBOR_DATA.i -> {
                    val data = JSONArray(msg.data?.getString("data"))
                    val size = data.length()
                    x_message.append("total neighbors = $size\n")
                    for (i in 0 until size) {
                        val j = data.getJSONObject(i)
                        val n = j.getString("name")
                        val c = j.getString("coin")
                        x_neighbor_list.append("$n: $c, ")
                    }
                }
                RestResponseMsg.WALLET_GEN_FAILED.i -> x_message.text = "wallet generate failed\n"
                RestResponseMsg.WALLET_GEN_OK.i -> x_message.text = "wallet generate ok\n"
                RestResponseMsg.WALLET_SAVE_FAILED.i -> x_message.text = "wallet save failed\n"
                RestResponseMsg.WALLET_SAVE_OK.i -> x_message.text = "wallet save ok\n"
                RestResponseMsg.WALLET_SHOW_FAILED.i -> x_message.text = "wallet show failed\n"
                RestResponseMsg.WALLET_SHOW_OK.i -> x_message.text = "wallet show ok\n"
                RestResponseMsg.WALLET_DATA.i -> {
                    x_pri_key.text = "pri_key: " + msg.data?.getString("pri_key")
                    x_pub_key.text = "pub_key: " + msg.data?.getString("pub_key")
                }
                else -> x_message.text = "unknown\n"
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onStart() {
        super.onStart()
        x_message.movementMethod = ScrollingMovementMethod()

        x_neighbor.setOnClickListener { neighbor(HANDLER) }
        x_generate.setOnClickListener { generateWallet(HANDLER) }
        x_show.setOnClickListener { showUserWallet() }
        x_save.setOnClickListener { saveUserWallet() }
    }

    override fun onResume() {
        super.onResume()
        x_message.text = "find your neighbors name and their coins, and \nmanipulate your wallet\n"
        setup()
    }

    //
    //  PRIVATE FUN
    //
    private fun setup() {
        x_user.setText(sUser)
    }

    private fun showUserWallet() {
        showWallet(HANDLER, x_user.text.toString())
    }

    private fun saveUserWallet() {
        saveWallet(HANDLER, x_user.text.toString())
    }

}

/* EOF */