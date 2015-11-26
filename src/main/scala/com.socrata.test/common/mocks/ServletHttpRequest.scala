package com.socrata.test.common.mocks

import javax.servlet.http.HttpServletRequest

import com.rojoma.simplearm.v2.ResourceScope

import com.socrata.http.server.HttpRequest
import com.socrata.http.server.HttpRequest.AugmentedHttpServletRequest

import ServletHttpRequest._

class ServletHttpRequest(underlying: HttpServletRequest) extends HttpRequest {
  val resourceScope: ResourceScope = rs
  def servletRequest: AugmentedHttpServletRequest = new AugmentedHttpServletRequest(underlying)
}

object ServletHttpRequest {
  private val rs = new ResourceScope()

  def apply(underlying: HttpServletRequest): ServletHttpRequest =
    new ServletHttpRequest(underlying)

  def apply(header: (String, String), param: (String, String)): ServletHttpRequest =
    apply(AugmentedServletRequest(header = header, param = param))

  def apply(headers: Map[String, String],
            params: Map[String, String]): ServletHttpRequest =
    apply(AugmentedServletRequest(headers = headers, params = params))

  def apply(): ServletHttpRequest = apply(AugmentedServletRequest())
}
