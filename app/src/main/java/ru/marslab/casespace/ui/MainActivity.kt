package ru.marslab.casespace.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.marslab.casespace.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavDrawerFragment: BottomNavDrawerFragment
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initListeners()
        initView()
    }

    private fun initView() {
        setSupportActionBar(binding.mainBottomNavBar)
        bottomNavDrawerFragment = BottomNavDrawerFragment()
    }

    private fun initListeners() {
        binding.activityMainContent.wikiSearch.setEndIconOnClickListener {
            val searchText = binding.activityMainContent.wikiSearchText.text.toString()
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/$searchText")
            })
        }
    }

}