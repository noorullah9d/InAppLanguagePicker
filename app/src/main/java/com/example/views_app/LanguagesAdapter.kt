package com.example.views_app

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.views_app.databinding.ItemLanguageBinding

class LanguagesAdapter(
    selectedLanguage: LanguageModel,
    private val selectedLanguageCallback: (LanguageModel) -> Unit
) :
    RecyclerView.Adapter<LanguagesAdapter.LanguageViewHolder>() {

    private var languageList: List<LanguageModel> = arrayListOf()
    private var filteredList: List<LanguageModel> = arrayListOf()
    private var currentSelected = selectedLanguage


    fun setLanguageList(languageList: List<LanguageModel>) {
        this.languageList = languageList
        this.filteredList = languageList // Reset the filtered list to the full list
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String, visibility: (Boolean) -> Unit) {
        filteredList = if (query.isEmpty()) {
            languageList
        } else {
            val lowerCaseQuery = query.lowercase()
            languageList.filter {
                it.languageName.lowercase().contains(lowerCaseQuery)
            }
        }
        notifyDataSetChanged()
        visibility(filteredList.isEmpty())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding =
            ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bindData(filteredList[position], currentSelected) {
            selectedLanguageCallback(it)
            currentSelected = it
            notifyDataSetChanged()
        }
    }

    inner class LanguageViewHolder(private val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(
            language: LanguageModel,
            selectedLang: LanguageModel,
            selectedLanguage: (LanguageModel) -> Unit
        ) {
            binding.apply {
                languageName.text = language.languageName
                languageLocalName.text = language.languageLocalName
                if (language.languageCode == selectedLang.languageCode) {
                    root.setBackgroundColor(ContextCompat.getColor(languageName.context, R.color.purple_200))
                    languageLocalName.setTextColor(ContextCompat.getColor(languageLocalName.context, R.color.white))
                } else {
                    root.setBackgroundColor(ContextCompat.getColor(languageName.context, R.color.white))
                    languageLocalName.setTextColor(ContextCompat.getColor(languageLocalName.context, R.color.purple_200))
                }
                root.setOnClickListener {
                    selectedLanguage(language)
                }
            }
        }
    }
}