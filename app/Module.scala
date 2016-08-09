import com.google.inject.{Binder, Module => GModule}
import sc.ript.cgmlms.fw.guicemodule.GuiceModuleProvider

/**
  * This class is a Guice module that tells Guice how to bind several
  * different types. This Guice module is created when the Play
  * application starts.
  *
  * Play will automatically use any class called `Module` that is in
  * the root package. You can create modules in other locations by
  * adding `play.modules.enabled` settings to the `application.conf`
  * configuration file.
  */
class Module extends GModule {

  private lazy val module = GuiceModuleProvider.module

  override def configure(binder: Binder) = module.configure(binder)

}
