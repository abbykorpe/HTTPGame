package org.prac.httpgame

import com.squareup.okhttp.mockwebserver.{MockResponse, MockWebServer}
import com.squareup.okhttp.{Headers, HttpUrl, MediaType, RequestBody}
import org.prac.httpgame.core.{ProductCatalog, Item}
import org.prac.httpgame.engine.Driver
import org.prac.httpgame.utils.{HttpClient, ProductCatalogUtils, JsonUtils}
import org.scalatest.{FunSpec, Matchers}

import scala.collection.JavaConverters._

class IntegrationTest extends FunSpec with Matchers {

  describe("game") {

    it("should get fail if invalid data is passed") {
      //given
      val server = new MockWebServer
      server.enqueue(new MockResponse().setBody("[{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2016 12:00:00 AM\",\"price\":123,\"category\":\"Kitchen\",\"name\":\"\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2012 12:00:00 AM\",\"price\":123,\"category\":\"Kitchen\",\"name\":\"asf\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2017 12:00:00 AM\",\"price\":123,\"category\":\"Electronics\",\"name\":\"asf\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2012 12:00:00 AM\",\"price\":123,\"category\":\"Electronics\",\"name\":\"asf\"}]"))
      server.enqueue(new MockResponse().setBody("Ok"))
      server.start()
      val getUrl: HttpUrl = server.url("/challenge/input")
      val postUrl: HttpUrl = server.url("/challenge/output")

      //when
      val beforeOperationTimeStamp = System.currentTimeMillis()
      val game: Either[String, String] = Driver.challengeOne("S1fQgA0YW", "application/json", getUrl, postUrl)
      val timeDifference = System.currentTimeMillis() - beforeOperationTimeStamp

      //then
      game.isRight shouldBe false
      game.left.get shouldEqual "InvalidItemName"
      server.shutdown()
    }

    it("should send count to server in get and post requests") {
      //given
      val JSON = MediaType.parse("application/json")
      val server = new MockWebServer
      server.enqueue(new MockResponse().setBody("[{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2016 12:00:00 AM\",\"price\":123,\"category\":\"Kitchen\",\"name\":\"ads\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2012 12:00:00 AM\",\"price\":123,\"category\":\"Kitchen\",\"name\":\"asf\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2017 12:00:00 AM\",\"price\":123,\"category\":\"Electronics\",\"name\":\"asf\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2012 12:00:00 AM\",\"price\":123,\"category\":\"Electronics\",\"name\":\"asf\"}]"))
      server.enqueue(new MockResponse().setBody("Ok"))
      server.start()
      val getUrl: HttpUrl = server.url("/challenge/input")
      val postUrl: HttpUrl = server.url("/challenge/output")
      val headers = Headers.of(Map("userId" -> "S1fQgA0YW").asJava)

      //when
      val beforeOperationTimeStamp = System.currentTimeMillis()

      val getResponse: Either[String, String] = HttpClient.performGetCall(getUrl, headers)
      val getRequest = server.takeRequest

      val items = JsonUtils.createObject[Array[Item]](getResponse.right.get, classOf[Array[Item]])

      val countMap = Map("count" -> ProductCatalog(ProductCatalogUtils.sanityCheck(items).right.get).itemsCount)
      val dataAsJson = JsonUtils.mapToJsonString(countMap)
      val requestBody: RequestBody = RequestBody.create(JSON, dataAsJson.getBytes("utf-8"))
      val postResponse = HttpClient.performPostCall(postUrl, headers, requestBody)

      val postRequest = server.takeRequest
      val timeDifference = System.currentTimeMillis() - beforeOperationTimeStamp

      //then
      getResponse.right.get shouldEqual "[{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2016 12:00:00 AM\",\"price\":123,\"category\":\"Kitchen\",\"name\":\"ads\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2012 12:00:00 AM\",\"price\":123,\"category\":\"Kitchen\",\"name\":\"asf\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2017 12:00:00 AM\",\"price\":123,\"category\":\"Electronics\",\"name\":\"asf\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2012 12:00:00 AM\",\"price\":123,\"category\":\"Electronics\",\"name\":\"asf\"}]"
      postResponse.right.get shouldEqual "Ok"
      getRequest.getHeader("userId") shouldEqual "S1fQgA0YW"
      postRequest.getHeader("userId") shouldEqual "S1fQgA0YW"
      postRequest.getHeader("Content-Type") shouldEqual "application/json"
      timeDifference < 2000 shouldEqual true

      server.shutdown()

    }

    it("should send valid items count to server in get and post requests") {
      //given
      val JSON = MediaType.parse("application/json")
      val server = new MockWebServer
      server.enqueue(new MockResponse().setBody("[{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2016 12:00:00 AM\",\"price\":123,\"category\":\"Kitchen\",\"name\":\"ads\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2012 12:00:00 AM\",\"price\":123,\"category\":\"Kitchen\",\"name\":\"asf\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2017 12:00:00 AM\",\"price\":123,\"category\":\"Electronics\",\"name\":\"asf\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2012 12:00:00 AM\",\"price\":123,\"category\":\"Electronics\",\"name\":\"asf\"}]"))
      server.enqueue(new MockResponse().setBody("Ok"))
      server.start()
      val getUrl: HttpUrl = server.url("/challenge/input")
      val postUrl: HttpUrl = server.url("/challenge/output")
      val headers = Headers.of(Map("userId" -> "S1fQgA0YW").asJava)

      //when
      val beforeOperationTimeStamp = System.currentTimeMillis()

      val getResponse: Either[String, String] = HttpClient.performGetCall(getUrl, headers)
      val getRequest = server.takeRequest

      val items = JsonUtils.createObject[Array[Item]](getResponse.right.get, classOf[Array[Item]])

      val countMap = Map("count" -> ProductCatalog(ProductCatalogUtils.sanityCheck(items).right.get).activeItemsCount)
      val dataAsJson = JsonUtils.mapToJsonString(countMap)
      val requestBody: RequestBody = RequestBody.create(JSON, dataAsJson.getBytes("utf-8"))
      val postResponse = HttpClient.performPostCall(postUrl, headers, requestBody)

      val postRequest = server.takeRequest
      val timeDifference = System.currentTimeMillis() - beforeOperationTimeStamp

      //then
      getResponse.right.get shouldEqual "[{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2016 12:00:00 AM\",\"price\":123,\"category\":\"Kitchen\",\"name\":\"ads\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2012 12:00:00 AM\",\"price\":123,\"category\":\"Kitchen\",\"name\":\"asf\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2017 12:00:00 AM\",\"price\":123,\"category\":\"Electronics\",\"name\":\"asf\"},{\"endDate\":\"Dec 2, 2017 12:00:00 AM\",\"startDate\":\"Dec 2, 2012 12:00:00 AM\",\"price\":123,\"category\":\"Electronics\",\"name\":\"asf\"}]"
      postResponse.right.get shouldEqual "Ok"
      getRequest.getHeader("userId") shouldEqual "S1fQgA0YW"
      postRequest.getHeader("userId") shouldEqual "S1fQgA0YW"
      postRequest.getHeader("Content-Type") shouldEqual "application/json"
      timeDifference < 2000 shouldEqual true

      server.shutdown()

    }

  }

}
