package org.wordpress.aztec.plugins.wpcomments.spans

import android.graphics.drawable.Drawable
import org.wordpress.aztec.AztecText
import org.wordpress.aztec.spans.AztecDynamicImageSpan
import org.wordpress.aztec.spans.IAztecFullWidthImageSpan
import java.lang.ref.WeakReference

class GutenbergInlineCommentSpan @JvmOverloads constructor(val commentText: String, drawable: Drawable, override var nestingLevel: Int, editor: AztecText? = null) :
        AztecDynamicImageSpan(drawable), IAztecFullWidthImageSpan {

    init {
        textView = WeakReference(editor)
    }
}
