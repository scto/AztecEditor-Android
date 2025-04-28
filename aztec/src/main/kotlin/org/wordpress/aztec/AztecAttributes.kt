package org.wordpress.aztec

import org.wordpress.android.util.AppLog
import org.xml.sax.Attributes
import org.xml.sax.helpers.AttributesImpl

class AztecAttributes(attributes: Attributes = AttributesImpl()) : AttributesImpl(attributes) {
    @Synchronized
    fun setValue(key: String, value: String) {
        val index = getIndex(key)

        if (index == -1) {
            try {
                addAttribute("", key, key, "string", value)
            } catch (e: ArrayIndexOutOfBoundsException) {
                // https://github.com/wordpress-mobile/AztecEditor-Android/issues/705
                AppLog.e(
                    AppLog.T.EDITOR,
                    "Error adding attribute with name: $key and value: $value"
                )
                logInternalState()
                throw e
            }
        } else {
            setValue(index, value)
        }
    }

    @Synchronized
    private fun logInternalState() {
        AppLog.e(AppLog.T.EDITOR, "Dumping internal state:")
        AppLog.e(AppLog.T.EDITOR, "length = $length")
        // Since the toString can throw OOB error we need to wrap it in a try/catch
        try {
            AppLog.e(AppLog.T.EDITOR, toString())
        } catch (e: ArrayIndexOutOfBoundsException) {
            // No need to log anything here. `toString` already writes to log details, but we need to shallow the exception
            // we don't want to crash logging state of the app
        }
    }

    @Synchronized
    fun isEmpty(): Boolean {
        return length == 0
    }

    @Synchronized
    fun removeAttribute(key: String) {
        if (hasAttribute(key)) {
            val index = getIndex(key)
            try {
                removeAttribute(index)
            } catch (e: ArrayIndexOutOfBoundsException) {
                // https://github.com/wordpress-mobile/AztecEditor-Android/issues/705
                AppLog.e(AppLog.T.EDITOR, "Tried to remove attribute: $key that is not in the list")
                AppLog.e(AppLog.T.EDITOR, "Reported to be at index: $index")
                logInternalState()
                throw e
            }
        }
    }

    @Synchronized
    fun hasAttribute(key: String): Boolean {
        return getValue(key) != null
    }

    @Synchronized
    override fun toString(): String {
        return toMap().map { (key, value) ->
            "$key=\"$value\""
        }.joinToString(" ")
    }

    override fun hashCode(): Int {
        return toMap().hashCode()
    }

    private fun toMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        try {
            for (i in 0..<this.length) {
                map[this.getLocalName(i)] = this.getValue(i)
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            // https://github.com/wordpress-mobile/AztecEditor-Android/issues/705
            AppLog.e(AppLog.T.EDITOR, "IOOB occurred in toMap. Dumping partial state:")
            AppLog.e(AppLog.T.EDITOR, map.toString())
            throw e
        }
        return map
    }

    override fun equals(other: Any?): Boolean {
        if (other is AztecAttributes) {
            return this.toMap() == other.toMap()
        } else {
            return super.equals(other)
        }
    }
}
