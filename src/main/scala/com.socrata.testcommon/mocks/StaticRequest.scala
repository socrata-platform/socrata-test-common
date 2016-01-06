package com.socrata.testcommon.mocks

import javax.servlet.http.HttpServletRequest

import com.rojoma.simplearm.v2.ResourceScope

import com.socrata.http.server.HttpRequest
import com.socrata.http.server.HttpRequest.AugmentedHttpServletRequest

import StaticRequest._

class StaticRequest private (underlying: HttpServletRequest) extends HttpRequest {
  val resourceScope: ResourceScope = rs
  def servletRequest: AugmentedHttpServletRequest = new AugmentedHttpServletRequest(underlying)
}

object StaticRequest {
  private val rs = new ResourceScope()

  private[mocks] def apply(underlying: HttpServletRequest): StaticRequest =
    new StaticRequest(underlying)

  def apply(header: (String, String), param: (String, String)): StaticRequest =
    apply(util.AugmentedServletRequest(header = header, param = param))

  def apply(headers: Map[String, String],
            params: Map[String, String]): StaticRequest =
    apply(util.AugmentedServletRequest(headers = headers, params = params))

  def apply(): StaticRequest = apply(util.AugmentedServletRequest())
}
