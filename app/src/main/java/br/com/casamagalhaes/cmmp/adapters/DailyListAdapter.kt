package br.com.casamagalhaes.cmmp.adapters

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.casamagalhaes.cmmp.R
import br.com.casamagalhaes.cmmp.events.EditInfoEvent
import br.com.casamagalhaes.cmmp.models.Daily
import br.com.casamagalhaes.cmmp.utils.calendar.DateHelper
import kotlinx.android.synthetic.main.item_daily.view.*
import org.greenrobot.eventbus.EventBus


class DailyListAdapter(private val context: Context) :  Adapter<DailyListAdapter.ViewHolder>() {

    var resume: List<Daily>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_daily, parent, false)
        return ViewHolder(view, context).listen { position, _ ->
            EventBus.getDefault().post(EditInfoEvent(position, resume!![position]))
        }
    }

    override fun getItemCount(): Int {
        return resume?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bindView(resume?.get(position)!!)
    }

    class ViewHolder(itemView: View, val context: Context):  RecyclerView.ViewHolder(itemView){
        val dateUtils = DateHelper()
        fun bindView(daily: Daily){
            val date = itemView.date_txt_view
            val checkin = itemView.checkin_txt_view
            val checkout = itemView.checkout_txt_view
            val lunchReturn = itemView.lunch_return_txt_view
            val lunchLeaving = itemView.lunch_leaving_txt_view

            date.text = dateUtils.formatKeyDate(daily.key)
            checkin.text = dateUtils.getHourByDate(daily.checkIn)
            checkout.text = dateUtils.getHourByDate(daily.checkOut)
            lunchLeaving.text = dateUtils.getHourByDate(daily.lunchLeaving)
            lunchReturn.text = dateUtils.getHourByDate(daily.lunchReturn)

            customViewDynamic(daily.key)
        }

        private fun customViewDynamic(key: String?){
            if(dateUtils.isToday(key)) {
                itemView.background = ContextCompat.getDrawable(context, R.drawable.border_view_style)
            } else {
                itemView.background = ContextCompat.getDrawable(context, android.R.color.white)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.elevation = 3.0F
            }
        }

    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(adapterPosition, itemViewType)
        }
        return this
    }

}