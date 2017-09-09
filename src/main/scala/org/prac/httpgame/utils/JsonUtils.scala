package org.prac.httpgame.utils

import com.google.gson.{Gson, JsonObject}

object JsonUtils {

  def objectToJson[T](data: T) = new Gson().toJson(data)

  def createObject[T](inputJson: String, clazz: Class[_]): T = {
    new Gson().fromJson[T](inputJson, clazz)
  }

  def mapToJsonString(map: Map[String, Int]): String = {
    val jsonObject = new JsonObject()
    map.foreach(x => jsonObject.addProperty(x._1, x._2))
    jsonObject.toString
  }

}
