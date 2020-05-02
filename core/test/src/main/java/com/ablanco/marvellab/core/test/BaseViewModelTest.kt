package com.ablanco.marvellab.core.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Rule
import org.mockito.Mockito

/**
 * Created by Álvaro Blanco Cabrero on 01/05/2020.
 * MarvelLab.
 */
@ExperimentalCoroutinesApi
abstract class BaseViewModelTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @After
    fun validate() {
        /*Ensure misusing errors are showing in the correct tests and not at the end of the
        execution chain*/
        Mockito.validateMockitoUsage()
    }
}
