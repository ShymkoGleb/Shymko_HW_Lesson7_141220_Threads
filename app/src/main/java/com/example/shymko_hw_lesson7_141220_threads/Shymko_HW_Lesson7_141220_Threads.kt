package com.example.test_threads

import java.util.concurrent.Executors
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.write

//  3. Створити проект, де буде 4 потока, які повинні постійно збільшувати counter кожних 10 мс.
//  При цьому має бути 5-й поток, який буде виводити значення counter в консоль кожних 1000 мс.
//  Ви самі повинні вибрати спосіб уникнення race condition.

fun main() {
    var counter1 = 0L

    val semaphore = Semaphore(1)

    fun increaseCounterWithoutRaceCondition() {
        while (true) {
            semaphore.acquire()
            Thread.sleep(10)
            counter1 += 1
            println("FUN increaseCounter counter =___ $counter1  ____name of Thread is -  ${Thread.currentThread()}")
            semaphore.release()
        }
    }

    fun showCounter() {
        while (true) {
            Thread.sleep(1000)
            println("Show counter = _________________________________________________________________________________________________________________________________________$counter1")
        }
    }

    val executor1 = Executors.newFixedThreadPool(4)
    executor1.execute(::increaseCounterWithoutRaceCondition)
    executor1.execute(::increaseCounterWithoutRaceCondition)
    executor1.execute(::increaseCounterWithoutRaceCondition)
    executor1.execute(::increaseCounterWithoutRaceCondition)

    val executor2 = Executors.newSingleThreadExecutor()
    executor2.execute(::showCounter)

    executor1.shutdown()
    executor1.awaitTermination(10, TimeUnit.SECONDS)

}