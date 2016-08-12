import org.scalatest.{FlatSpec, Matchers}
import slick.driver.H2Driver.api._

class FlywaySpec extends FlatSpec with Matchers {

  "Flyway" should "run the migration correctly" in {
    val ds = Database.forConfig("slick.dbs.default.db").source
    val cn = ds.createConnection()
    val rs = cn.prepareStatement("select * from person").executeQuery()
    rs.next() should be(true)
    rs.getInt(1) should be(1)
    rs.getString(2) should be("John")
    rs.next() should be(true)
    rs.getInt(1) should be(2)
    rs.getString(2) should be("Paul")
    rs.next() should be(true)
    rs.getInt(1) should be(3)
    rs.getString(2) should be("George")
    rs.next() should be(true)
    rs.getInt(1) should be(4)
    rs.getString(2) should be("Ringo")
    rs.next() should be(false)
    rs.close()
    cn.close()
  }

}
