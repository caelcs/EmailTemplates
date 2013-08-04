package com.dods.mpp.domain.document

import org.jdom2.{Element, Document}
import org.joda.time.DateTime
import scala.collection.JavaConverters._
import java.io.InputStream
import org.joda.time.format.{DateTimeFormatter, ISODateTimeFormat}
import java.util.Date
import com.dods.mpp.domain.Transformers

/** @author Stephen Samuel */
class DodsML(val doc: Document) extends JDomSugar with ISODatetimeParser {

    var snippets: Seq[String] = _
    val annotationRoot = doc.getRootElement
    val isoDateFormat: DateTimeFormatter = ISODateTimeFormat.dateTime()

    val item = doc.getRootElement.getChild("Item")

    def code: String = item.getChildTextTrim("Code")
    def code_=(s: String) = createOrSetChildText(item, "Code", s)

    def securityTerms: Array[String] = item.getChildren("Security").asScala.map(_.getTextTrim).toArray
    def securityTerms_=(terms: Array[String]) = {
        item.getChildren("Security").asScala.foreach(_.detach)
        terms.foreach(t => createChildText(item, "Security", t))
    }

    def creationDate: Long = toMillis(item.getChildTextTrim("CreatedDatetime"))

    def deletionDateTime: Long = toMillis(item.getChildTextTrim("DeletedDatetime"))

    def deletionDateTime_=(dt: Date) = createOrSetChildText(item, "DeletedDatetime",
        isoDateFormat.print(new DateTime(dt)))

    def creator: Option[String] = Option(item.getChildTextTrim("Creator"))
    def creator_=(s: String) = createOrSetChildText(item, "Creator", s)

    def organisation: Option[String] = Option(item.getChildTextTrim("Organisation"))

    def editor: Option[String] = Option(item.getChildTextTrim("Editor")).filter(_.trim.length > 0)
    def editor_=(s: String) = createOrSetChildText(item, "Editor", s)

//    def note: Option[String] = Option(item.getChildTextTrim("Note")).filter(_.trim.length > 0)
//    def note_=(s: String) = createOrSetChildText(item, "Note", s)

    def importance: Int = Option(item.getChildTextTrim("Importance")).filter(_.trim.length > 0).getOrElse("0").toInt
    def importance_=(i: Int) = createOrSetChildText(item, "Importance", i.toString)

    def schemaType: SchemaType = SchemaType(item.getChildText("SchemaType"))

    lazy val revisions: Seq[Revision] = item.getChildren("Revision").asScala.map(e => new Revision(this, e))
    def localisations: Seq[Localisation] = revisions.flatMap(_.localisations)
    def localisationsWithPayload = localisations.filter(_.payload.isDefined)
    def payloads = localisations.flatMap(_.payload)

    def payload = payloads.head
    def payload_=(bytes: Array[Byte]): Unit = localisations.head.payload = Option(bytes)
    // returns a default repository filename which is the latest revisions first locale
    def latestRevision = revisions.last
    def title = latestRevision.localisations.head.title
    def title_=(s: String) = latestRevision.localisations.head.title = s

    lazy val contentProvenance = {
        val cp = item.getChild("ContentProvenance")
        ContentProv(cp.getChildText("ContentSource"),
            Option(cp.getChildText("ContentLocation")),
            cp.getChildText("InformationType"))
    }

    def addRelatedItem(itemCode: String): Boolean = {

        val relatedItems = item.getChildren("RelatedItem").asScala

        relatedItems.find(_.getChildTextTrim("RelatedItem") == itemCode) match {

            case Some(ri) =>
                false

            case _ =>
                val ri = new Element("RelatedItem")
                createOrSetChildText(ri, "ItemRelationship", "Interested In")
                createOrSetChildText(ri, "RelatedItem", itemCode)
                item.addContent(ri)
                true
        }

    }

    override def toString: String = String.format("DodsML [Code=%s, title=%s]", code, title)
}

object DodsML {
  implicit def apply(ml: Document): DodsML = new DodsML(ml)
  def apply(in: InputStream): DodsML = apply(Transformers.stream2jdom(in))
  def apply(bytes: Array[Byte]): DodsML = apply(Transformers.bytes2jdom(bytes))
}

class Revision(val ml: DodsML, element: Element) extends JDomSugar with ISODatetimeParser{

    val annotationRoot = element

    def annotatedViewFilename = localisations.head.annotatedViewFilename

    lazy val localisations: Seq[Localisation] = element
      .getChildren("Localisation")
      .asScala
      .map(e => new Localisation(this, e))

    def code = element.getChildTextTrim("Code")
    def code_=(s: String) = createOrSetChildText(element, "Code", s)

    def contentDate: Long = toMillis(element.getChildTextTrim("ContentDatetime"))
    def ingestionDate: Long = toMillis(element.getChildTextTrim("IngestionDatetime"))
    def creationDate: Long = toMillis(element.getChildTextTrim("CreationDatetime"))

}

case class ContentProv(contentSource: String, contentLocation: Option[String], informationType: String)

class Localisation(revision: Revision, element: Element) extends JDomSugar with ISODatetimeParser {

    val annotationRoot = element

    var payload: Option[Array[Byte]] = None

    def code = element.getChildTextTrim("Code")
    def code_=(s: String) = createOrSetChildText(element, "Code", s)

    def creationDate: Long = toMillis(element.getChildTextTrim("CreationDatetime"))
    def ingestionDate: Long = toMillis(element.getChildTextTrim("IngestDatetime"))

    def country = element.getChildTextTrim("Country")
    def country_=(s: String) = createOrSetChildText(element, "Country", s)

    def language = element.getChildTextTrim("Language")
    def language_=(s: String) = createOrSetChildText(element, "Language", s)

    def mediaType = element.getChildTextTrim("MediaType")
    def mediaType_=(s: String) = createOrSetChildText(element, "MediaType", s)

    def title = element.getChildTextTrim("Title")
    def title_=(s: String) = createOrSetChildText(element, "Title", s)

    def referenceURI = element.getChildTextTrim("ReferenceURI")
    def referenceURI_=(s: String) = createOrSetChildText(element, "ReferenceURI", s)

    def annotatedViewFilename = code + "_annotated.html"

    def repositoryURI = element.getChildTextTrim("RepositoryURI")
    def repositoryURI_=(s: String) = createOrSetChildText(element, "RepositoryURI", s)

    def referenceFormat = element.getChildTextTrim("ReferenceFormat")
    def referenceFormat_=(s: String) = createOrSetChildText(element, "ReferenceFormat", s)

    def embargoed = element.getChildTextTrim("Embargoed") == "1"
    def embargoed_=(s: Boolean) = createOrSetChildText(element, "Embargoed", if (s == true) "1" else "0")
}
