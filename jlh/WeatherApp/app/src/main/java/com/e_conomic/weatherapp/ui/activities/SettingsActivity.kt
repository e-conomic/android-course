package com.e_conomic.weatherapp.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.e_conomic.weatherapp.R
import com.e_conomic.weatherapp.extensions.Preference
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.toolbar.*

class SettingsActivity : AppCompatActivity() {

    companion object {
        val CITY_ID = "cityId"
        val DEFAULT_ID = 2618425L
    }

    var cityId: Long by Preference(this, CITY_ID, DEFAULT_ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        settingsCityId.setText(cityId.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {onBackPressed(); true}
        else -> false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cityId = settingsCityId.text.toString().toLong()
    }
}
