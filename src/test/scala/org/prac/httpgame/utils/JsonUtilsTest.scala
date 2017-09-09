package org.prac.httpgame.utils

import java.text.SimpleDateFormat

import org.prac.httpgame.core.{ProductCatalog, Item}
import org.scalatest.{FunSpec, Matchers}

class JsonUtilsTest extends FunSpec with Matchers{

  describe("jsonutils") {
    it("should unmarshall json to inputData") {
      //given
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val items = Array(Item(sdf.parse("2017-12-02"), sdf.parse("2016-12-02"), 123, "Kitchen", "ads"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2012-12-02"), 123, "Kitchen", "asf"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2017-12-02"), 123, "Electronics", "asf"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2012-12-02"), 123, "Electronics", "asf"))
      val inputJson = "{\"input\":[{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2016 12:00:00 AM\",\"price\":123,\"category\":\"Kitchen\",\"name\":\"ads\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2012 12:00:00 AM\",\"price\":123,\"category\":\"Kitchen\",\"name\":\"asf\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2017 12:00:00 AM\",\"price\":123,\"category\":\"Electronics\",\"name\":\"asf\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2012 12:00:00 AM\",\"price\":123,\"category\":\"Electronics\",\"name\":\"asf\"}]}"
      val expected = ProductCatalog(items)
      //when
      val inputData = JsonUtils.createObject[ProductCatalog](inputJson, classOf[ProductCatalog])
      //then
      inputData.input shouldEqual expected.input
    }

    it("should marshall inputData to json") {
      //given
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val items = Array(Item(sdf.parse("2017-12-02"), sdf.parse("2016-12-02"), 123, "Kitchen", "ads"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2012-12-02"), 123, "Kitchen", "asf"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2017-12-02"), 123, "Electronics", "asf"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2012-12-02"), 123, "Electronics", "asf"))
      val inputData = ProductCatalog(items)
      //when
      val inputDataJson = JsonUtils.objectToJson[ProductCatalog](inputData)
      //then
      inputDataJson shouldEqual "{\"input\":[{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2016 12:00:00 AM\",\"price\":123,\"category\":\"Kitchen\",\"name\":\"ads\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2012 12:00:00 AM\",\"price\":123,\"category\":\"Kitchen\",\"name\":\"asf\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2017 12:00:00 AM\",\"price\":123,\"category\":\"Electronics\",\"name\":\"asf\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2012 12:00:00 AM\",\"price\":123,\"category\":\"Electronics\",\"name\":\"asf\"}]}"
    }

    it("should convert Map to JSON String") {
      //given
      val expected = "{\"Kitchen\":2,\"Electronics\":1}"
      val map = Map[String, Int]("Kitchen" -> 2, "Electronics" -> 1)

      //when
      val jsonString = JsonUtils.mapToJsonString(map)

      //then
      jsonString shouldEqual expected
    }
  }

}
