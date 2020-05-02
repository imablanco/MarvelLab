package com.ablanco.marvellab.core.test

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import java.util.*

/**
 * Created by Álvaro Blanco Cabrero on 02/05/2020.
 * MarvelLab.
 */

fun randomId() = UUID.randomUUID().toString()

@ExperimentalCoroutinesApi
fun BaseViewModelTest.runTest(testBody: suspend TestCoroutineScope.() -> Unit) =
    runBlockingTest(coroutinesTestRule.testDispatcher, testBody)

