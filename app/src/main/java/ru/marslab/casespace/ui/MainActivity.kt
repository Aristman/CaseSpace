package ru.marslab.casespace.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.marslab.casespace.R
import ru.marslab.casespace.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy {
        (supportFragmentManager
            .findFragmentById(binding.activityMainContent.mainFragmentContainer.id) as NavHostFragment)
            .navController
    }

    private val bottomNavDrawerFragment: BottomNavDrawerFragment by lazy {
        BottomNavDrawerFragment()
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
        setSupportActionBar(binding.mainBottomNavBar)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.settingsFragment -> {
                    setNavVisibility(View.GONE)
                }
                else -> {
                    setNavVisibility(View.VISIBLE)
                }
            }
        }
    }

    private fun setNavVisibility(visibility: Int) {
        binding.activityMainContent.wikiSearch.visibility = visibility
        binding.mainBottomNavBar.visibility = visibility
        binding.mainFab.visibility = visibility
    }

    private fun initListeners() {
        binding.activityMainContent.wikiSearch.setEndIconOnClickListener {
            val searchText = binding.activityMainContent.wikiSearchText.text.toString()
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/$searchText")
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bottom_bar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_menu_settings -> {
                navController.navigate(R.id.show_settingsFragment)
                true
            }
            android.R.id.home -> {
                bottomNavDrawerFragment.show(
                    supportFragmentManager,
                    BottomNavDrawerFragment.FRAGMENT_TAG
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}