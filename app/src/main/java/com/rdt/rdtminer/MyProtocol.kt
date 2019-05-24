package com.rdt.rdtminer

import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.rdt.rdtminer.MyConfig.Companion.sPassword
import com.rdt.rdtminer.MyConfig.Companion.sServer
import com.rdt.rdtminer.MyConfig.Companion.sUser
import com.rdt.rdtminer.MyUtil.Companion.showToast
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MyProtocol {

    private val TAG = MyProtocol::class.java.simpleName

    companion object {

        fun checkServer(handler: Handler) {
            val client = OkHttpClient()
            val url = "$sServer/rest"
            val request = Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val msg = handler.obtainMessage(RestResponseMsg.SERVER_NOT_FOUND.i)
                    handler.sendMessage(msg)
                }
                override fun onResponse(call: Call, response: Response) {
                    val msg = handler.obtainMessage(RestResponseMsg.SERVER_OK.i)
                    handler.sendMessage(msg)
                }
            })
        }

        fun register(handler: Handler) {
            if (sUser == null || sPassword == null) {
                showToast("user name and password required to register")
                return
            }
            val client = OkHttpClient()
            val url = "$sServer/reg/rest"
            val body = FormBody.Builder()
                .add(RestRequest.USER_NAME.s, sUser)
                .add(RestRequest.USER_PASSWORD.s, sPassword)
                .build()
            val request = Request.Builder().url(url).post(body).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val msg = handler.obtainMessage(RestResponseMsg.REGISTER_FAILED.i)
                    handler.sendMessage(msg)
                }
                override fun onResponse(call: Call, response: Response) {
                    val msg = handler.obtainMessage(RestResponseMsg.REGISTER_OK.i)
                    handler.sendMessage(msg)
                }
            })
        }

        fun unregister(handler: Handler) {
            if (sUser == null || sPassword == null) {
                showToast("user name and password required to register")
                return
            }
            val client = OkHttpClient()
            val url = "$sServer/unreg/rest"
            val body = FormBody.Builder()
                .add(RestRequest.USER_NAME.s, sUser)
                .add(RestRequest.USER_PASSWORD.s, sPassword)
                .build()
            val request = Request.Builder().url(url).post(body).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val msg = handler.obtainMessage(RestResponseMsg.UNREGISTER_FAILED.i)
                    handler.sendMessage(msg)
                }
                override fun onResponse(call: Call, response: Response) {
                    val msg = handler.obtainMessage(RestResponseMsg.UNREGISTER_OK.i)
                    handler.sendMessage(msg)
                }
            })
        }

        fun login(handler: Handler) {
            if (sUser == null || sPassword == null) {
                showToast("user name and password required to login")
                return
            }
            val client = OkHttpClient()
            val url = "$sServer/login/rest"
            val body = FormBody.Builder()
                .add(RestRequest.USER_NAME.s, sUser)
                .add(RestRequest.USER_PASSWORD.s, sPassword)
                .build()
            val request = Request.Builder().url(url).post(body).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val msg = handler.obtainMessage(RestResponseMsg.LOGIN_FAILED.i)
                    handler.sendMessage(msg)
                }
                override fun onResponse(call: Call, response: Response) {
                    val msg = handler.obtainMessage(RestResponseMsg.LOGIN_OK.i)
                    handler.sendMessage(msg)
                }
            })
        }

        fun logout(handler: Handler) {
            val client = OkHttpClient()
            val url = "$sServer/logout/rest"
            val request = Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val msg = handler.obtainMessage(RestResponseMsg.LOGOUT_FAILED.i)
                    handler.sendMessage(msg)
                }
                override fun onResponse(call: Call, response: Response) {
                    val msg = handler.obtainMessage(RestResponseMsg.LOGOUT_OK.i)
                    handler.sendMessage(msg)
                }
            })
        }

        fun mine(handler: Handler) {
            val client = OkHttpClient()
            val url = "$sServer/blockchain/mine/rest"
            val body = FormBody.Builder()
                .add(RestRequest.USER_NAME.s, sUser)
                .build()
            val request = Request.Builder().url(url).post(body).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val msg = handler.obtainMessage(RestResponseMsg.MINE_FAILED.i)
                    handler.sendMessage(msg)
                }
                override fun onResponse(call: Call, response: Response) {
                    var msg = handler.obtainMessage(RestResponseMsg.MINE_OK.i)
                    handler.sendMessage(msg)
                    msg = handler.obtainMessage(RestResponseMsg.MINE_DATA.i)
                    val jsonObject = JSONObject(response.body()?.string()) //---- should be string() not toString()
                    val msgData = Bundle()
                    msgData.putString("data", jsonObject.getString(RestResponse.R_MESSAGE.s))
                    msg.data = msgData
                    handler.sendMessage(msg)
                }
            })
        }

        fun neighbor(handler: Handler) {
            val client = OkHttpClient()
            val url = "$sServer/user/users/rest"
            val request = Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val msg = handler.obtainMessage(RestResponseMsg.NEIGHBOR_FAILED.i)
                    handler.sendMessage(msg)
                }
                override fun onResponse(call: Call, response: Response) {
                    var msg = handler.obtainMessage(RestResponseMsg.NEIGHBOR_OK.i)
                    handler.sendMessage(msg)
                    msg = handler.obtainMessage(RestResponseMsg.NEIGHBOR_DATA.i)
                    val jsonObject = JSONObject(response.body()?.string())
                    val jsonArray = JSONArray(jsonObject.getString(RestResponse.R_DATA.s))
                    val msgData = Bundle()
                    msgData.putString("data", jsonArray.toString())
                    msg.data = msgData
                    handler.sendMessage(msg)
                }
            })
        }

        fun transact(handler: Handler, sender: String, recipient: String, amount: String) {
            val client = OkHttpClient()
            val url = "$sServer/transaction/generate/rest"
            val body = FormBody.Builder()
                .add(RestRequest.SENDER.s, sender)
                .add(RestRequest.RECIPIENT.s, recipient)
                .add(RestRequest.AMOUNT.s, amount)
                .build()
            val request = Request.Builder().url(url).post(body).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val msg = handler.obtainMessage(RestResponseMsg.TRANSACT_GEN_FAILED.i)
                    handler.sendMessage(msg)
                }
                override fun onResponse(call: Call, response: Response) {
                    var msg = handler.obtainMessage(RestResponseMsg.TRANSACT_GEN_OK.i)
                    handler.sendMessage(msg)
                    val jsonObject = JSONObject(response.body()?.string())
                    val code = jsonObject.getString(RestResponse.R_CODE.s)
                    if (code != "ok") {
                        msg = handler.obtainMessage(RestResponseMsg.TRANSACT_DATA.i)
                        val msgData = Bundle()
                        msgData.putString("data", jsonObject.getString(RestResponse.R_MESSAGE.s))
                        msg.data = msgData
                        handler.sendMessage(msg)
                        return
                    }
                    val signature = jsonObject.getString(RestResponse.R_SIGN.s)
                    val trans = JSONObject(jsonObject.getString(RestResponse.R_TRANSACT.s))
                    val s = trans.getString("sender")
                    val r = trans.getString("recipient")
                    val a = trans.getString("amount")
                    transactCoin(handler, s, r, a, signature)
                }
            })
        }

        private fun transactCoin(handler: Handler, sender: String, recipient: String, amount: String, signature: String) {
            val client = OkHttpClient()
            val url = "$sServer/blockchain/transact/rest"
            val body = FormBody.Builder()
                .add(RestRequest.SENDER.s, sender)
                .add(RestRequest.RECIPIENT.s, recipient)
                .add(RestRequest.AMOUNT.s, amount)
                .add(RestRequest.SIGNATURE.s, signature)
                .build()
            val request = Request.Builder().url(url).post(body).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val msg = handler.obtainMessage(RestResponseMsg.TRANSACT_FAILED.i)
                    handler.sendMessage(msg)
                }
                override fun onResponse(call: Call, response: Response) {
                    val msg = handler.obtainMessage(RestResponseMsg.TRANSACT_OK.i)
                    handler.sendMessage(msg)
                    //val jsonObject = JSONObject(response.body()?.string())
                    //val code = jsonObject.getString(RestResponse.R_CODE.s)
                    //val message = jsonObject.getString(RestResponse.R_MESSAGE.s)
                }
            })
        }

        fun showWallet(handler: Handler, user: String) {
            val client = OkHttpClient()
            val url = "$sServer/wallet/show/rest"
            val body = FormBody.Builder()
                .add(RestRequest.USER_NAME.s, user)
                .build()
            val request = Request.Builder().url(url).post(body).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val msg = handler.obtainMessage(RestResponseMsg.WALLET_SHOW_FAILED.i)
                    handler.sendMessage(msg)
                }
                override fun onResponse(call: Call, response: Response) {
                    var msg = handler.obtainMessage(RestResponseMsg.WALLET_SHOW_OK.i)
                    handler.sendMessage(msg)
                    msg = handler.obtainMessage(RestResponseMsg.WALLET_DATA.i)
                    val jsonObject = JSONObject(response.body()?.string())
                    val msgData = Bundle()
                    msgData.putString("pri_key", jsonObject.getString(RestResponse.R_PRI_KEY.s))
                    msgData.putString("pub_key", jsonObject.getString(RestResponse.R_PUB_KEY.s))
                    msg.data = msgData
                    handler.sendMessage(msg)
                }
            })
        }

        fun generateWallet(handler: Handler) {
            val client = OkHttpClient()
            val url = "$sServer/wallet/generate/rest"
            val request = Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val msg = handler.obtainMessage(RestResponseMsg.WALLET_GEN_FAILED.i)
                    handler.sendMessage(msg)
                }
                override fun onResponse(call: Call, response: Response) {
                    var msg = handler.obtainMessage(RestResponseMsg.WALLET_GEN_OK.i)
                    handler.sendMessage(msg)
                    msg = handler.obtainMessage(RestResponseMsg.WALLET_DATA.i)
                    val jsonObject = JSONObject(response.body()?.string())
                    val msgData = Bundle()
                    msgData.putString("pri_key", jsonObject.getString(RestResponse.R_PRI_KEY.s))
                    msgData.putString("pub_key", jsonObject.getString(RestResponse.R_PUB_KEY.s))
                    msg.data = msgData
                    handler.sendMessage(msg)
                }
            })
        }

        fun saveWallet(handler: Handler, user: String) {
            val client = OkHttpClient()
            val url = "$sServer/wallet/save/rest"
            val body = FormBody.Builder()
                .add(RestRequest.USER_NAME.s, user)
                .build()
            val request = Request.Builder().url(url).post(body).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    val msg = handler.obtainMessage(RestResponseMsg.WALLET_SAVE_FAILED.i)
                    handler.sendMessage(msg)
                }
                override fun onResponse(call: Call, response: Response) {
                    val msg = handler.obtainMessage(RestResponseMsg.WALLET_SAVE_OK.i)
                    handler.sendMessage(msg)
                }
            })
        }

    }

}

/* EOF */