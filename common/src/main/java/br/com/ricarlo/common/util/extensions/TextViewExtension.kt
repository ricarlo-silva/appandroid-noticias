package br.com.ricarlo.common.util.extensions

import android.widget.TextView

fun TextView?.getString() = this?.text?.toString().orEmpty()
