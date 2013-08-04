package com.dods.mpp.domain.document

import org.jdom2.{Document, Element}
import org.jdom2.filter.ElementFilter
import scala.collection.JavaConverters._

/** @author Stephen Samuel */
trait JDomSugar {

    def createOrSetChildText(e: Element, name: String, value: String) = {
        Option(e.getChild(name)) match {
            case None =>
                val child = new Element(name).setText(value)
                e.addContent(child)
            case Some(child) => child.setText(value)
        }
    }

    def createChildText(e: Element, name: String, value: String) = {
        val child = new Element(name).setText(value)
        e.addContent(child)
    }

    def getDescendants(doc: Document, elementName: String): Seq[Element] = {
        doc.getDescendants(new ElementFilter(elementName)).iterator.asScala.toSeq
    }
}
