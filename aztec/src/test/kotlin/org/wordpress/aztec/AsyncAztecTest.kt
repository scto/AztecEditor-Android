package org.wordpress.aztec

import android.app.Activity
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.ToggleButton
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.wordpress.aztec.source.SourceViewEditText
import org.wordpress.aztec.toolbar.AztecToolbar
import org.wordpress.aztec.toolbar.ToolbarAction
import org.wordpress.aztec.toolbar.ToolbarItems

@RunWith(RobolectricTestRunner::class)
class AsyncAztecTest {
    lateinit var editText: AztecText
    lateinit var sourceText: SourceViewEditText
    lateinit var toolbar: AztecToolbar
    lateinit var buttonQuote: ToggleButton
    lateinit var menuHeading: PopupMenu
    lateinit var menuHeading1: MenuItem
    lateinit var menuHeading2: MenuItem
    lateinit var menuParagraph: MenuItem
    lateinit var buttonPreformat: ToggleButton
    lateinit var buttonBold: ToggleButton

    /**
     * Initialize variables.
     */
    @Before
    fun init() {
        val activity = Robolectric.buildActivity(Activity::class.java).create().visible().get()
        editText = AztecText(activity)
        editText.setCalypsoMode(false)
        sourceText = SourceViewEditText(activity)
        sourceText.setCalypsoMode(false)
        toolbar = AztecToolbar(activity)
        toolbar.setToolbarItems(
            ToolbarItems.BasicLayout(
            ToolbarAction.HEADING,
            ToolbarAction.PREFORMAT,
            ToolbarAction.LIST,
            ToolbarAction.QUOTE,
            ToolbarAction.BOLD,
            ToolbarAction.ITALIC,
            ToolbarAction.LINK,
            ToolbarAction.UNDERLINE,
            ToolbarAction.STRIKETHROUGH,
            ToolbarAction.ALIGN_LEFT,
            ToolbarAction.ALIGN_CENTER,
            ToolbarAction.ALIGN_RIGHT,
            ToolbarAction.HORIZONTAL_RULE,
            ToolbarAction.HTML
        ))
        toolbar.setEditor(editText, sourceText)
        buttonQuote = toolbar.findViewById<ToggleButton>(R.id.format_bar_button_quote)
        menuHeading = toolbar.getHeadingMenu() as PopupMenu
        menuHeading1 = menuHeading.menu.getItem(1)
        menuHeading2 = menuHeading.menu.getItem(2)
        menuParagraph = menuHeading.menu.getItem(0)
        buttonPreformat = toolbar.findViewById<ToggleButton>(R.id.format_bar_button_pre)
        buttonBold = toolbar.findViewById<ToggleButton>(R.id.format_bar_button_bold)
        activity.setContentView(editText)
    }

    @Test
    @Throws(Exception::class)
    fun asyncToHtmlWorksOnCurrentVersion() = runTest {
        editText.append("One two three")
        val textCopy = editText.getTextCopy()
        Assert.assertEquals("One two three", editText.toHtmlAsync(textCopy))
        val jobs = mutableListOf<Job>()
        jobs.add(launch {
            Assert.assertEquals("One two three", editText.toHtmlAsync(textCopy))
        })
        toolbar.onMenuItemClick(menuHeading1)
        val textCopy2 = editText.getTextCopy()
        jobs.add(launch {
            Assert.assertEquals("<h1>One two three</h1>", editText.toHtmlAsync(textCopy2))
        })
        toolbar.onMenuItemClick(menuParagraph)
        val textCopy3 = editText.getTextCopy()
        jobs.add(launch {
            Assert.assertEquals("One two three", editText.toHtmlAsync(textCopy3))
        })
        val from = editText.editableText.indexOf("two")
        editText.setSelection(from, from + 3)
        editText.toggleFormatting(AztecTextFormat.FORMAT_BOLD)
        val textCopy4 = editText.getTextCopy()
        jobs.add(launch {
            Assert.assertEquals("One <strong>two</strong> three", editText.toHtmlAsync(textCopy4))
        })
        jobs.forEach {
            it.join()
            Assert.assertTrue(it.isCompleted)
        }
    }
}
