package com.dods.mpp.domain

import org.jdom2.output.{LineSeparator, XMLOutputter, Format}

import java.io.{ByteArrayInputStream, InputStream, StringWriter, StringReader}
import net.sf.json.xml.XMLSerializer
import org.apache.commons.io.IOUtils
import java.nio.charset.Charset
import org.jdom2.{Element, Document}
import org.jdom2.input.SAXBuilder
import scala.collection.JavaConverters._
import org.jdom2.filter.ElementFilter
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.fasterxml.jackson.databind.node.ObjectNode
import org.jdom2.Content.CType

object Transformers {

    val mapper = new ObjectMapper()

    val rich = new XMLOutputter(Format.getPrettyFormat)
    rich.getFormat.setOmitDeclaration(false)
    rich.getFormat.setOmitEncoding(false)
    rich.getFormat.setLineSeparator(LineSeparator.UNIX)

    val plain = new XMLOutputter(Format.getPrettyFormat)
    plain.getFormat.setOmitDeclaration(true)
    plain.getFormat.setOmitEncoding(true)
    plain.getFormat.setLineSeparator(LineSeparator.UNIX)

    val serializer = new XMLSerializer()
    serializer.setTypeHintsEnabled(false)
    serializer.setTypeHintsCompatibility(false)
    serializer.setRemoveNamespacePrefixFromElements(true)
    serializer
      .setExpandableProperties(Array("CoarseAnnotation", "GranularAnnotation", "urls", "indices", "hashtags", "Label"))
    serializer.setObjectName("root")

    def string2jdom(string: String): Document = new SAXBuilder().build(new StringReader(string))
    def bytes2jdom(b: Array[Byte]): Document = new SAXBuilder().build(new ByteArrayInputStream(b))
    def stream2jdom(in: InputStream): Document = new SAXBuilder().build(in)

    //    def jdom2ML(doc: Document) = DodsMLDocument.Factory.parse(jdom2string(doc))
    //   def jdom2IL(doc: Document) = DodsILDocument.Factory.parse(jdom2string(doc))
    def jdom2InputStream(doc: Document) = IOUtils.toInputStream(jdom2string(doc), Charset.forName("UTF-8").toString)
    def jdom2bytes(doc: Document): Array[Byte] = IOUtils.toByteArray(jdom2InputStream(doc))
    def jdom2node(doc: Document): ObjectNode = {
        val string = jdom2json(doc)
        mapper.readTree(string).asInstanceOf[ObjectNode]
    }
    // convert a jdom XML document into string output
    def jdom2string(doc: Document): String = jdom2string(doc, includeDeclaration = true)//TODO added this to get the build working, but shoud not be required
    def jdom2string(doc: Document, includeDeclaration: Boolean = true): String = {
        includeDeclaration match {
            case true => rich.outputString(doc)
            case false => plain.outputString(doc)
        }
    }
    def jdom2string(e: Element): String = rich.outputString(e)
    // convert a jdom XML document into string json
    def jdom2json(doc: Document): String = {
        val xmlString: String = jdom2string(doc)
        val jsonNode = serializer.read(xmlString)
        val sw = new StringWriter()
        jsonNode.write(sw)
        sw.toString
    }

    // convert jackson JSON node into a string json
    def node2string(json: JsonNode): String =
        mapper.writeValueAsString(json)

    // convert a string JSON into a JDOM XML document
    def json2jdom(string: String): Document = {
        val jsonNode = net.sf.json.JSONSerializer.toJSON(string)
        val xml = serializer.write(jsonNode)
        string2jdom(xml)
    }

    def removeEmptyElements(doc: Document) {
        val elements = doc.getDescendants(new ElementFilter).iterator().asScala.toArray
        elements.foreach(arg => {
            println(arg + " " + arg.getContent)
            // an empty dangling leaf node
            if (arg.getContentSize == 0 && arg.getChildren.size == 0)
                arg.detach()
            // a node with at most some whitespace
            if (arg.getContentSize == 1 && arg.getContent(0).getCType == CType.Text && arg.getTextTrim.isEmpty)
                arg.detach()
        })
    }
}