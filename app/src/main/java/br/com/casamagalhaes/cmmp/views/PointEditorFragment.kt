package br.com.casamagalhaes.cmmp.views

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.casamagalhaes.cmmp.R
import br.com.casamagalhaes.cmmp.models.Daily
import br.com.casamagalhaes.cmmp.utils.calendar.DateHelper

class PointEditorFragment: DialogFragment() {

    var daily: Daily? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_point_editor, container, false)

        val dateHelper = DateHelper()

        val dateTxtView = view.findViewById<TextView>(R.id.date_txt_view)
        val checkinTxtView = view.findViewById<TextView>(R.id.checkin_txt_view)
        val lunchLeavingTxtView = view.findViewById<TextView>(R.id.lunch_leaving_txt_view)
        val lunchReturnTxtView = view.findViewById<TextView>(R.id.lunch_return_txt_view)
        val checkoutTxtView = view.findViewById<TextView>(R.id.checkout_txt_view)

        dateTxtView.text = daily?.key ?: "--/--/----"
        checkinTxtView.text = dateHelper.getHourByDate(daily?.checkIn)
        lunchLeavingTxtView.text = dateHelper.getHourByDate(daily?.lunchLeaving)
        lunchReturnTxtView.text = dateHelper.getHourByDate(daily?.lunchReturn)
        checkoutTxtView.text = dateHelper.getHourByDate(daily?.checkOut)


        return view
    }



}