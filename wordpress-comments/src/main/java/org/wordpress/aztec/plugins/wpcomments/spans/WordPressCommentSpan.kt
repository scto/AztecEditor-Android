package org.wordpress.aztec.plugins.wpcomments.spans

import android.graphics.drawable.Drawable
import org.wordpress.aztec.AztecText
import org.wordpress.aztec.spans.AztecDynamicImageSpan
import org.wordpress.aztec.spans.IAztecFullWidthImageSpan
import java.lang.ref.WeakReference

class WordPressCommentSpan @JvmOverloads constructor(val commentText: String, drawable: Drawable, override var nestingLevel: Int, editor: AztecText? = null) :
        AztecDynamicImageSpan(drawable), IAztecFullWidthImageSpan {

    init {
        textView = WeakReference(editor)
    }

    companion object {
        private val HTML_MORE: String = "more"
        private val HTML_PAGE: String = "nextpage"
    }

    enum class Comment constructor(val html: String) {
        MORE(HTML_MORE),
        PAGE(HTML_PAGE)
    }
}
