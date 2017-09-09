package org.prac.httpgame.core

import java.text.SimpleDateFormat

import org.scalatest.{FunSpec, Matchers}

class ProductCatalogTest extends FunSpec with Matchers {

  describe("An Input") {
    it("should give correct count of items") {
      //given
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val items = Array(Item(sdf.parse("2017-12-02"), sdf.parse("2017-12-02"), 123, "Kitchen", "ads"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2017-12-02"), 123, "Kitchen", "asf"))
      val inventory = ProductCatalog(items)
      //when
      //then
      inventory.itemsCount shouldEqual 2
    }
    it("should give count of valid items today ") {
      //given
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val items = Array(Item(sdf.parse("2017-12-02"), sdf.parse("2016-12-02"), 123, "Kitchen", "ads"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2012-12-02"), 123, "Kitchen", "asf"))
      val inventory = ProductCatalog(items)
      //when
      //then
      inventory.activeItemsCount shouldEqual 2
    }
    it("should give count of valid items today grouped by category") {
      //given
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val items = Array(Item(sdf.parse("2017-12-02"), sdf.parse("2016-12-02"), 123, "Kitchen", "ads"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2012-12-02"), 123, "Kitchen", "asf"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2017-12-02"), 123, "Electronics", "asf"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2012-12-02"), 123, "Electronics", "asf"))
      val inventory = ProductCatalog(items)
      //when
      //then
      inventory.activeItemsCountByCategory.toString shouldEqual "Map(Kitchen -> 2, Electronics -> 1)"
    }
    it("should give total price of valid items today") {
      //given
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val items = Array(Item(sdf.parse("2017-12-02"), sdf.parse("2016-12-02"), 123, "Kitchen", "ads"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2012-12-02"), 123, "Kitchen", "asf"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2017-12-02"), 123, "Electronics", "asf"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2012-12-02"), 123, "Electronics", "asf"))
      val inventory = ProductCatalog(items)
      //when
      //then
      inventory.totalPriceOfActiveItems.toString shouldEqual "Map(totalValue -> 369)"
    }
  }

}
