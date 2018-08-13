package br.com.casamagalhaes.cmmp.views

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import br.com.casamagalhaes.cmmp.R
import br.com.casamagalhaes.cmmp.adapters.DailyListAdapter
import br.com.casamagalhaes.cmmp.controllers.MonthlyController
import br.com.casamagalhaes.cmmp.events.EditInfoEvent
import br.com.casamagalhaes.cmmp.models.Daily
import br.com.casamagalhaes.cmmp.models.Monthly
import br.com.casamagalhaes.cmmp.helpers.AlarmHelper
import br.com.casamagalhaes.cmmp.services.COUNTDOWN_BR
import br.com.casamagalhaes.cmmp.services.ChronoService
import br.com.casamagalhaes.cmmp.utils.calendar.DateHelper
import kotlinx.android.synthetic.main.activity_main_scrolling.*
import kotlinx.android.synthetic.main.content_main_scrolling.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import java.util.concurrent.TimeUnit


class MainScrollingActivity : AppCompatActivity() {

    private val monthlyController = MonthlyController(context = this)
    private val adapterList = DailyListAdapter(context = this)

    val br = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateGUI(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_scrolling)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            val snackbar: Snackbar?
            if (monthlyController.pointRecordByKey(Calendar.getInstance(), adapterList.resume!!)) {
                val dailyList = getListOfMonth().resume
                adapterList.resume = dailyList
                setAlarmAndroidForLunch(Calendar.getInstance().time)
                daily_list_recyclerview.adapter.notifyDataSetChanged()
                snackbar = Snackbar.make(view, "Ponto registrado com sucesso", Snackbar.LENGTH_LONG)
                startService()
            } else {
                snackbar = Snackbar.make(view, "Sua jornada já esta completa.", Snackbar.LENGTH_LONG)
            }
            snackbar.setAction("Action", null).show()
        }

        configRecycleView()
    }

    private fun startService() {
        val intent = Intent(this, ChronoService::class.java)
        startService(intent)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(br, IntentFilter(COUNTDOWN_BR))
        Log.i("COUNTER", "Registered broacast receiver")
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(br)
        Log.i("COUNTER", "Unregistered broacast receiver")
    }

    fun configRecycleView() {
        daily_list_recyclerview.adapter = adapterList
        adapterList.resume = getListOfMonth().resume
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        daily_list_recyclerview.layoutManager = layoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main_scrolling, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> return true
            else -> super.onOptionsItemSelected(item)
        }
    }

    @Subscribe
    fun editDaily(event: EditInfoEvent) {
        openWindowAlert(event.daily)
    }

    private fun setAlarmAndroidForLunch(date: Date) {
        val alarm = AlarmHelper(this)
        val predictionReturn = alarm.predictionLunchReturn(date)
        if (predictionReturn != null) alarm.createAlarm(predictionReturn, "Você já bateu o ponto?")
    }

    private fun getListOfMonth(): Monthly {
        val dateHelper = DateHelper()
        val currentMonth = dateHelper.dateStringPatternForMonthKey(Calendar.getInstance())
        return monthlyController.getMonthByKey(key = currentMonth, context = this)
    }

    private fun openWindowAlert(daySelected: Daily) {
        val fragment = PointEditorFragment()
        fragment.daily = daySelected
        fragment.show(supportFragmentManager, "my_fragment")
    }

    private fun updateGUI(intent: Intent) {
        if (intent.extras != null) {
            val millisUntilFinished = intent.getLongExtra("countdown", 0)

            val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
            val minutes = seconds % 3600 / 60
            val hour = seconds % 86400 / 3600

            val timeLeft = "$hour:$minutes:${seconds % 60}"
            resume_daily.text = timeLeft
        }
    }

}
