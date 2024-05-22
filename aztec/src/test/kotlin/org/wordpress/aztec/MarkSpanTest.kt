package org.wordpress.aztec

import org.junit.Assert
import org.junit.Test
import org.wordpress.aztec.spans.MarkSpan

class MarkSpanTest {
    /**
     * Test used to confirm two crashes related are fixed.
     *
     * https://github.com/wordpress-mobile/WordPress-Android/issues/20738
     */
    @Test
    fun `Calling MarkSpan#safelyParseColor with empty string should not cause a crash`() {
        var error = false
        try {
            MarkSpan(colorString = "")
        } catch (e: Exception) {
            error = true
        }
        Assert.assertFalse(error)
    }

    /**
     * Test used to confirm two crashes related are fixed.
     *
     * https://github.com/wordpress-mobile/WordPress-Android/issues/20694
     */
    @Test
    fun `Calling MarkSpan#safelyParseColor with null string should not cause a crash`() {
        var error = false
        try {
            MarkSpan(colorString = null)
        } catch (e: Exception) {
            error = true
        }
        Assert.assertFalse(error)
    }
}
