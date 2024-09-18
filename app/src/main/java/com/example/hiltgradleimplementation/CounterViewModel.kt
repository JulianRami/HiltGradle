package com.example.hiltgradleimplementation;

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(@ApplicationContext private val context: Context) : ViewModel() {

    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> get() = _count

    private val _images = MutableLiveData<MutableList<Int>>(mutableListOf())
    val images: MutableLiveData<MutableList<Int>> get() = _images

    init {
        loadCount()
    }

    private fun loadCount() {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        _count.value = sharedPreferences.getInt("counter", 0)
    }

    fun incrementCount() {
        val newCount = (_count.value ?: 0) + 1
        _count.value = newCount
        saveCount(newCount)

        _images.value?.add(R.drawable.image_resource)
        _images.value = _images.value
    }

    private fun saveCount(count: Int) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("counter", count)
            apply()
        }
    }
}
