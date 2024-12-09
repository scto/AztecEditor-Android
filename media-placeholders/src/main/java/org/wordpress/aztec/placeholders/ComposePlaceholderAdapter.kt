package org.wordpress.aztec.placeholders

import androidx.compose.runtime.Composable
import org.wordpress.aztec.AztecAttributes

interface ComposePlaceholderAdapter : PlaceholderManager.PlaceholderAdapter {
    @Composable
    fun Placeholder(
        placeholderUuid: String,
        attrs: AztecAttributes,
        width: Int,
        height: Int,
    ) {}
}
