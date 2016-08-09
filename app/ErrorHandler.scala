import javax.inject.{Inject, Singleton}

import play.api.http.HttpErrorHandler
import play.api.mvc.{RequestHeader, Result}
import sc.ript.cgmlms.fw.handlers.ErrorHandlerProvider

import scala.concurrent.Future

@Singleton
class ErrorHandler @Inject() private(provider: ErrorHandlerProvider) extends HttpErrorHandler {

  private lazy val errorHandler = provider.errorHandler

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] =
    errorHandler.onClientError(request, statusCode, message)

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] =
    errorHandler.onServerError(request, exception)

}
