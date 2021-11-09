package ru.marslab.casespace.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import dagger.hilt.android.AndroidEntryPoint
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ViewElementsVisibility {

    private val navController: NavController by lazy {
        (supportFragmentManager
            .findFragmentById(binding.activityMainContent.mainFragmentContainer.id) as NavHostFragment)
            .navController
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theme.applyStyle(mainViewModel.getCurrentTheme(), true)
        setContentView(binding.root)
        initListeners()
        initView()
    }

    private fun initView() {
        val mainToolbar = binding.activityMainContent.mainToolbar
        setSupportActionBar(mainToolbar)
        mainToolbar.setupWithNavController(
            navController,
            AppBarConfiguration(navController.graph, binding.root)
        )
        binding.mainNavView.setupWithNavController(navController)
        binding.activityMainContent.mainBottomNavigation.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initListeners() {
        binding.activityMainContent.wikiSearch.also {
            it.setEndIconOnClickListener {
                val searchText = binding.activityMainContent.wikiSearchText.text.toString()
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://en.wikipedia.org/wiki/$searchText")
                })
            }
            it.setStartIconOnClickListener {
                val transitionSet = TransitionSet()
                    .addTransition(Slide(Gravity.START).addTarget(binding.activityMainContent.mainToolbar))
                    .setDuration(1000L)
                TransitionManager.beginDelayedTransition(binding.root, transitionSet)
                wikiSearchVisibility(false)
                toolbarVisibility(true)
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_menu_settings -> {
                navController.navigate(R.id.show_settingsFragment)
                true
            }
            R.id.item_menu_wiki -> {
                TransitionManager.beginDelayedTransition(binding.root)
                wikiSearchVisibility(true)
                toolbarVisibility(false)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun toolbarVisibility(status: Boolean) {
        supportActionBar?.run {
            if (status) {
                show()
            } else {
                hide()
            }
        }
    }

    override fun wikiSearchVisibility(status: Boolean) {
        binding.activityMainContent.wikiSearch.visibility = if (status) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun buttonNavVisibility(status: Boolean) {
        binding.activityMainContent.mainBottomNavigation.visibility = if (status) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun wikiMenuItemVisibility(status: Boolean) {
        binding.activityMainContent.mainToolbar.menu.getItem(1).isEnabled = status
        invalidateOptionsMenu()
    }

}