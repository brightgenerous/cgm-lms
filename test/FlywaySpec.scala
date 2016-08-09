import org.scalatestplus.play.{OneAppPerTest, PlaySpec}

class FlywaySpec extends PlaySpec with OneAppPerTest {

  "Flyway" should {

    "run the migration correctly" in {
      val ds = play.api.db.DB.getDataSource()(play.api.Play.current)
      val cn = ds.getConnection
      val rs = cn.prepareStatement("select * from person").executeQuery
      rs.next mustBe true
      rs.getInt(1) mustBe 1
      rs.getString(2) mustBe "John"
      rs.next mustBe true
      rs.getInt(1) mustBe 2
      rs.getString(2) mustBe "Paul"
      rs.next mustBe true
      rs.getInt(1) mustBe 3
      rs.getString(2) mustBe "George"
      rs.next mustBe true
      rs.getInt(1) mustBe 4
      rs.getString(2) mustBe "Ringo"
      rs.next mustBe false
      rs.close()
      cn.close()
    }

  }

}
