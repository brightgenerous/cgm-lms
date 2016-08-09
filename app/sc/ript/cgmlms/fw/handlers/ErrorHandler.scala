package sc.ript.cgmlms.fw.handlers

import javax.inject.{Inject, Singleton}

import com.google.inject.Provider
import com.typesafe.scalalogging.LazyLogging
import play.api.http.DefaultHttpErrorHandler
import play.api.mvc.Results.{InternalServerError, Status}
import play.api.mvc.{RequestHeader, Result}
import play.api.routing.Router
import play.api.{Configuration, Environment, OptionalSourceMapper}

import scala.concurrent.Future

@Singleton
private class ErrorHandler @Inject()(env: Environment, config: Configuration, sourceMapper: OptionalSourceMapper, router: Provider[Router]) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) with LazyLogging {

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    logger.info("statusCode = {}, message = {}", statusCode.toString, message)
    Future.successful(Status(statusCode)("A client error occurred: " + message))
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    logger.error(s"message = ${exception.getMessage}", exception)
    Future.successful(InternalServerError("A server error occurred: " + exception.getMessage))
  }

}
