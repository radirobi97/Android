package com.example.contact.util

import android.database.Cursor

fun Cursor.getStringByColumnName(colName: String) = this.getString(this.getColumnIndex(colName))