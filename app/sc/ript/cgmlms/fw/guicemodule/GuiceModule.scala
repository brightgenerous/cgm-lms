package sc.ript.cgmlms.fw.guicemodule

import java.time.Clock

import com.google.inject.AbstractModule
import sc.ript.cgmlms.fw.handlers.StartStopHandler

private class GuiceModule extends AbstractModule {

  override def configure() = {
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    // Ask Guice to create an instance of StartStopHandler when the application starts and stops.
    bind(classOf[StartStopHandler]).asEagerSingleton()
  }

}
