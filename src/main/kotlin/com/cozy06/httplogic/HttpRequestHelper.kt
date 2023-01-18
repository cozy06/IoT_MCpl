package com.cozy06.httplogic

import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class HttpRequestHelper {
    fun SenderWithBody(URL: String, SendData: String): String {
        val json = """$SendData"""
        val url = URL("${readURL()}${URL}")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.setRequestProperty("Content-Type", "application/json")
        val outputStream: OutputStream = connection.outputStream
        outputStream.write(json.toByteArray())
        outputStream.flush()
        outputStream.close()
        val responseCode = connection.responseCode
        println(responseCode)
        val response = connection.inputStream.bufferedReader().readText()
        println(response)
        connection.disconnect()
        return response
    }

    fun SenderWithNoBody(URL: String): String? {
        var response: String? = null
        try {
            val url = URL("${readURL()}${URL}")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = false
            connection.connect()
            val responseCode = connection.responseCode
            println(responseCode)
            response = connection.inputStream.bufferedReader().readText()
            println(response)
            connection.disconnect()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return response
    }

    fun readURL(): String {

        val path = "${System.getProperty("user.dir")}/plugins/IoT/URL.txt"

        val String = FileReader(File(path))
        val buffer = BufferedReader(String)
        val temp = buffer.readLine()
        buffer.close()
        println(temp.toString())

        return temp.toString()
    }
}