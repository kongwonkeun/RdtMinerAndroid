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
import com.rdt.rdtminer.MyConfig.Companion.sPassword
import com.rdt.rdtminer.MyConfig.Companion.sServer
import com.rdt.rdtminer.MyConfig.Companion.sUser
import com.rdt.rdtminer.MyConfig.Companion.setUserInfo
import com.rdt.rdtminer.MyProtocol.Companion.checkServer
import com.rdt.rdtminer.MyProtocol.Companion.login
import com.rdt.rdtminer.MyProtocol.Companion.logout
import com.rdt.rdtminer.MyProtocol.Companion.register
import com.rdt.rdtminer.MyProtocol.Companion.unregister
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val TAG = HomeFragment::class.java.simpleName

    private val HANDLER = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                RestResponseMsg.SERVER_NOT_FOUND.i -> x_message.text = "server not found\n"
                RestResponseMsg.SERVER_OK.i -> x_message.text = "server ok\n"
                RestResponseMsg.REGISTER_FAILED.i -> x_message.text = "register failed\n"
                RestResponseMsg.REGISTER_OK.i -> x_message.append("register ok\n")
                RestResponseMsg.UNREGISTER_FAILED.i -> x_message.text = "unregister failed\n"
                RestResponseMsg.UNREGISTER_OK.i -> x_message.text = "unregister ok\n"
                RestResponseMsg.LOGIN_FAILED.i -> x_message.text = "login failed\n"
                RestResponseMsg.LOGIN_OK.i -> x_message.text = "login ok\n"
                RestResponseMsg.LOGOUT_FAILED.i -> x_message.text = "logout failed\n"
                RestResponseMsg.LOGOUT_OK.i -> x_message.text = "logout ok\n"
                else -> x_message.text = "unknown\n"
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        x_message.movementMethod = ScrollingMovementMethod()

        x_url_save.setOnClickListener { saveUrl() }
        x_name_save.setOnClickListener { saveName() }
        x_password_save.setOnClickListener { savePassword() }
        x_check_server.setOnClickListener { checkServer(HANDLER) }
        x_register.setOnClickListener { register(HANDLER) }
        x_unregister.setOnClickListener { unregister(HANDLER) }
        x_login.setOnClickListener { login(HANDLER) }
        x_logout.setOnClickListener { logout(HANDLER) }
    }

    override fun onResume() {
        super.onResume()
        x_message.text = "configure your server and account information, and\nlogin to your account\n"
        setup()
    }

    //
    //  PRIVATE FUN
    //
    private fun setup() {
        x_url.setText(sServer)
        x_name.setText(sUser)
        x_password.setText(sPassword)
    }

    private fun saveUrl() {
        sServer = x_url.text.toString()
        setUserInfo()
    }

    private fun saveName() {
        sUser = x_name.text.toString()
        setUserInfo()
    }

    private fun savePassword() {
        sPassword = x_password.text.toString()
        setUserInfo()
    }

}

/* EOF */
