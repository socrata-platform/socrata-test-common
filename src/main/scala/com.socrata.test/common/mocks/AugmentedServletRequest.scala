package com.socrata.test.common
package mocks

import java.util.Collections
import javax.servlet._
import javax.servlet.http._
import scala.collection.JavaConverters._

import UnusedSugarCommon._

class AugmentedServletRequest(private val headers: Map[String, Seq[String]], // scalastyle:ignore
                              private val params: Map[String, String],
                              private val method: String = "GET")
    extends HttpServletRequest {
  def getQueryString(): String =
    params map { case (k, v) => s"$k=$v" } mkString "&"

  def getHeaderNames(): java.util.Enumeration[String] =
    Collections.enumeration(headers.keys.toSeq.asJava)
  def getHeader(name: String): String = headers.get(name).map(_.head).orNull
  def getHeaders(name: String): java.util.Enumeration[String] =
    Collections.enumeration(headers.asJava.get(name).asJava)
  def getIntHeader(name: String): Int = getHeader(name).toInt
  def getDateHeader(name: String): Long = getHeader(name).toLong

  def getMethod(): String = method

  // Whee Boilerplate!
  def authenticate(ignored: HttpServletResponse): Boolean = Unused
  def changeSessionId(): String = Unused
  def getAsyncContext(): AsyncContext = throw new UnsupportedOperationException()
  def getAttribute(x$1: String): Object = Unused
  def getAttributeNames(): java.util.Enumeration[String] = Unused
  def getAuthType(): String = Unused
  def getCharacterEncoding(): String = Unused
  def getContentLength(): Int = Unused
  def getContentLengthLong(): Long = Unused
  def getContentType(): String = Unused
  def getContextPath(): String = Unused
  def getCookies(): Array[Cookie] = Unused
  def getDispatcherType(): DispatcherType = DispatcherType.ASYNC
  def getInputStream(): ServletInputStream = StaticServletInputStream()
  def getLocalAddr(): String = Unused
  def getLocalName(): String = Unused
  def getLocalPort(): Int = Unused
  def getLocale(): java.util.Locale = java.util.Locale.getDefault()
  def getLocales(): java.util.Enumeration[java.util.Locale] = Unused
  def getParameter(x$1: String): String = Unused
  def getParameterMap(): java.util.Map[String,Array[String]] = Map.empty.asJava
  def getParameterNames(): java.util.Enumeration[String] = Unused
  def getParameterValues(x$1: String): Array[String] = Unused
  def getPart(x$1: String): Part = throw new UnsupportedOperationException()
  def getParts(): java.util.Collection[Part] = Unused: java.util.List[Part]
  def getPathInfo(): String = Unused
  def getPathTranslated(): String = Unused
  def getProtocol(): String = Unused
  def getReader(): java.io.BufferedReader = Unused
  def getRealPath(x$1: String): String = Unused
  def getRemoteAddr(): String = Unused
  def getRemoteHost(): String = Unused
  def getRemotePort(): Int = Unused
  def getRemoteUser(): String = Unused
  def getRequestDispatcher(x$1: String): RequestDispatcher = throw new UnsupportedOperationException()
  def getRequestURI(): String = Unused
  def getRequestURL(): StringBuffer = Unused
  def getRequestedSessionId(): String = Unused
  def getScheme(): String = Unused
  def getServerName(): String = Unused
  def getServerPort(): Int = Unused
  def getServletContext(): ServletContext = throw new UnsupportedOperationException()
  def getServletPath(): String = Unused
  def getSession(): HttpSession = throw new UnsupportedOperationException()
  def getSession(x$1: Boolean): HttpSession = throw new UnsupportedOperationException()
  def getUserPrincipal(): java.security.Principal = throw new UnsupportedOperationException()
  def isAsyncStarted(): Boolean = Unused
  def isAsyncSupported(): Boolean = Unused
  def isRequestedSessionIdFromCookie(): Boolean = Unused
  def isRequestedSessionIdFromURL(): Boolean = Unused
  def isRequestedSessionIdFromUrl(): Boolean = Unused
  def isRequestedSessionIdValid(): Boolean = Unused
  def isSecure(): Boolean = Unused
  def isUserInRole(x$1: String): Boolean = Unused
  def login(x$1: String, x$2: String): Unit = {}
  def logout(): Unit = {}
  def removeAttribute(x$1: String): Unit = {}
  def setAttribute(x$1: String, x$2: Any): Unit = {}
  def setCharacterEncoding(x$1: String): Unit = {}
  def startAsync(): AsyncContext = throw new UnsupportedOperationException()
  def startAsync(x$1: ServletRequest, x$2: ServletResponse): AsyncContext =
    throw new UnsupportedOperationException()
  def upgrade[T](x$1: Class[T]): T = throw new UnsupportedOperationException()
}

object AugmentedServletRequest {
  private val Unset = ("", "")
  private def listify(m: Map[String, String]): Map[String, Seq[String]] = m.map { case (k, v) =>
    k -> Seq(v)
  }

  def apply(header: (String, String) = Unset,
            param: (String, String) = Unset,
            headers: Map[String, String] = Map.empty,
            params: Map[String, String] = Map.empty): AugmentedServletRequest = {
    val hdrs = if (header eq Unset) Map(header) else headers
    val prms = if (param eq Unset) Map(param) else params
    new AugmentedServletRequest(listify(hdrs), prms)
  }
}
