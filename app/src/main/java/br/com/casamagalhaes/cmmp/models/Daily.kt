package br.com.casamagalhaes.cmmp.models

import java.io.Serializable
import java.util.*

/**
 * Created by aguiar on 05/06/18.
 */
open class Daily(
        val key: String? = null,
        var checkIn: Date? = null,
        var lunchLeaving: Date? = null,
        var lunchReturn: Date? = null,
        var checkOut: Date? = null
) : Serializable