package org.prac.httpgame.core

import java.util.Date

sealed trait ItemValidations
case object InvalidItemPrice extends ItemValidations
case object InvalidItemName extends ItemValidations
case object InvalidItemCategory extends ItemValidations
case object MissingItemParams extends ItemValidations

case class Item(endDate: Date, startDate: Date, price: Long, category: String, name: String )

object Item {

  def validate(item: Item): Either[ItemValidations, Item] = {
    if(missingParameters(item)) Left(MissingItemParams)
    else if(nonNegativeValue(item.price)) Left(InvalidItemPrice)
    else if(blankValue(item.name)) Left(InvalidItemName)
    else if(blankValue(item.category)) Left(InvalidItemCategory)
    else Right(item)
  }

  private def missingParameters(item: Item): Boolean = item.startDate == null ||
          item.name == null  || item.category == null
  private def nonNegativeValue(price: Long): Boolean = price < 0
  private def blankValue(value: String): Boolean = value.equals("")
}
