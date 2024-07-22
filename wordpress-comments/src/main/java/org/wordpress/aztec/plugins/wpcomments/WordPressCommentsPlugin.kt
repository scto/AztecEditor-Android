package org.wordpress.aztec.plugins.wpcomments

import android.text.Editable
import android.text.Spannable
import android.text.style.CharacterStyle
import androidx.appcompat.content.res.AppCompatResources
import org.wordpress.aztec.AztecText
import org.wordpress.aztec.Constants
import org.wordpress.aztec.plugins.html2visual.IHtmlCommentHandler
import org.wordpress.aztec.plugins.visual2html.IInlineSpanHandler
import org.wordpress.aztec.plugins.wpcomments.spans.WordPressCommentSpan
import java.util.Locale

class WordPressCommentsPlugin(private val visualEditor: AztecText) : IInlineSpanHandler, IHtmlCommentHandler {

    override fun canHandleSpan(span: CharacterStyle): Boolean {
        return span is WordPressCommentSpan
    }

    override fun shouldParseContent(): Boolean {
        return false
    }

    override fun handleSpanStart(html: StringBuilder, span: CharacterStyle) {
        html.append("<!--")
        html.append((span as WordPressCommentSpan).commentText)
    }

    override fun handleSpanEnd(html: StringBuilder, span: CharacterStyle) {
        html.append("-->")
    }

    override fun handleComment(text: String, output: Editable, nestingLevel: Int, updateNesting: (Int) -> Unit): Boolean {

        val spanStart = output.length

        if (text.lowercase(Locale.getDefault()) ==
                WordPressCommentSpan.Comment.MORE.html.lowercase(Locale.getDefault())) {

            output.append(Constants.MAGIC_CHAR)

            output.setSpan(
                    WordPressCommentSpan(
                            text,
                            AppCompatResources.getDrawable(visualEditor.context, R.drawable.img_more)!!,
                            nestingLevel
                    ),
                    spanStart,
                    output.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return true
        } else if (text.lowercase(Locale.getDefault()) ==
                WordPressCommentSpan.Comment.PAGE.html.lowercase(Locale.getDefault())) {

            output.append(Constants.MAGIC_CHAR)

            output.setSpan(
                    WordPressCommentSpan(
                            text,
                            AppCompatResources.getDrawable(visualEditor.context, R.drawable.img_page)!!,
                            nestingLevel
                    ),
                    spanStart,
                    output.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return true
        }
        return false
    }
}
