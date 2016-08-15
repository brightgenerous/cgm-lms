import org.scalatest.{FlatSpec, Matchers}
import sc.ript.cgmlms.infra.database.Tables._
import slick.driver.H2Driver.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class SlickCodegenSpec extends FlatSpec with Matchers {

  "SlickCodegen" should "use slick with flyway migration" in {
    val db = Database.forConfig("slick.dbs.default.db")
    val resultFuture = db.run(Person.filter(_.id < 3).sortBy(_.id).result).map(_.map(row => (row.id, row.name)))
    val result = Await.result(resultFuture, Duration.Inf)

    result should be(Seq((1, "John"), (2, "Paul")))
  }

}
