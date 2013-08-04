package com.dods.mpp.domain.document

/** @author Stephen Samuel */
sealed abstract class SchemaType(val text: String)

object Debate extends SchemaType("Debate")
object Motion extends SchemaType("Motion")
object ParliamentaryXML extends SchemaType("Parliamentary_XML")
object QADocument extends SchemaType("QADocument")
object Tweet extends SchemaType("Tweet")
object External extends SchemaType("External")

object SchemaType {

    val Types = List(Debate, Motion, ParliamentaryXML, QADocument, Tweet, External)

    def apply(schemaType: String): SchemaType = {
        require(schemaType != null)
        Types.find(_.text.equalsIgnoreCase(schemaType)).head
    }

}