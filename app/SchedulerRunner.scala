import java.io.{StringWriter, PrintWriter}
import java.nio.file.Paths

import com.github.mustachejava.{MustacheFactory, DefaultMustacheFactory}
import org.apache.commons.mail.{HtmlEmail, DefaultAuthenticator, SimpleEmail}

import scala.collection.immutable.HashMap

/**
 * Created by dev on 5/01/16.
 */
object SchedulerRunner {
  def main(a: Array[String]) : Unit = {
    println("Scheduler running...")
    val mf = new DefaultMustacheFactory()
    val file = Paths.get("conf/templates/template.mustache")
    val mustache = mf.compile(file.toAbsolutePath.toString)
    val writer = new StringWriter()
    case class Title(title: String)
    mustache.execute(writer, Title("title")).flush()
    val message = writer.toString
    println("Template: " + message)

    println("Connecting...")
    val email = new HtmlEmail()
    email.setHostName("smtp.gmail.com")
    email.setSmtpPort(465)
    email.setAuthenticator(new DefaultAuthenticator(sys.env("EMAIL_USER"), sys.env("EMAIL_PASSWORD")))
    email.setSSLOnConnect(true)
    email.setFrom(sys.env("EMAIL_USER"))
    email.setSubject("TestMail")
    email.setHtmlMsg(message)
    email.addTo(sys.env("EMAIL_USER"))
    email.send()
  }
}
