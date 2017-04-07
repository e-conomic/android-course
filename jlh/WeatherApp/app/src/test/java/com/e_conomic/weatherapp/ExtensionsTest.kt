package com.e_conomic.weatherapp

import com.e_conomic.weatherapp.extensions.Preference
import com.e_conomic.weatherapp.extensions.toDateString
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DateFormat

class ExtensionsTest {

    @Test fun testLongToDateString() {
        assertEquals("Oct 19, 2015", 1445275635L.toDateString())
    }

    @Test fun testDateStringFullFormat() {
        assertEquals("Monday, October 19, 2015", 1445275635L.toDateString(DateFormat.FULL))
    }

}