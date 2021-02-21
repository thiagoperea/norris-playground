package com.thiagoperea.norrisplayground.presentation.screens.main

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.thiagoperea.norrisplayground.business.model.JokeItem
import com.thiagoperea.norrisplayground.presentation.R
import com.thiagoperea.norrisplayground.presentation.databinding.ActivityMainBinding
import com.thiagoperea.norrisplayground.presentation.databinding.DialogLoadingBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
        setupObservers()
        viewModel.refreshJoke()
    }

    private fun setupListeners() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mainMenuRefresh -> viewModel.refreshJoke()
                R.id.mainMenuFilter -> viewModel.getCategories()
            }

            return@setOnMenuItemClickListener true
        }
    }

    private fun setupObservers() {
        viewModel.mainState.observe(this) { state ->
            when (state) {
                is MainState.Loading -> showLoading(state.message)
                is MainState.Success -> onSuccess(state.joke)
                is MainState.Error -> onError()
            }
        }
    }

    private fun showLoading(@StringRes titleRes: Int) {
        val dialogBinding = DialogLoadingBinding.inflate(layoutInflater, null, false)
        dialogBinding.title.text = getString(titleRes)

        MaterialAlertDialogBuilder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()
    }

    private fun onSuccess(joke: JokeItem) {

    }

    private fun onError() {
        TODO("Not yet implemented")
    }
}
