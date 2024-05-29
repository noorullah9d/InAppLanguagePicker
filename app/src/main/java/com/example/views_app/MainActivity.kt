/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.views_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.views_app.PrefUtils.STATUS_DONE
import com.example.views_app.PrefUtils.firstTimeMigration
import com.example.views_app.PrefUtils.selectedLanguage
import com.example.views_app.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    /**
     * This is a sample code that explains the use of getter and setter APIs for Locales introduced
     * in the Per-App language preferences. Here is an example use of the AndroidX Support Library
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

        /* NOTE: If you were handling the locale storage on you own earlier, you will need to add a
        one time migration for switching this storage from a custom way to the AndroidX storage.

        This can be done in the following manner. Lets say earlier the locale preference was
        stored in a SharedPreference */

        // Check if the migration has already been done or not
        if (firstTimeMigration != STATUS_DONE) {
            // Fetch the selected language from wherever it was stored. In this case its SharedPref
            selectedLanguage.let {
                // Set this locale using the AndroidX library that will handle the storage itself
                val localeList = LocaleListCompat.forLanguageTags(it.languageCode)
                AppCompatDelegate.setApplicationLocales(localeList)
                // Set the migration flag to ensure that this is executed only once
                firstTimeMigration = STATUS_DONE
            }
        }

        // Fetching the current application locale using the AndroidX support Library
        val currentLocaleName = if (!AppCompatDelegate.getApplicationLocales().isEmpty) {
            // Fetches the current Application Locale from the list
            AppCompatDelegate.getApplicationLocales()[0]?.displayName
        } else {
            // Fetches the default System Locale
            Locale.getDefault().displayName
        }

        // Displaying the selected locale on screen
        binding.tvSelectedLanguage.text = currentLocaleName

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.apply {
            btnLanguages.setOnClickListener {
                showLanguagesBottomSheet {
                    selectedLanguage = it
                    val localeList = LocaleListCompat.forLanguageTags(it.languageCode)
                    AppCompatDelegate.setApplicationLocales(localeList)
                }
            }
        }
    }
}
