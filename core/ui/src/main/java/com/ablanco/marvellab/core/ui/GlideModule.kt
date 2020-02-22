package com.ablanco.marvellab.core.ui

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule

/**
 * Created by Álvaro Blanco Cabrero on 2020-02-22.
 * MarvelLab.
 */
@GlideModule
class GlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDefaultTransitionOptions(
            Drawable::class.java,
            DrawableTransitionOptions.withCrossFade()
        )
    }
}