import javax.inject.{Inject, Singleton}

import play.api.http.HttpFilters
import sc.ript.cgmlms.fw.filters.FiltersProvider

/**
  * This class configures filters that run on every request. This
  * class is queried by Play to get a list of filters.
  *
  * Play will automatically use filters from any class called
  * `Filters` that is placed the root package. You can load filters
  * from a different class by adding a `play.http.filters` setting to
  * the `application.conf` configuration file.
  *
  * @param provider filters provider.
  */
@Singleton
class Filters @Inject() private(provider: FiltersProvider) extends HttpFilters {

  override lazy val filters = provider.filters

}
