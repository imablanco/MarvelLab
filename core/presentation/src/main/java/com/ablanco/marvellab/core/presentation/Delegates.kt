package com.ablanco.marvellab.core.presentation

import kotlinx.coroutines.Job
import kotlin.properties.Delegates

/**
 * Created by Álvaro Blanco Cabrero on 2020-01-29.
 * MarvelLab.
 */

fun autoCancelableJob(initialJob: Job? = null) =
    Delegates.observable(initialJob) { _, oldJob, _ -> oldJob?.cancel() }