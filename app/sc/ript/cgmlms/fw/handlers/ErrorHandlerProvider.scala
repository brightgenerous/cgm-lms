package sc.ript.cgmlms.fw.handlers

import javax.inject.{Inject, Singleton}

import play.api.http.HttpErrorHandler

@Singleton
class ErrorHandlerProvider @Inject() private(handler: ErrorHandler) {

  val errorHandler: HttpErrorHandler = handler

}
