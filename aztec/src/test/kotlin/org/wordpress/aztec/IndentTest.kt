package org.wordpress.aztec

import android.app.Activity
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.wordpress.aztec.source.SourceViewEditText
import org.wordpress.aztec.toolbar.AztecToolbar

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23])
class IndentTest {
    lateinit var editText: AztecText
    lateinit var sourceText: SourceViewEditText
    lateinit var toolbar: AztecToolbar

    /**
     * Initialize variables.
     */
    @Before
    fun init() {
        val activity = Robolectric.buildActivity(Activity::class.java).create().visible().get()
        editText = AztecText(activity)
        editText.setCalypsoMode(false)
        editText.setGutenbergMode(true)
        sourceText = SourceViewEditText(activity)
        sourceText.setCalypsoMode(false)
        toolbar = AztecToolbar(activity)
        toolbar.setEditor(editText, sourceText)
        activity.setContentView(editText)
    }

    @Test
    @Throws(Exception::class)
    fun testIndentingHeading() {
        editText.fromHtml("<h1>Heading 1</h1>")

        editText.setSelection(editText.editableText.indexOf("1"))

        editText.indent()

        Assert.assertEquals("<h1>\tHeading 1</h1>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testOutdentingHeading() {
        editText.fromHtml("<h1>\tHeading 1</h1>")

        editText.setSelection(editText.editableText.indexOf("1"))

        editText.outdent()

        Assert.assertEquals("<h1>Heading 1</h1>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testIndentingMultipleHeadings() {
        editText.fromHtml("<h1>Heading 1</h1><h1>Heading 2</h1>")

        editText.setSelection(editText.editableText.indexOf("1"), editText.editableText.indexOf("2"))

        editText.indent()

        Assert.assertEquals("<h1>\tHeading 1</h1><h1>\tHeading 2</h1>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testOutdentingMultipleHeadings() {
        editText.fromHtml("<h1>\tHeading 1</h1><h1>\tHeading 2</h1>")

        editText.setSelection(editText.editableText.indexOf("1"), editText.editableText.indexOf("2"))

        editText.outdent()

        Assert.assertEquals("<h1>Heading 1</h1><h1>Heading 2</h1>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testIndentingQuoteLine() {
        editText.fromHtml("<blockquote>Line 1<br>Line 2<br>Line 3</blockquote>")

        editText.setSelection(editText.editableText.indexOf("1"))

        editText.indent()

        Assert.assertEquals("<blockquote>\tLine 1<br>Line 2<br>Line 3</blockquote>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testOutdentingQuoteLine() {
        editText.fromHtml("<blockquote>\tLine 1<br>Line 2<br>Line 3</blockquote>")

        editText.setSelection(editText.editableText.indexOf("1"))

        editText.outdent()

        Assert.assertEquals("<blockquote>Line 1<br>Line 2<br>Line 3</blockquote>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testIndentingQuote() {
        editText.fromHtml("<blockquote>Line 1<br>Line 2<br>Line 3</blockquote>")

        editText.setSelection(editText.editableText.indexOf("1"), editText.editableText.indexOf("3"))

        editText.indent()

        Assert.assertEquals("<blockquote>\tLine 1<br>\tLine 2<br>\tLine 3</blockquote>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testOutdentingQuote() {
        editText.fromHtml("<blockquote>\tLine 1<br>\tLine 2<br>\tLine 3</blockquote>")

        editText.setSelection(editText.editableText.indexOf("1"), editText.editableText.indexOf("3"))

        editText.outdent()

        Assert.assertEquals("<blockquote>Line 1<br>Line 2<br>Line 3</blockquote>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testIndentingParagraphLine() {
        editText.fromHtml("<p>Line 1<br>Line 2<br>Line 3</p>")

        editText.setSelection(editText.editableText.indexOf("1"))

        editText.indent()

        Assert.assertEquals("<p>\tLine 1<br>Line 2<br>Line 3</p>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testOutdentingParagraphLine() {
        editText.fromHtml("<p>\tLine 1<br>Line 2<br>Line 3</p>")

        editText.setSelection(editText.editableText.indexOf("1"))

        editText.outdent()

        Assert.assertEquals("<p>Line 1<br>Line 2<br>Line 3</p>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testIndentingParagraph() {
        editText.fromHtml("<p>Line 1<br>Line 2<br>Line 3</p>")

        editText.setSelection(editText.editableText.indexOf("1"), editText.editableText.indexOf("3"))

        editText.indent()

        Assert.assertEquals("<p>\tLine 1<br>\tLine 2<br>\tLine 3</p>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testOutdentingParagraph() {
        editText.fromHtml("<p>\tLine 1<br>\tLine 2<br>\tLine 3</p>")

        editText.setSelection(editText.editableText.indexOf("1"), editText.editableText.indexOf("3"))

        editText.outdent()

        Assert.assertEquals("<p>Line 1<br>Line 2<br>Line 3</p>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testIndentingPreformatLine() {
        editText.fromHtml("<pre>Line 1<br>Line 2<br>Line 3</pre>")

        editText.setSelection(editText.editableText.indexOf("1"))

        editText.indent()

        Assert.assertEquals("<pre>\tLine 1<br>Line 2<br>Line 3</pre>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testOutdentingPreformatLine() {
        editText.fromHtml("<pre>\tLine 1<br>Line 2<br>Line 3</pre>")

        editText.setSelection(editText.editableText.indexOf("1"))

        editText.outdent()

        Assert.assertEquals("<pre>Line 1<br>Line 2<br>Line 3</pre>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testIndentingPreformat() {
        editText.fromHtml("<pre>Line 1<br>Line 2<br>Line 3</pre>")

        editText.setSelection(editText.editableText.indexOf("1"), editText.editableText.indexOf("3"))

        editText.indent()

        Assert.assertEquals("<pre>\tLine 1<br>\tLine 2<br>\tLine 3</pre>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testOutdentingPreformat() {
        editText.fromHtml("<pre>\tLine 1<br>\tLine 2<br>\tLine 3</pre>")

        editText.setSelection(editText.editableText.indexOf("1"), editText.editableText.indexOf("3"))

        editText.outdent()

        Assert.assertEquals("<pre>Line 1<br>Line 2<br>Line 3</pre>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testIndentingPureText() {
        editText.fromHtml("123<br>456")

        editText.setSelection(editText.editableText.indexOf("1"), editText.editableText.indexOf("4"))

        editText.indent()

        Assert.assertEquals("\t123<br>\t456", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testOutdentingPureText() {
        editText.fromHtml("\t123<br>\t456")

        editText.setSelection(editText.editableText.indexOf("1"), editText.editableText.indexOf("4"))

        editText.outdent()

        Assert.assertEquals("123<br>456", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun doesNotIndentMedia() {
        editText.fromHtml("<h1>Heading 1</h1><img src=\"test.jpg\" /><h1>Heading 2</h1>")

        editText.setSelection(editText.editableText.indexOf("1"), editText.editableText.indexOf("2"))

        editText.indent()

        Assert.assertEquals("<h1>\tHeading 1</h1><img src=\"test.jpg\" /><h1>\tHeading 2</h1>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testIndentingComplexText() {
        editText.fromHtml("Line 1<br>Line 2<ul><li>List item</li></ul><h1>Heading 3</h1><img src=\"test.jpg\" /><pre>Test pre 1<br>Test pre 2</pre>")

        editText.setSelection(editText.editableText.indexOf("1"), editText.editableText.indexOf("pre 2"))

        editText.indent()

        Assert.assertEquals("\tLine 1<br>\tLine 2<ul><li>List item</li></ul><h1>\tHeading 3</h1><img src=\"test.jpg\" /><pre>\tTest pre 1<br>\tTest pre 2</pre>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testOutdentingComplexText() {
        editText.fromHtml("\tLine 1<br>\tLine 2<ul><li>List item</li></ul><h1>\tHeading 3</h1><img src=\"test.jpg\" /><pre>\tTest pre 1<br>\tTest pre 2</pre>")

        editText.setSelection(editText.editableText.indexOf("1"), editText.editableText.indexOf("pre 2"))

        editText.outdent()

        Assert.assertEquals("Line 1<br>Line 2<ul><li>List item</li></ul><h1>Heading 3</h1><img src=\"test.jpg\" /><pre>Test pre 1<br>Test pre 2</pre>", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testOutdentingSecondLineOnly() {
        editText.fromHtml("\tLine 1<br>\tLine 2<br>\tLine 3")

        editText.setSelection(editText.editableText.indexOf("2"))

        editText.outdent()

        Assert.assertEquals("\tLine 1<br>Line 2<br>\tLine 3", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testCannotOutdentEmptyText() {
        editText.fromHtml("")

        editText.setSelection(0)

        Assert.assertFalse(editText.isOutdentAvailable())
    }

    @Test
    @Throws(Exception::class)
    fun testCanIndentEmptyText() {
        editText.fromHtml("")

        editText.setSelection(0)

        Assert.assertTrue(editText.isIndentAvailable())
        editText.indent()

        Assert.assertEquals("\t", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testCanOutdentSingleIndent() {
        editText.fromHtml("")
        editText.setSelection(0)
        editText.indent()

        editText.setSelection(1)

        Assert.assertTrue(editText.isOutdentAvailable())
        editText.outdent()

        Assert.assertEquals("", editText.toHtml())
    }

    @Test
    @Throws(Exception::class)
    fun testCanOutdentMultipleNestedLists() {
        editText.fromHtml("<ul><li>Item 1<ul><li>Item 2<ul><li>Item 3<ul><li>Item 4</li></ul></li><li>Item 5</li></ul></li><li>Item 6</li></ul></li><li>Item 7</li></ul>")
        editText.setSelection(editText.editableText.indexOf("4"))
        editText.outdent()

        editText.setSelection(editText.editableText.indexOf("3"), editText.editableText.indexOf("5"))

        Assert.assertTrue(editText.isOutdentAvailable())
        editText.outdent()

        Assert.assertEquals("<ul><li>Item 1<ul><li>Item 2</li><li>Item 3</li><li>Item 4</li><li>Item 5</li><li>Item 6</li></ul></li><li>Item 7</li></ul>", editText.toHtml())
    }
}

