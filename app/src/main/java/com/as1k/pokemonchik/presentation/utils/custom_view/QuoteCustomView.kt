package com.as1k.pokemonchik.presentation.utils.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.as1k.pokemonchik.R

class QuoteCustomView : ConstraintLayout {
    private lateinit var tvQuote: TextView
    private lateinit var tvAuthor: TextView

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    fun setQuote(text: String) {
        tvQuote.text = resources.getString(R.string.quotation_marks, text)
    }

    fun setAuthor(text: String) {
        if (text.isEmpty()) tvAuthor.visibility = View.GONE
        else tvAuthor.text = resources.getString(R.string.copyright, text)
    }

    fun setQuoteAndAuthor(quote: String, author: String) {
        tvQuote.text = resources.getString(R.string.quotation_marks, quote)
        if (author.isEmpty()) tvAuthor.visibility = View.GONE
        else tvAuthor.text = resources.getString(R.string.copyright, author)
    }

    private fun init(context: Context) {
        inflate(context, R.layout.item_quote_custom_view, this)
        tvQuote = findViewById(R.id.tvQuote)
        tvAuthor = findViewById(R.id.tvAuthor)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        init(context)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.QuoteCustomView)
        tvQuote.text = resources.getString(
            R.string.quotation_marks,
            typedArray.getText(R.styleable.QuoteCustomView_itemText)
        )
        tvAuthor.text = resources.getString(
            R.string.copyright,
            typedArray.getText(R.styleable.QuoteCustomView_itemAuthor)
        )
        typedArray.recycle()
    }
}
