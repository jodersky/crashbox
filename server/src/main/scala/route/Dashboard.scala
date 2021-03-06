package crashbox.ci
package route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives
import crashbox.ci.model.ApiProtocol
import crashbox.ci.model.spec.Image

class Dashboard(title: String)
    extends Directives
    with TwirlSupport
    with ApiProtocol
    with SprayJsonSupport {

  val route = pathEndOrSingleSlash(
    complete(view.html.main(title))
  ) ~ path("api") {
    extractRequest { req =>
      println(req.headers)
      complete(Image.Docker("test"): Image)
    }
  } ~ path("redir") {
    redirect("/api", StatusCodes.Found)
  } ~ pathPrefix("assets") {
    getFromResourceDirectory("assets")
  }

}
