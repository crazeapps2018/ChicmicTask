package com.meprogrammer.chichmic.util

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast

fun String.isValidEmail() =
    !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Context.showToast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()