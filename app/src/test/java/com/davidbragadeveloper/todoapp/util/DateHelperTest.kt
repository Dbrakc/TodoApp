package com.davidbragadeveloper.todoapp.util

import org.joda.time.DateTime
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class DateHelperTest{

    @Test
    fun `GIVEN timeAgo function WHEN insert a date THEN returns the correct time ago`(){

        val date = DateTime.now().minusHours(1)
        val result = DateHelper.calculateTimaAgo(date.toDate())

        val date2 = DateTime.now()
        val result2 = DateHelper.calculateTimaAgo(date2.toDate())

        assertEquals("hace 1 hora",result)
        assertEquals("hace un instante",result2)
    }
}