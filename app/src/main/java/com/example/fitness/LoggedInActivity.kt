package com.example.fitness

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.fitness.databinding.ActivityLoggedInBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoggedInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoggedInBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoggedInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
        bottomNavigationView = binding.bottomNavigationView

        val navigationView = binding.navView
        val toolbar = binding.toolbar

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit()
//            navigationView.setCheckedItem(R.id.nav_home)
//        }
//        replaceFragment(HomeFragment())
//        binding.bottomNavigationView.background = null
//        binding.bottomNavigationView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.home -> replaceFragment(HomeFragment())
//                R.id.shorts -> replaceFragment(ShortsFragment())
//                R.id.subscriptions -> replaceFragment(SubscriptionFragment())
//                R.id.library -> replaceFragment(LibraryFragment())
//            }
//            true
//        }
    }

    //    private fun replaceFragment(fragment: Fragment) {
//        val fragmentManager: FragmentManager = supportFragmentManager
//        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.frame_layout, fragment)
//        fragmentTransaction.commit()
//    }
    fun waterActivity(view: View) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}