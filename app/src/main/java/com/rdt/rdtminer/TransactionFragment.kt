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
import com.rdt.rdtminer.MyProtocol.Companion.mine
import com.rdt.rdtminer.MyProtocol.Companion.transact
import com.rdt.rdtminer.MyUtil.Companion.showToast
import kotlinx.android.synthetic.main.fragment_transaction.*
import java.lang.NumberFormatException

class TransactionFragment : Fragment() {

    private val TAG = TransactionFragment::class.java.simpleName

    private val HANDLER = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                RestResponseMsg.TRANSACT_GEN_FAILED.i -> x_message.text = "transaction generation failed\n"
                RestResponseMsg.TRANSACT_GEN_OK.i -> x_message.text = "transaction generation ok\n"
                RestResponseMsg.TRANSACT_FAILED.i -> x_message.text = "transaction failed\n"
                RestResponseMsg.TRANSACT_OK.i -> x_message.text = "transaction ok\n"
                RestResponseMsg.TRANSACT_DATA.i -> {
                    val data = msg.data?.getString("data")
                    x_message.append(data + "\n")
                    showToast("$data, or invalid neighbor")
                }
                RestResponseMsg.MINE_FAILED.i -> x_message.text = "mine failed\n"
                RestResponseMsg.MINE_OK.i -> x_message.text = "mine ok\n"
                RestResponseMsg.MINE_DATA.i -> {
                    x_message.append(msg.data?.getString("data") + "\n")
                }
                else -> x_message.text = "unknown\n"
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onStart() {
        super.onStart()
        x_message.movementMethod = ScrollingMovementMethod()

        x_transact.setOnClickListener { makeTransaction() }
        x_mine.setOnClickListener { mine(HANDLER) }
    }

    override fun onResume() {
        super.onResume()
        x_message.text = "transact coins with your neighbors, and \nmine coins from cycling\n"
        setup()
    }

    //
    //  PRIVATE FUN
    //
    private fun setup() {
        x_sender.setText(sUser)
    }

    private fun makeTransaction() {
        val sender = x_sender.text.toString()
        val recipient = x_recipient.text.toString()
        val amount: Int
        try {
            amount = (x_amount.text.toString()).toInt()
        } catch (e: NumberFormatException) {
            showToast("amount must be integer")
            return
        }
        if (sender != sUser) {
            showToast("sender is not the logged user")
            return
        }
        if (recipient == "") {
            showToast("recipient is not entered")
            return
        }
        if (amount <= 0) {
            showToast("amount must be larger than 0")
            return
        }
        transact(HANDLER, sender, recipient, amount.toString())
    }

}

/* EOF */
