package br.com.casamagalhaes.cmmp.models

import java.io.Serializable

/**
 * Created by aguiar on 05/06/18.
 */
open class Monthly(val key: String? = null, val resume: List<Daily>? = null) : Serializable