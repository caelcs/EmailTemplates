package com.dods.mpp.domain.document

import org.joda.time.format.ISODateTimeFormat
import com.dods.mpp.domain.Logging

trait ISODatetimeParser extends Logging {

    private val parser = ISODateTimeFormat.dateTimeParser

    def toMillis(dateTime: String): Long = {
        var millis:Long = -1
        try {
            millis = parser.parseMillis(dateTime)
        } catch {
            case e:Exception => logger.error(s"Invalid dateTime String: ${dateTime}")
        }
        millis
    }

}
