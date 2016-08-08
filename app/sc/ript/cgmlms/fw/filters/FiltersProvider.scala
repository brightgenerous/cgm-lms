package sc.ript.cgmlms.fw.filters

import javax.inject.{Inject, Singleton}

import play.api.mvc.EssentialFilter
import play.api.{Environment, Mode}

@Singleton
class FiltersProvider @Inject() private(env: Environment, logFilter: LogFilter) {

  lazy val filters: Seq[EssentialFilter] = {
    // Use the example filter if we're running development mode. If
    // we're running in production or test mode then don't use any
    // filters at all.
    if (env.mode == Mode.Dev) Seq(logFilter) else Seq.empty
  }

}
