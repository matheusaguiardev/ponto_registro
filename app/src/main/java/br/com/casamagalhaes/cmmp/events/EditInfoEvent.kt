package br.com.casamagalhaes.cmmp.events

import br.com.casamagalhaes.cmmp.models.Daily

data class EditInfoEvent(val position: Int, val daily: Daily)