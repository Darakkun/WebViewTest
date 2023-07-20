package ennbose.sinewers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ennbose.sinewers.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val modelProvider: MainViewModel by lazy { MainViewModel.viewModelWithActivity(this) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modelProvider.initDatabase(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
