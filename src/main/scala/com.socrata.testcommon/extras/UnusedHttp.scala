package com.socrata.testcommon
package extras

import scala.language.implicitConversions

import com.rojoma.simplearm.v2.ResourceScope

import com.socrata.http.client.{HttpClient, RequestBuilder, Response}
import com.socrata.http.server.responses._
import com.socrata.http.server.{HttpRequest, HttpResponse}

import UnusedSugarSimple._

trait UnusedHttp extends UnusedSugarSimple with UnusedSimpleArm {
  private val rs = this.resourceScope

  implicit def unusedToRespToHttpResponse(u: UnusedValue): Response => HttpResponse = { r => OK }

  implicit def unusedToHttpRequest(u: UnusedValue): HttpRequest = new HttpRequest {
    def servletRequest: HttpRequest.AugmentedHttpServletRequest =
      new HttpRequest.AugmentedHttpServletRequest(mocks.util.AugmentedServletRequest())
    def resourceScope = rs
  }

  implicit def unusedToRequestBuilder(u: UnusedValue): RequestBuilder =
    RequestBuilder(Unused)

  implicit def unusedToHttpClient(u: UnusedValue): HttpClient =
    mocks.StaticHttpClient()
}
