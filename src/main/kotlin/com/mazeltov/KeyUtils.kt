package com.mazeltov

import java.io.*
import java.lang.StringBuilder
import java.util.*

object KeyUtils {

    fun readKey(keyFile: File, kR: IntArray, kC: IntArray): Int {
        val sc = Scanner(keyFile)
        val kRLine = sc.nextLine()
        val kArray = kRLine.split(" ").toTypedArray()
        if (kR.size + kC.size + 1 != kArray.size) {
            sc.close()
            throw Exception("Invalid key file")
        }
        var j = 0
        for (i in kR.indices)
            kR[i] = kArray[j].toInt().also { j++ }

        for (i in kC.indices)
            kC[i] = kArray[j].toInt().also { j++ }

        return kArray[j].toInt().also { sc.close() }
    }


    fun writeKey(keyFile: File, kR: IntArray, kC: IntArray, iterations: Int) {
        val s = StringBuilder()
        for (i in kR) {
            s.append("$i ")
        }
        for (i in kC) {
            s.append("$i ")
        }
        s.append(iterations)
        keyFile.writeBytes(String(s).toByteArray())
    }
}
