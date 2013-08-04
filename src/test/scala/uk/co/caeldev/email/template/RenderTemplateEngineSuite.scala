package uk.co.caeldev.email.template

import org.scalatest.{BeforeAndAfter, FunSuite}
import freemarker.template.Configuration
import java.io.StringWriter
import scala.io.Source
import scala.xml.parsing.XhtmlParser
import scala.xml.{NodeSeq, Node}
import org.scalatest.mock.MockitoSugar
import com.dods.mpp.domain.document.DodsML
import com.dods.mpp.domain.Transformers
import java.util
import org.fusesource.scalate.TemplateEngine


class FreemarkerTemplateEngineSuite extends FunSuite with BeforeAndAfter with MockitoSugar {

  var velocityTemplateEngine:RenderTemplateEngine[Configuration] = _
  var scalateTemplateEngine:RenderTemplateEngine[TemplateEngine] = _

  val templateFolder = getClass.getResource("/").getPath
  val ml1 = Transformers.stream2jdom(getClass.getResourceAsStream("/dodsML/edm1.ml"))
  val dods1 = DodsML(ml1)
  val docs: Seq[DodsML] = Seq(dods1)

  before {
    velocityTemplateEngine = new FreemarkerTemplateEngine(templateFolder)
    scalateTemplateEngine = new ScalateTemplateEngine()
  }

  test("Should get a Freemarker engine and generate html content without shared variables") {
    val engine = velocityTemplateEngine.getEngine()
    val template = engine.getTemplate("/template.ftl")


    val out = new StringWriter()
    template.process(Map.empty, out)

    val body = XhtmlParser(Source.fromString(out.toString))
    val nodes: Seq[Node] = body.theSeq
    val ns: NodeSeq = NodeSeq fromSeq nodes

    val allIds = (ns \\ "@id").text

    assert (allIds === "headerbodyfooter")
  }

  /*test("Should get a Freemarker engine and generate html content using shared variables") {
    val engine = velocityTemplateEngine.getEngine()
    val template = engine.getTemplate("/template2.ftl")


    val out = new StringWriter()
    val data = new util.HashMap[String, util.ArrayList[DodsML]]()

    val listDods = new util.ArrayList[DodsML]()
    listDods.add(dods1)
    data.put("dodDocs", listDods)
    template.process(data, out)

    println(out)
    /*val body = XhtmlParser(Source.fromString(out.toString))
    val nodes: Seq[Node] = body.theSeq
    val ns: NodeSeq = NodeSeq fromSeq nodes

    val allIds = (ns \\ "@id").text

    assert (allIds === "headerbodyfooter")*/
  }*/

  test("Should get a Freemarker engine and generate html content using shared variables") {
    val engine = scalateTemplateEngine.getEngine()
    val template = engine.layout(getClass.getResource("/template3.ssp").getPath, Map("dodDocs" -> docs))

    println(template)

    /*val body = XhtmlParser(Source.fromString(out.toString))
    val nodes: Seq[Node] = body.theSeq
    val ns: NodeSeq = NodeSeq fromSeq nodes

    val allIds = (ns \\ "@id").text

    assert (allIds === "headerbodyfooter")*/
  }



}

case class Test1(name: String, lastName:String)
