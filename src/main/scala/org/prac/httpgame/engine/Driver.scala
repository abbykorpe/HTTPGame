package org.prac.httpgame.engine

import com.squareup.okhttp.{Headers, HttpUrl, MediaType, RequestBody}
import org.prac.httpgame.core.{Item, ProductCatalog}
import org.prac.httpgame.utils.{HttpClient, JsonUtils, ProductCatalogUtils}

import scala.collection.JavaConverters._

object Driver {

  def challengeOne(userId: String, applicationType: String, getUrl: HttpUrl, postUrl: HttpUrl): Either[String, String] = {
    val APP_TYPE = MediaType.parse(applicationType)
    val headers = Headers.of(Map("userId" -> userId).asJava)

    HttpClient.performGetCall(getUrl, headers) match {
      case Left(x) => Left(x)
      case Right(x) => {
        val items = JsonUtils.createObject[Array[Item]](x, classOf[Array[Item]])
        ProductCatalogUtils.sanityCheck(items) match {
          case Left(x) => Left(x.toString)
          case Right(r) => {
            val countMap = Map("count" -> ProductCatalog(r).itemsCount)
            val requestBody: RequestBody = RequestBody.create(APP_TYPE, JsonUtils.mapToJsonString(countMap))
            HttpClient.performPostCall(postUrl, headers, requestBody)
          }
        }
      }
    }
  }

  def challengeTwo(userId: String, applicationType: String, getUrl: HttpUrl, postUrl: HttpUrl): Either[String, String] = {
    val APP_TYPE = MediaType.parse(applicationType)
    val headers = Headers.of(Map("userId" -> userId).asJava)

    HttpClient.performGetCall(getUrl, headers) match {
      case Left(x) => Left(x)
      case Right(x) => {
        val items = JsonUtils.createObject[Array[Item]](x, classOf[Array[Item]])
        ProductCatalogUtils.sanityCheckAndCorrectDate(items) match {
          case Left(x) => Left(x.toString)
          case Right(r) => {
            val countMap = Map("count" -> ProductCatalog(r).activeItemsCount)
            val requestBody: RequestBody = RequestBody.create(APP_TYPE, JsonUtils.mapToJsonString(countMap))
            HttpClient.performPostCall(postUrl, headers, requestBody)
          }
        }
      }
    }
  }

  def challengeThree(userId: String, applicationType: String, getUrl: HttpUrl, postUrl: HttpUrl): Either[String, String] = {
    val APP_TYPE = MediaType.parse(applicationType)
    val headers = Headers.of(Map("userId" -> userId).asJava)

    HttpClient.performGetCall(getUrl, headers) match {
      case Left(x) => Left(x)
      case Right(x) => {
        val items = JsonUtils.createObject[Array[Item]](x, classOf[Array[Item]])
        ProductCatalogUtils.sanityCheckAndCorrectDate(items) match {
          case Left(x) => Left(x.toString)
          case Right(r) => {
            val requestBody: RequestBody = RequestBody.create(APP_TYPE, JsonUtils.mapToJsonString(ProductCatalog(r).activeItemsCountByCategory))
            HttpClient.performPostCall(postUrl, headers, requestBody)
          }
        }
      }
    }
  }

  def challengeFour(userId: String, applicationType: String, getUrl: HttpUrl, postUrl: HttpUrl): Either[String, String] = {
    val APP_TYPE = MediaType.parse(applicationType)
    val headers = Headers.of(Map("userId" -> userId).asJava)

    HttpClient.performGetCall(getUrl, headers) match {
      case Left(x) => Left(x)
      case Right(x) => {
        val items = JsonUtils.createObject[Array[Item]](x, classOf[Array[Item]])
        ProductCatalogUtils.sanityCheckAndCorrectDate(items) match {
          case Left(x) => Left(x.toString)
          case Right(r) => {
            val requestBody: RequestBody = RequestBody.create(APP_TYPE, JsonUtils.mapToJsonString(ProductCatalog(r).totalPriceOfActiveItems))
            HttpClient.performPostCall(postUrl, headers, requestBody)
          }
        }
      }
    }
  }

  //  def main(args: Array[String]): Unit = {
  //    val userId = "S1fQgA0YW"
  //    val applicationType = "application/json"
  //    val getUrl = HttpUrl.parse("http://tw-http-hunt-api-1062625224.us-east-2.elb.amazonaws.com/challenge/input")
  //    val postUrl = HttpUrl.parse("http://tw-http-hunt-api-1062625224.us-east-2.elb.amazonaws.com/challenge/output")
  //
  //    val responseOne = challengeOne(userId, applicationType, getUrl, postUrl)
  //    val responseTwo = challengeTwo(userId, applicationType, getUrl, postUrl)
  //    val responseThree = challengeThree(userId, applicationType, getUrl, postUrl)
  //    println("response : " + responseOne)
  //    println("response : " + responseTwo)
  //    println("response : " + responseThree)
  //  }

}