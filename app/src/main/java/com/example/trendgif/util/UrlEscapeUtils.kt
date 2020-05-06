package com.example.trendgif.util

internal object UrlEscapeUtils {
    /** The amount of padding (chars) to use when growing the escape buffer.  */
    private const val DEST_PAD = 32

    /** Url form parameter safe chars.  */
    private const val SAFE_CHARS = "-_.*"

    /** This escaper represents spaces as '+'.  */
    private val PLUS_SIGN = charArrayOf('+')

    /** Percent escapers output upper case hex digits (uri escapers require this).  */
    private val UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray()

    /**
     * An array of flags where for any `char c` if `SAFE_OCTETS[c]` is
     * true then `c` should remain unmodified in the output. If
     * `c > SAFE_OCTETS.length` then it should be escaped.
     */
    private val SAFE_OCTETS = createSafeOctets(
        SAFE_CHARS + "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
    )

    /**
     * Returns the escaped form of a given literal string.
     *
     * @param string the literal string to be escaped
     * @return the escaped form of `string`
     * @throws NullPointerException if `string` is null
     * @throws IllegalArgumentException if `string` contains badly formed UTF-16 or cannot be
     * escaped for any other reason
     */
    fun escape(string: String): String {
        checkNotNull(string, "string == null")
        val slen = string.length
        for (index in 0 until slen) {
            val c = string[index]
            if (c.toInt() >= SAFE_OCTETS.size || !SAFE_OCTETS[c.toInt()]) {
                return escapeSlow(string, index)
            }
        }
        return string
    }

    /**
     * Returns the escaped form of a given literal string, starting at the given index.
     *
     *
     * This method is not reentrant and may only be invoked by the top level
     * [.escape] method.
     *
     * @param s the literal string to be escaped
     * @param index the index to start escaping from
     * @return the escaped form of `string`
     * @throws NullPointerException if `string` is null
     * @throws IllegalArgumentException if invalid surrogate characters are
     * encountered
     */
    private fun escapeSlow(s: String, index: Int): String {
        var index = index
        val end = s.length

        // Get a destination buffer and setup some loop variables.
        var dest = CharArray(1024)
        var destIndex = 0
        var unescapedChunkStart = 0
        while (index < end) {
            val cp = codePointAt(s, index, end)
            require(cp >= 0) { "Trailing high surrogate at end of input" }
            // It is possible for this to return null because nextEscapeIndex() may
            // (for performance reasons) yield some false positives but it must never
            // give false negatives.
            val escaped = escape(cp)
            val nextIndex =
                index + if (Character.isSupplementaryCodePoint(cp)) 2 else 1
            if (escaped != null) {
                val charsSkipped = index - unescapedChunkStart

                // This is the size needed to add the replacement, not the full
                // size needed by the string.  We only regrow when we absolutely must.
                val sizeNeeded = destIndex + charsSkipped + escaped.size
                if (dest.size < sizeNeeded) {
                    val destLength = sizeNeeded + (end - index) + DEST_PAD
                    dest = growBuffer(dest, destIndex, destLength)
                }
                // If we have skipped any characters, we need to copy them now.
                if (charsSkipped > 0) {
                    s.toCharArray(dest, destIndex, unescapedChunkStart, index)
                    destIndex += charsSkipped
                }
                if (escaped.size > 0) {
                    System.arraycopy(escaped, 0, dest, destIndex, escaped.size)
                    destIndex += escaped.size
                }
                // If we dealt with an escaped character, reset the unescaped range.
                unescapedChunkStart = nextIndex
            }
            index = nextEscapeIndex(s, nextIndex, end)
        }

        // Process trailing unescaped characters - no need to account for escaped
        // length or padding the allocation.
        val charsSkipped = end - unescapedChunkStart
        if (charsSkipped > 0) {
            val endIndex = destIndex + charsSkipped
            if (dest.size < endIndex) {
                dest = growBuffer(dest, destIndex, endIndex)
            }
            s.toCharArray(dest, destIndex, unescapedChunkStart, end)
            destIndex = endIndex
        }
        return String(dest, 0, destIndex)
    }

    /** Escapes the given Unicode code point in UTF-8.  */
    fun escape(cp: Int): CharArray? {
        // We should never get negative values here but if we do it will throw an
        // IndexOutOfBoundsException, so at least it will get spotted.
        var cp = cp
        return if (cp < SAFE_OCTETS.size && SAFE_OCTETS[cp]) {
            null
        } else if (cp == ' '.toInt()) {
            PLUS_SIGN
        } else if (cp <= 0x7F) {
            // Single byte UTF-8 characters
            // Start with "%--" and fill in the blanks
            val dest = CharArray(3)
            dest[0] = '%'
            dest[2] = UPPER_HEX_DIGITS[cp and 0xF]
            dest[1] = UPPER_HEX_DIGITS[cp ushr 4]
            dest
        } else if (cp <= 0x7ff) {
            // Two byte UTF-8 characters [cp >= 0x80 && cp <= 0x7ff]
            // Start with "%--%--" and fill in the blanks
            val dest = CharArray(6)
            dest[0] = '%'
            dest[3] = '%'
            dest[5] = UPPER_HEX_DIGITS[cp and 0xF]
            cp = cp ushr 4
            dest[4] = UPPER_HEX_DIGITS[0x8 or (cp and 0x3)]
            cp = cp ushr 2
            dest[2] = UPPER_HEX_DIGITS[cp and 0xF]
            cp = cp ushr 4
            dest[1] = UPPER_HEX_DIGITS[0xC or cp]
            dest
        } else if (cp <= 0xffff) {
            // Three byte UTF-8 characters [cp >= 0x800 && cp <= 0xffff]
            // Start with "%E-%--%--" and fill in the blanks
            val dest = CharArray(9)
            dest[0] = '%'
            dest[1] = 'E'
            dest[3] = '%'
            dest[6] = '%'
            dest[8] = UPPER_HEX_DIGITS[cp and 0xF]
            cp = cp ushr 4
            dest[7] = UPPER_HEX_DIGITS[0x8 or (cp and 0x3)]
            cp = cp ushr 2
            dest[5] = UPPER_HEX_DIGITS[cp and 0xF]
            cp = cp ushr 4
            dest[4] = UPPER_HEX_DIGITS[0x8 or (cp and 0x3)]
            cp = cp ushr 2
            dest[2] = UPPER_HEX_DIGITS[cp]
            dest
        } else if (cp <= 0x10ffff) {
            val dest = CharArray(12)
            // Four byte UTF-8 characters [cp >= 0xffff && cp <= 0x10ffff]
            // Start with "%F-%--%--%--" and fill in the blanks
            dest[0] = '%'
            dest[1] = 'F'
            dest[3] = '%'
            dest[6] = '%'
            dest[9] = '%'
            dest[11] = UPPER_HEX_DIGITS[cp and 0xF]
            cp = cp ushr 4
            dest[10] = UPPER_HEX_DIGITS[0x8 or (cp and 0x3)]
            cp = cp ushr 2
            dest[8] = UPPER_HEX_DIGITS[cp and 0xF]
            cp = cp ushr 4
            dest[7] = UPPER_HEX_DIGITS[0x8 or (cp and 0x3)]
            cp = cp ushr 2
            dest[5] = UPPER_HEX_DIGITS[cp and 0xF]
            cp = cp ushr 4
            dest[4] = UPPER_HEX_DIGITS[0x8 or (cp and 0x3)]
            cp = cp ushr 2
            dest[2] = UPPER_HEX_DIGITS[cp and 0x7]
            dest
        } else {
            // If this ever happens it is due to bug in UnicodeEscaper, not bad input.
            throw IllegalArgumentException("Invalid unicode character value $cp")
        }
    }

    /**
     * Creates a boolean array with entries corresponding to the character values
     * specified in safeChars set to true. The array is as small as is required to
     * hold the given character information.
     */
    private fun createSafeOctets(safeChars: String): BooleanArray {
        var maxChar = -1
        val safeCharArray = safeChars.toCharArray()
        for (c in safeCharArray) {
            maxChar = Math.max(c.toInt(), maxChar)
        }
        val octets = BooleanArray(maxChar + 1)
        for (c in safeCharArray) {
            octets[c.toInt()] = true
        }
        return octets
    }

    /**
     * Scans a sub-sequence of characters from a given [CharSequence],
     * returning the index of the next character that requires escaping.
     *
     * @param csq a sequence of characters
     * @param start the index of the first character to be scanned
     * @param end the index immediately after the last character to be scanned
     * @throws IllegalArgumentException if the scanned sub-sequence of `csq`
     * contains invalid surrogate pairs
     */
    private fun nextEscapeIndex(csq: CharSequence, start: Int, end: Int): Int {
        var start = start
        checkNotNull(csq, "csq == null")
        while (start < end) {
            val c = csq[start]
            if (c.toInt() >= SAFE_OCTETS.size || !SAFE_OCTETS[c.toInt()]) {
                break
            }
            start++
        }
        return start
    }

    /**
     * Returns the Unicode code point of the character at the given index.
     *
     *
     * Unlike [Character.codePointAt] or
     * [String.codePointAt] this method will never fail silently when
     * encountering an invalid surrogate pair.
     *
     *
     * The behaviour of this method is as follows:
     *
     *  1. If `index >= end`, [IndexOutOfBoundsException] is thrown.
     *  1. **If the character at the specified index is not a surrogate, it is
     * returned.**
     *  1. If the first character was a high surrogate value, then an attempt is
     * made to read the next character.
     *
     *  1. **If the end of the sequence was reached, the negated value of
     * the trailing high surrogate is returned.**
     *  1. **If the next character was a valid low surrogate, the code point
     * value of the high/low surrogate pair is returned.**
     *  1. If the next character was not a low surrogate value, then
     * [IllegalArgumentException] is thrown.
     *
     *  1. If the first character was a low surrogate value,
     * [IllegalArgumentException] is thrown.
     *
     *
     * @param seq the sequence of characters from which to decode the code point
     * @param index the index of the first character to decode
     * @param end the index beyond the last valid character to decode
     * @return the Unicode code point for the given index or the negated value of
     * the trailing high surrogate character at the end of the sequence
     */
    private fun codePointAt(seq: CharSequence, index: Int, end: Int): Int {
        var index = index
        checkNotNull(seq, "seq == null")
        if (index < end) {
            val c1 = seq[index++]
            return if (c1 < Character.MIN_HIGH_SURROGATE || c1 > Character.MAX_LOW_SURROGATE) {
                // Fast path (first test is probably all we need to do)
                c1.toInt()
            } else if (c1 <= Character.MAX_HIGH_SURROGATE) {
                // If the high surrogate was the last character, return its inverse
                if (index == end) {
                    return -(c1.toInt())
                }
                // Otherwise look for the low surrogate following it
                val c2 = seq[index]
                if (Character.isLowSurrogate(c2)) {
                    return Character.toCodePoint(c1, c2)
                }
                throw IllegalArgumentException(
                    "Expected low surrogate but got char '"
                            + c2 + "' with value " + c2.toInt() + " at index " + index
                            + " in '" + seq + "'"
                )
            } else {
                throw IllegalArgumentException(
                    "Unexpected low surrogate character '" + c1
                            + "' with value " + c1.toInt() + " at index " + (index - 1)
                            + " in '" + seq + "'"
                )
            }
        }
        throw IndexOutOfBoundsException("Index exceeds specified range")
    }

    /**
     * Helper method to grow the character buffer as needed, this only happens
     * once in a while so it's ok if it's in a method call.  If the index passed
     * in is 0 then no copying will be done.
     */
    private fun growBuffer(dest: CharArray, index: Int, size: Int): CharArray {
        val copy = CharArray(size)
        if (index > 0) {
            System.arraycopy(dest, 0, copy, 0, index)
        }
        return copy
    }

    fun <T> checkNotNull(reference: T?, errorMessage: String?): T {
        if (reference == null) {
            throw NullPointerException(errorMessage)
        }
        return reference
    }
}