package org.prac.httpgame.core

import java.util.{Calendar, Date}

case class ProductCatalog(input: Array[Item]) {
  def itemsCount: Int = input.length

  def activeItemsCount: Int = input.count(expiry)

  def activeItemsCountByCategory: Map[String, Int] = {
    val grouped: Map[String, Array[Item]] = input.filter(expiry).groupBy(_.category)
    val mapped: Map[String, Int] = grouped.map(x => (x._1, x._2.size))
    mapped.foldLeft(Map[String, Int]())(addToMap)
  }

  def totalPriceOfActiveItems: Map[String, Int] = {
    val totalValue: Int = input.filter(expiry).foldLeft(0)(addPrices)
    Map[String, Int]("totalValue" -> totalValue)
  }

  private def addToMap(map: Map[String, Int], propertyCount: (String, Int)): Map[String, Int] = {
    map ++ Map(propertyCount._1 -> propertyCount._2)
  }

  private def addPrices(a: Int, b: Item): Int = a + b.price.toInt

  private def expiry(item: Item): Boolean = {
    val today = getTodaysDate
    today.before(item.endDate) && today.after(item.startDate)
  }

  private def getTodaysDate: Date = {
    val today = Calendar.getInstance
    today.set(Calendar.HOUR_OF_DAY, 0)
    today.set(Calendar.MINUTE, 0)
    today.set(Calendar.SECOND, 0)
    today.getTime
  }
}
