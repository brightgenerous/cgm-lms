package sc.ript.cgmlms.fw.guicemodule

import com.google.inject.Module

object GuiceModuleProvider {

  lazy val module: Module = new GuiceModule

}
