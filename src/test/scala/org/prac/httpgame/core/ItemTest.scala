package org.prac.httpgame.core

import java.text.SimpleDateFormat

import org.scalatest.{FunSpec, Matchers}

class ItemTest extends FunSpec with Matchers {

  describe("An Item") {
    it("should return validity as false if Item name is blank") {
      //given
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val anItem = Item(sdf.parse("2017-12-02"), sdf.parse("2017-12-02"), 123, "Kitchen", "")
      //when
      val isValid = Item.validate(anItem)
      //then
      isValid.left.get shouldBe InvalidItemName
    }
    it("should return validity as false if Item price is -ve") {
      //given
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val anItem = Item(sdf.parse("2017-12-02"), sdf.parse("2017-12-02"), -123, "Kitchen", "abc")
      //when
      val isValid = Item.validate(anItem)
      //then
      isValid.left.get shouldBe InvalidItemPrice
    }
    it("should return validity as false if Item category is blank") {
      //given
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val anItem = Item(sdf.parse("2017-12-02"), sdf.parse("2017-12-02"), 123, "", "abc")
      //when
      val isValid = Item.validate(anItem)
      //then
      isValid.left.get shouldBe InvalidItemCategory
    }
  }

}