package com.socrata.test.mocks

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

  def apply(): ServletHttpRequest = apply(AugmentedServletRequest())
}
