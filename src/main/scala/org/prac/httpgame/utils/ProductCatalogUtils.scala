package org.prac.httpgame.utils

import java.text.SimpleDateFormat

import org.prac.httpgame.core.{InvalidItemName, Item, ItemValidations}

case object ProductCatalogUtils {

  def sanityCheck(input: Array[Item]): Either[ItemValidations, Array[Item]] = {
    def iterate(list: List[Item], violationStatus: Boolean, itemViolations: ItemValidations): Either[ItemValidations, Array[Item]] =
      (list, violationStatus) match {
        case (_, true) => Left(itemViolations)
        case (List(), false) => Right(input)
        case (x :: xs, _) => Item.validate(x) match {
          case Left(x) => iterate(xs, true, x)
          case _ => iterate(xs, false, itemViolations)
        }
      }

    iterate(input.toList, false, InvalidItemName)
  }

  def sanityCheckAndCorrectDate(input: Array[Item]): Either[ItemValidations, Array[Item]] = {
    def iterate(list: List[Item], violationStatus: Boolean, itemViolations: ItemValidations, acc: List[Item]): Either[ItemValidations, Array[Item]] =
      (list, violationStatus) match {
        case (_, true) => Left(itemViolations)
        case (List(), false) => Right(acc.toArray)
        case (x :: xs, _) => Item.validate(x) match {
          case Left(x) => iterate(xs, true, x, acc)
          case _ => iterate(xs, false, itemViolations, acc ++ List(setExpiryDateInCaseOfNull(x)))
        }
      }

    iterate(input.toList, false, InvalidItemName, List())
  }

  private def setExpiryDateInCaseOfNull(x: Item): Item = {
    if (x.endDate == null) Item(new SimpleDateFormat("yyyy-MM-dd").parse("2100-01-01"), x.startDate, x.price, x.category, x.name)
    else x
  }

}
