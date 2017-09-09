package org.prac.httpgame.utils

import java.text.SimpleDateFormat

import org.prac.httpgame.core.{InvalidItemName, Item}
import org.scalatest.{FunSpec, Matchers}

class ProductCatalogUtilsTest extends FunSpec with Matchers {

  describe("staging") {
    it("should return violation if sanityCHeck fails") {
      //given
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val items = Array(Item(sdf.parse("2017-12-02"), sdf.parse("2017-12-02"), 123, "Kitchen", ""),
        Item(sdf.parse("2017-12-02"), sdf.parse("2017-12-02"), -123, "Kitchen", ""))

      //when
      val result = ProductCatalogUtils.sanityCheck(items)

      //then
      result shouldEqual Left(InvalidItemName)
    }
    it("should return array of items if sanity check passes") {
      //given
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val items = Array(Item(sdf.parse("2017-12-02"), sdf.parse("2017-12-02"), 123, "Kitchen", "ads"),
        Item(sdf.parse("2017-12-02"), sdf.parse("2017-12-02"), 123, "Kitchen", "asf"))

      //when
      val result = ProductCatalogUtils.sanityCheck(items)

      //then
      result shouldEqual Right(items)
      result.right.get should have length 2
    }
  }

}
