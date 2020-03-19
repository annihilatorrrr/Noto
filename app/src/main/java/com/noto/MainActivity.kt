package com.noto

import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.noto.database.AppDatabase
import com.noto.databinding.ActivityMainBinding
import com.noto.network.DAOs


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        DAOs.notebookDao = AppDatabase.getInstance(applicationContext).notebookDao

        DAOs.noteDao = AppDatabase.getInstance(applicationContext).noteDao

        val navController = findNavController(R.id.nav_host_fragment)

        binding.bottomNav.setupWithNavController(navController)


        binding.exFab.setOnClickListener {
            if (binding.exFabNewNotebook.visibility == View.GONE) {

                binding.exFabNewNotebook.visibility = View.VISIBLE

                binding.exFab.text = resources.getString(R.string.cancel)

            } else {

                binding.exFabNewNotebook.visibility = View.GONE

                binding.exFab.text = resources.getString(R.string.create)
            }
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            if (destination.id != R.id.notebookListFragment) {

                binding.exFab.visibility = View.GONE

                binding.bottomNav.visibility = View.GONE

                binding.exFabNewNotebook.visibility = View.GONE

            } else {
                binding.exFab.visibility = View.VISIBLE
                binding.exFab.text = resources.getString(R.string.create)
                binding.bottomNav.visibility = View.VISIBLE
            }
        }

    }
}