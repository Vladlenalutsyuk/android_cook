package com.example.myapplication3

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

class VolleySingleton private constructor(context: Context) {

    private val appContext = context.applicationContext

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(appContext)
    }

    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue, object : ImageLoader.ImageCache {
            private val cache = object : LruCache<String, Bitmap>(20 * 1024 * 1024) {
                override fun sizeOf(key: String, value: Bitmap): Int {
                    return value.byteCount
                }
            }

            override fun getBitmap(url: String): Bitmap? {
                return cache.get(url)
            }

            override fun putBitmap(url: String, bitmap: Bitmap) {
                cache.put(url, bitmap)
            }
        })
    }

    companion object {
        @Volatile
        private var INSTANCE: VolleySingleton? = null

        fun getInstance(context: Context): VolleySingleton {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: VolleySingleton(context).also { INSTANCE = it }
            }
        }
    }
}