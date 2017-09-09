package org.prac.httpgame.utils

import com.squareup.okhttp.mockwebserver.{MockResponse, MockWebServer}
import com.squareup.okhttp.{Headers, HttpUrl, MediaType, RequestBody}
import org.scalatest.{FunSpec, Matchers}

import scala.collection.JavaConverters._

class HttpClientTest extends FunSpec with Matchers {

  describe("HttpClient") {
    it("should be return data on successful get request") {
      //given
      val server = new MockWebServer
      server.enqueue(new MockResponse().setBody("{\"input\":[{\"price\":123,\"name\":\"abc\"},{\"price\":234,\"name\":\"asd\"}]}"))
      server.start()
      val url: HttpUrl = server.url("/challenge/input")
      val headers = Headers.of(Map("userId" -> "S1fQgA0YW").asJava)
      //when
      val getResponse = HttpClient.performGetCall(url, headers)
      val request = server.takeRequest
      //then
      getResponse shouldEqual Right("{\"input\":[{\"price\":123,\"name\":\"abc\"},{\"price\":234,\"name\":\"asd\"}]}")
      request.getHeader("userId") shouldEqual "S1fQgA0YW"
      server.shutdown()
    }

    it("should be return error code on failure of get request") {
      //given
      val server = new MockWebServer
      server.enqueue(new MockResponse().setResponseCode(404).setBody("Not Found"))
      server.start()
      val url: HttpUrl = server.url("/challenge/input12")
      val headers = Headers.of(Map("userId" -> "S1fQgA0YW").asJava)
      //when
      val getResponse = HttpClient.performGetCall(url, headers)
      val request = server.takeRequest
      //then
      getResponse shouldEqual Left("404")
      request.getHeader("userId") shouldEqual "S1fQgA0YW"
    }

    it("should be return data on successful post request") {
      //given
      val JSON = MediaType.parse("application/json")
      val server = new MockWebServer
      server.enqueue(new MockResponse().setBody("Ok"))
      server.start()
      val url: HttpUrl = server.url("/challenge/input")
      val headers = Headers.of(Map("userId" -> "S1fQgA0YW").asJava)
      val requestBody: RequestBody = RequestBody.create(JSON, "{\"count\":2}")
      //when
      val getResponse = HttpClient.performPostCall(url, headers, requestBody)
      val request = server.takeRequest
      //then
      getResponse shouldEqual Right("Ok")
      request.getHeader("userId") shouldEqual "S1fQgA0YW"
      request.getHeader("Content-Type") shouldEqual "application/json; charset=utf-8"
      server.shutdown()
    }
  }

}
