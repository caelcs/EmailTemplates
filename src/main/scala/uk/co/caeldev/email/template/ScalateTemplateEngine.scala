package uk.co.caeldev.email.template
import org.fusesource.scalate._

class ScalateTemplateEngine extends RenderTemplateEngine[TemplateEngine] {

  val engine = new TemplateEngine

  def getEngine(): TemplateEngine = {
    engine
  }
}
