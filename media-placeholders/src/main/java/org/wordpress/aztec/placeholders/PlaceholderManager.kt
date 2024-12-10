package org.wordpress.aztec.placeholders

import android.view.MotionEvent
import android.view.View
import org.wordpress.aztec.AztecAttributes
import org.xml.sax.Attributes
import kotlin.math.min

interface PlaceholderManager {
    fun removeItem(uuid: String)
    fun removeItem(predicate: (Attributes) -> Boolean)
    suspend fun removeOrUpdate(uuid: String, shouldUpdateItem: (Attributes) -> Boolean, updateItem: (currentAttributes: Map<String, String>) -> Map<String, String>): Boolean
    suspend fun insertOrUpdateItem(
        type: String,
        shouldMergeItem: (currentItemType: String) -> Boolean = { true },
        updateItem: (
            currentAttributes: Map<String, String>?,
            currentType: String?,
            placeAtStart: Boolean
        ) -> Map<String, String>
    )

    data class Placeholder(val elementPosition: Int, val uuid: String)

    companion object {
        internal const val DEFAULT_HTML_TAG = "placeholder"
        internal const val UUID_ATTRIBUTE = "uuid"
        internal const val TYPE_ATTRIBUTE = "type"
        internal const val EDITOR_INNER_PADDING = 20
    }
    /**
     * A adapter for a custom view drawn over the placeholder in the Aztec text.
     */
    interface PlaceholderAdapter : View.OnTouchListener {
        /**
         * Called when the placeholder is deleted by the user. Use this method if you need to clear your data when the
         * item is deleted (for example delete an image in your DB).
         * @param placeholderUuid placeholder UUID
         */
        fun onPlaceholderDeleted(placeholderUuid: String) {}

        /**
         * This method is called when the placeholders are destroyed
         */
        fun onDestroy() {}

        /**
         * Override this method if you want to handle view touches. To handle clicks on subviews just use
         * `setOnClickListener` on the view that you want to handle the click.
         */
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            return false
        }

        /**
         * Define unique string type here in order to differentiate between the adapters drawing the custom views.
         */
        val type: String

        /**
         * Returns width of the view based on the HTML attributes. Use this method to either set fixed width or to
         * calculate width based on the view.
         */
        suspend fun getWidth(attrs: AztecAttributes): Proportion = Proportion.Ratio(1.0f)

        /**
         * Returns height of the view based on the HTML attributes. Use this method to either set fixed height or to
         * calculate width based on the view.
         */
        suspend fun getHeight(attrs: AztecAttributes): Proportion

        /**
         * Returns height of the view based on the width and the placeholder height.
         */
        suspend fun calculateHeight(attrs: AztecAttributes, windowWidth: Int): Int {
            return getHeight(attrs).let { height ->
                when (height) {
                    is Proportion.Fixed -> height.value
                    is Proportion.Ratio -> {
                        val ratio = if (height.ratio < 0.1) {
                            0.1f
                        } else {
                            height.ratio
                        }
                        val result = (ratio * calculateWidth(attrs, windowWidth)).toInt()
                        if (height.limit != null && height.limit < result) {
                            height.limit
                        } else {
                            result
                        }
                    }
                }
            }
        }

        /**
         * Returns height of the view based on the width and the placeholder height.
         */
        suspend fun calculateWidth(attrs: AztecAttributes, windowWidth: Int): Int {
            return getWidth(attrs).let { width ->
                when (width) {
                    is Proportion.Fixed -> min(windowWidth, width.value)
                    is Proportion.Ratio -> {
                        val safeRatio: Float = when {
                            width.ratio < 0.1 -> 0.1f
                            width.ratio > 1.0 -> 1.0f
                            else -> width.ratio
                        }
                        val result = (safeRatio * windowWidth).toInt()
                        if (width.limit != null && result > width.limit) {
                            width.limit
                        } else {
                            result
                        }
                    }
                }
            }
        }

        sealed class Proportion {
            data class Fixed(val value: Int, val limit: Int? = null) : Proportion()
            data class Ratio(val ratio: Float, val limit: Int? = null) : Proportion()
        }
    }

    suspend fun insertItem(type: String, vararg attributes: Pair<String, String>)
}
