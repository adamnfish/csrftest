import controllers.CSRFController
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "CSRFController" should {
    "succeed when skipping CSRF check" in new WithApplication {
      val request = FakeRequest(POST, "/").withHeaders("Csrf-Token" -> "nocheck")
      val result = CSRFController.post()(request)
    }

    "definitely succeed if we explicitly set CSRFAddToken as well" in new WithApplication {
      val request = FakeRequest(POST, "/2").withHeaders("Csrf-Token" -> "nocheck")
      val result = CSRFController.post2()(request)
    }
  }
}
