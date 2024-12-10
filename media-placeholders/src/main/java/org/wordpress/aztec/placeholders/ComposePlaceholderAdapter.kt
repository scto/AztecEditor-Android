package org.wordpress.aztec.placeholders

import androidx.compose.runtime.Composable
import org.wordpress.aztec.AztecAttributes

interface ComposePlaceholderAdapter : PlaceholderManager.PlaceholderAdapter {
    /**
     * Use this method to draw the placeholder using Jetpack Compose.
     */
    @Composable
    fun Placeholder(
        placeholderUuid: String,
        attrs: AztecAttributes,
    ) {}
}
