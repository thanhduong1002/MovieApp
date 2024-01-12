package com.example.movieapp.utils

import android.text.Html
import android.text.Spanned

object StringUtil {
    fun setColor(string: String): Spanned? {
        return Html.fromHtml("<font color='#FF7F27'>$string</font>")
    }
}