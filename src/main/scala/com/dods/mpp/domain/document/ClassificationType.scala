package com.dods.mpp.domain.document

import org.jdom2.Document

/** @author Naga Kishore*/
sealed abstract class ClassificationType(val classificationType: String)

object Suggested extends ClassificationType("Suggested")
object Mention extends ClassificationType("Mention")


object ClassificationType {

    val Types = List(Suggested)

    def apply(classificationType: String): ClassificationType = {
        val found = Types.find(_.classificationType.equalsIgnoreCase(classificationType))
        if ( !found.isEmpty ) found.head
        else null
    }
}
