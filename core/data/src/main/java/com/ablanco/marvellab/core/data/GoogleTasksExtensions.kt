package com.ablanco.marvellab.core.data

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

/**
 * Created by Álvaro Blanco Cabrero on 26/02/2020.
 * MarvelLab.
 */

fun <T> T.asTask(): Task<T> = Tasks.call { this }