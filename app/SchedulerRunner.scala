import org.apache.commons.mail.{DefaultAuthenticator, SimpleEmail}

/**
 * Created by dev on 5/01/16.
 */
object SchedulerRunner {
  def main(a: Array[String]) : Unit = {
    println("Connecting...")
    val email = new SimpleEmail()
    email.setHostName("smtp.gmail.com")
    email.setSmtpPort(465)
    email.setAuthenticator(new DefaultAuthenticator(sys.env("EMAIL_USER"), sys.env("EMAIL_PASSWORD")))
    email.setSSLOnConnect(true)
    email.setFrom(sys.env("EMAIL_USER"))
    email.setSubject("TestMail")
    email.setMsg("This is a test mail ... :-)")
    email.addTo(sys.env("EMAIL_USER"))
    email.send()
  }
}
