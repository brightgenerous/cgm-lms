package sc.ript.cgmlms.fw.filters

import java.time.Clock
import javax.inject.{Inject, Singleton}

import akka.stream.Materializer
import com.typesafe.scalalogging.LazyLogging
import play.api.mvc.{Filter, RequestHeader, Result}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
private class LogFilter @Inject()(implicit override val mat: Materializer, clock: Clock, exec: ExecutionContext) extends Filter with LazyLogging {

  override def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    val startTime = clock.millis
    nextFilter(requestHeader).map { result =>
      val endTime = clock.millis
      val requestTime = endTime - startTime
      logger.info("{} {} took {}ms and returned {}", requestHeader.method, requestHeader.uri, requestTime.toString, result.header.status.toString)
      result
    }
  }

}
