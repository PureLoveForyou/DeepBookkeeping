package com.android.deepbookkeeping

import android.os.Bundle
import android.view.SurfaceControl.Transaction
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.android.deepbookkeeping.ui.bottomsheet.AddTransactionDialogFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.navigation_view)
        val controller: NavController = this.findNavController(R.id.fragmentContainerView)

//        val configuration = AppBarConfiguration.Builder(bottomNavigationView.menu).build()
//        NavigationUI.setupActionBarWithNavController(this, controller, configuration)
        NavigationUI.setupWithNavController(bottomNavigationView, controller)


//        val configuration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home,
//                R.id.navigation_report,
//                R.id.navigation_settings
//            )
//        )
//
//        NavigationUI.setupActionBarWithNavController(this, controller, configuration)
//        bottomNavigationView.setupWithNavController(controller)
    }
}