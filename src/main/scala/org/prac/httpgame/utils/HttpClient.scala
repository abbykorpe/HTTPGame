package org.prac.httpgame.utils

import com.squareup.okhttp._

object HttpClient {

  val client = new OkHttpClient()

  def performGetCall(httpUrl: HttpUrl, headers: Headers): Either[String, String] = {
    val getRequest = new Request.Builder()
      .url(httpUrl)
      .headers(headers)
      .build
    val getResponse = client.newCall(getRequest).execute
    getResponse.code() match {
      case 200 => Right(getResponse.body().string())
      case errorCode@_ => Left(errorCode.toString)
    }
  }

  def performPostCall(httpUrl: HttpUrl, headers: Headers, requestBody: RequestBody): Either[String, String] = {
    val getRequest = new Request.Builder()
      .url(httpUrl)
      .headers(headers)
      .post(requestBody)
      .build
    val getResponse = client.newCall(getRequest).execute
    getResponse.code() match {
      case 200 => Right(getResponse.body().string())
      case errorCode@_ => Left(errorCode.toString+","+getResponse.body().string())
    }
  }

}
