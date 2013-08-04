package uk.co.caeldev.email.template

import freemarker.template.Configuration
import java.io.File
import ru.circumflex.freemarker.ScalaObjectWrapper

class FreemarkerTemplateEngine(templateFolder:String) extends RenderTemplateEngine[Configuration] {

  val configuration = FmConfiguration

  def getEngine(): Configuration = {
    configuration.setDirectoryForTemplateLoading(new File(templateFolder))
    configuration
  }
}

object FmConfiguration extends Configuration {
  //setObjectWrapper(new ScalaObjectWrapper())
}