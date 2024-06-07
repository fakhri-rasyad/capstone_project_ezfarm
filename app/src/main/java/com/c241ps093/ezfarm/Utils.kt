package com.c241ps093.ezfarm

import android.content.Context
import android.widget.Toast
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val dateFormat = "dd-MM-yyyy"
fun makeToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun dateFormatter(date: Date): String {
    val dateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
    return dateFormat.format(date)
}

fun stringDateFormatter(dateString: String): Date? {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    return formatter.parse(dateString)

}

fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile("today", ".jpg", filesDir)
}