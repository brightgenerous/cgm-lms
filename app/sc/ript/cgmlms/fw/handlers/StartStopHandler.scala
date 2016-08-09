package sc.ript.cgmlms.fw.handlers

import java.time.Clock
import javax.inject.{Inject, Singleton}

import com.typesafe.scalalogging.LazyLogging
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

@Singleton
protected[fw] class StartStopHandler @Inject() private(clock: Clock, appLifecycle: ApplicationLifecycle) extends LazyLogging {

  // This code is called when the application starts.
  private val start = clock.instant
  logger.info("Starting application at {}.", start)

  // When the application starts, register a stop hook with the
  // ApplicationLifecycle object. The code inside the stop hook will
  // be run when the application stops.
  appLifecycle.addStopHook { () =>
    val stop = clock.instant
    val runningTime = stop.getEpochSecond - start.getEpochSecond
    logger.info("Stopping application at {} after {}s.", stop, runningTime.toString)
    Future.successful(())
  }

}
