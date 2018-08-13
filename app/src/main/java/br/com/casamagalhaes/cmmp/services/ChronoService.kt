package br.com.casamagalhaes.cmmp.services

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import br.com.casamagalhaes.cmmp.utils.Constants

const val COUNTDOWN_BR = "chronos.countdown_br"

open class ChronoService(val millisInFuture: Long = Constants.JOURNEY): Service() {

    private val TAG = "BroadcastService"
    var bi = Intent(COUNTDOWN_BR)
    private val count: CountDownTimer? = null

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Starting timer...")

        val cdt = object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000)
                bi.putExtra("countdown", millisUntilFinished)
                sendBroadcast(bi)
            }

            override fun onFinish() {
                Log.i(TAG, "Hora de bater o ponto")
            }
        }
        cdt.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    override fun onDestroy() {
        count?.cancel()
        Log.i(TAG, "Timer cancelled")
        super.onDestroy()
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

 }