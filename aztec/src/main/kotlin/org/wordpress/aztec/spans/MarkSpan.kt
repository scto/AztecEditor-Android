package org.wordpress.aztec.spans

import android.graphics.Color
import android.text.TextPaint
import android.text.style.CharacterStyle
import org.wordpress.aztec.AztecAttributes
import org.wordpress.aztec.source.CssStyleFormatter

class MarkSpan : CharacterStyle, IAztecInlineSpan {
    override var TAG = "mark"

    override var attributes: AztecAttributes = AztecAttributes()
    private val textColorValue: Int?

    constructor(attributes: AztecAttributes = AztecAttributes()) : super() {
        this.attributes = attributes

        val color = CssStyleFormatter.getStyleAttribute(attributes,
            CssStyleFormatter.CSS_COLOR_ATTRIBUTE)
        textColorValue = safelyParseColor(color)
    }

    constructor(attributes: AztecAttributes = AztecAttributes(), colorString: String?) : super() {
        this.attributes = attributes
        textColorValue = safelyParseColor(colorString)
    }

    private fun safelyParseColor(colorString: String?): Int? {
        if (colorString.isNullOrBlank()) {
            return null
        }
        return try {
            Color.parseColor(colorString)
        } catch (e: IllegalArgumentException) {
            // Unknown color
            null
        }
    }

    override fun updateDrawState(tp: TextPaint) {
        textColorValue?.let { tp.color = it }
    }

    fun getTextColor(): String {
        val currentColor = textColorValue ?: 0
        return String.format("#%06X", 0xFFFFFF and currentColor)
    }
}
