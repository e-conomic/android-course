package com.e_conomic.weatherapp

import com.e_conomic.weatherapp.extensions.toDateString
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DateFormat
import java.util.*

class ExtensionsTest {

    @Test fun testLongToDateString() {
        assertEquals("Oct 19, 2015", 1445275635L.toDateString(locale = Locale("en", "US")))
    }

    @Test fun testDateStringFullFormat() {
        assertEquals("Monday, October 19, 2015", 1445275635L.toDateString(DateFormat.FULL, locale = Locale("en", "US")))
    }

}