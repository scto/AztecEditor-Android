package org.wordpress.aztec.spans

import android.graphics.drawable.Drawable
import org.wordpress.aztec.AztecAttributes
import org.wordpress.aztec.AztecText
import java.lang.ref.WeakReference

class AztecHorizontalRuleSpan(drawable: Drawable, override var nestingLevel: Int,
                              override var attributes: AztecAttributes = AztecAttributes(), editor: AztecText? = null) :
        AztecDynamicImageSpan(drawable), IAztecFullWidthImageSpan, IAztecSpan {
    init {
        textView = WeakReference(editor)
    }

    override val TAG: String = "hr"
}
