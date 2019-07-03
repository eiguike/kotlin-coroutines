package com.monstro

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.io.File
import java.util.*

class Monstro {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun execute(chunk: MutableList<String>) {
        runBlocking {
           chunk.forEach {
               launch(Dispatchers.Default) {
                   callAPI(it)
               }
           }
        }

        Thread.sleep(1000)
    }

    fun callAPI(cbk: String) {
        logger.info("Calliing API for: $cbk")
        val response = khttp.get("http://httpstat.us/200?sleep=${(1..5).shuffled().first()}000", timeout = 60.0)

        if (response.statusCode == 200) {
            logger.info("Sucess sending: $cbk")
        }
    }

}

fun main(args: Array<String>) {
    val scanner = Scanner(File("/Users/henrique.eihara/test.txt"))
    val chunk: MutableList<String> = ArrayList()

    while (scanner.hasNextLine()) {
       chunk.add(scanner.nextLine())
        if (chunk.size < 5 && scanner.hasNextLine()) continue

        Monstro().execute(chunk)

        chunk.clear()
    }
}