package com.thiagoperea.norrisplayground.presentation.screens.main

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.thiagoperea.norrisplayground.business.model.JokeItem
import com.thiagoperea.norrisplayground.presentation.R
import com.thiagoperea.norrisplayground.presentation.databinding.ActivityMainBinding
import com.thiagoperea.norrisplayground.presentation.databinding.DialogLoadingBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by inject()

    private var loadingDialog: AlertDialog? = null

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
                is MainState.SuccessJoke -> onSuccessJoke(state.joke)
                is MainState.SuccessCategories -> onSuccessCategories(state.categories)
                is MainState.Error -> onError(state.isJokeError)
            }
        }
    }


    private fun showLoading(@StringRes titleRes: Int) {
        val dialogBinding = DialogLoadingBinding.inflate(layoutInflater, null, false)
        dialogBinding.title.text = getString(titleRes)

        loadingDialog = MaterialAlertDialogBuilder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()
    }

    private fun onSuccessJoke(joke: JokeItem) {
        loadingDialog?.dismiss()
        binding.joke.text = joke.joke
        binding.image.setImageDrawable(ContextCompat.getDrawable(this, joke.image))
    }

    private fun onSuccessCategories(categories: List<String>) {
        loadingDialog?.dismiss()

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.select_category))
            .setItems(categories.toTypedArray()) { dialog, which ->
                viewModel.selectedCategory = categories[which]
                binding.categorySelected.text =
                    getString(R.string.category_selected, categories[which])
                dialog.dismiss()
                viewModel.refreshJoke()
            }
            .setCancelable(false)
            .show()

    }

    private fun onError(isJokeError: Boolean) {
        loadingDialog?.dismiss()

        Snackbar.make(binding.root, getString(R.string.error), Snackbar.LENGTH_INDEFINITE).apply {
            setAction(getString(R.string.try_again)) {
                if (isJokeError) {
                    viewModel.refreshJoke()
                } else {
                    viewModel.getCategories()
                }
            }

            show()
        }
    }
}
