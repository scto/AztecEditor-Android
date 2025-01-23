package org.wordpress.aztec.placeholders

import android.content.Context
import android.view.View
import org.wordpress.aztec.AztecAttributes

interface ViewPlaceholderAdapter : PlaceholderManager.PlaceholderAdapter {
    /**
     * Creates the view but it's called before the view is measured. If you need the actual width and height. Use
     * the `onViewCreated` method where the view is already present in its correct size.
     * @param context necessary to build custom views
     * @param placeholderUuid the placeholder UUID
     * @param attrs aztec attributes of the view
     */
    suspend fun createView(context: Context, placeholderUuid: String, attrs: AztecAttributes): View

    /**
     * Called after the view is measured. Use this method if you need the actual width and height of the view to
     * draw your media.
     * @param view the frame layout wrapping the custom view
     * @param placeholderUuid the placeholder ID
     */
    suspend fun onViewCreated(view: View, placeholderUuid: String) {}
}
