package com.satria.dicoding.latihan.readwriteapp

import android.content.Context

internal object FileHelper {
    fun writeToFile(fileModel: FileModel, context: Context) {
        context.openFileOutput(fileModel.fileName, Context.MODE_PRIVATE).use {
            it.write(fileModel.data?.toByteArray())
        }
    }

    fun readFromFile(fileName: String, context: Context): FileModel {
        val fileModel = FileModel()
        fileModel.fileName = fileName
        fileModel.data = context.openFileInput(fileName).bufferedReader().useLines {
            it.fold("") { some, text ->
                "$some\n$text"
            }
        }

        return fileModel
    }
}