package com.example.views_app

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.views_app.databinding.DialogLanguagesLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun NavController.openFragment(
    destinationId: Int,
    addToBackStack: Boolean,
    bundle: Bundle? = null
) {
    if (!addToBackStack) {
        this.popBackStack()
    }

    val navOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.open_enter)
        .setExitAnim(R.anim.open_exit)
        .setPopEnterAnim(R.anim.close_enter)
        .setPopExitAnim(R.anim.close_exit)
        .build()

    this.navigate(destinationId, bundle, navOptions)
}

fun Activity.showLanguagesBottomSheet(
    selectedLanguage: ((LanguageModel) -> Unit)
) {
    val binding: DialogLanguagesLayoutBinding by lazy {
        DialogLanguagesLayoutBinding.inflate(layoutInflater)
    }
    val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
    dialog.setCancelable(true)
    dialog.setContentView(binding.root)

    // Access BottomSheetBehavior and set its initial state
    val bottomSheetBehavior = BottomSheetBehavior.from(binding.root.parent as View)
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    binding.apply {
        val selectedLang = PrefUtils.selectedLanguage
        val languagesAdapter = LanguagesAdapter(selectedLang) {
            selectedLanguage.invoke(it)
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }

        languagesRv.apply {
            adapter = languagesAdapter
            layoutManager = LinearLayoutManager(this@showLanguagesBottomSheet)
            languagesAdapter.setLanguageList(languagesList())
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }
            })
        }
    }
    dialog.show()
}

fun languagesList(): List<LanguageModel> {
    return arrayListOf(
        LanguageModel("English", "English", "en"),
//        LanguageModel("Afrikaans", "Afrikaans", "af"),
        LanguageModel("Arabic", "عربي", "ar"),
        LanguageModel("Chinese", "汉语", "zh"),
//        LanguageModel("Czech", "čeština", "cs"),
//        LanguageModel("Danish", "dansk", "da"),
//        LanguageModel("Dutch", "Nederlands", "nl"),
//        LanguageModel("French", "français", "fr"),
//        LanguageModel("German", "Deutsch", "de"),
//        LanguageModel("Greek", "ελληνικά", "el"),
        LanguageModel("Hindi", "हिन्दी", "hi"),
//        LanguageModel("Indonesian", "Bahasa Indonesia", "in"),
//        LanguageModel("Italian", "italiano", "it"),
        LanguageModel("Japanese", "日本語", "ja"),
//        LanguageModel("Malay", "məˈlā", "ms"),
//        LanguageModel("Korean", "한국인", "ko"),
//        LanguageModel("Norwegian", "norsk", "no"),
//        LanguageModel("Persian", "فارسی", "fa"),
//        LanguageModel("Portuguese", "Português", "pt"),
//        LanguageModel("Russian", "російський", "ru"),
        LanguageModel("Spanish", "español", "es"),
//        LanguageModel("Thai", "ไทย", "th"),
//        LanguageModel("Turkish", "Türk", "tr"),
//        LanguageModel("Vietnamese", "Tiếng Việt", "vi")
    )
}