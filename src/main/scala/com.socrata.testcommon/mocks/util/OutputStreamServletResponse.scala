package com.socrata.testcommon
package mocks.util

import scala.collection.JavaConverters._

import java.nio.charset.{Charset, StandardCharsets}
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse

import UnusedSugarCommon._

// scalastyle:off number.of.methods
class OutputStreamServletResponse private[mocks] (os: ServletOutputStream) extends HttpServletResponse {
  def getOutputStream(): javax.servlet.ServletOutputStream = os
  def getWriter(): java.io.PrintWriter = new java.io.PrintWriter(os)

  private var frozen = false
  private def mutate(setter: => Unit): Unit = if (!frozen) {
    setter
  } else {
    throw new IllegalStateException("Response is frozen!")
  }

  // So we can restore some semblance of immutability...
  def freeze(): Unit = frozen = true

  // We need to actually store some of these things...
  private var status: Int = 0
  def getStatus(): Int = status

  def setStatus(status: Int): Unit = mutate {
    this.status = status
  }

  def setStatus(status: Int, ignored: String): Unit = setStatus(status)
  def sendError(status: Int): Unit = setStatus(status)
  def sendError(status: Int, ignored: String): Unit = setStatus(status)
  def sendRedirect(ignored: String): Unit = setStatus(HttpServletResponse.SC_FOUND)

  private var contentType: String = ""
  def getContentType(): String = contentType
  def setContentType(contentType: String): Unit = mutate {
    this.contentType = contentType
    setHeader("Content-Type", contentType)
  }

  private var headers = Map.empty[String, List[String]]
  def addHeader(key: String, value: String): Unit = mutate {
    val values = value +: headers(key)
    headers = headers + (key -> values)
  }

  def setHeader(key: String, value: String): Unit = mutate {
    headers = headers + (key -> List(value))
  }

  def addCookie(cookie: javax.servlet.http.Cookie): Unit = addHeader("Set-Cookie", cookie.toString)
  def addDateHeader(key: String, value: Long): Unit = addHeader(key, value.toString)
  def addIntHeader(key: String, value: Int): Unit = addHeader(key, value.toString)
  def setDateHeader(key: String, value: Long): Unit = setHeader(key, value.toString)
  def setIntHeader(key: String, value: Int): Unit = setHeader(key, value.toString)
  def containsHeader(key: String): Boolean = headers.contains(key)

  def getHeader(key: String): String = headers(key).head
  def getHeaderNames(): java.util.Collection[String] = headers.asJava.keySet
  def getHeaders(key: String): java.util.Collection[String] = headers(key).asJava

  private var locale: java.util.Locale =
    new java.util.Locale.Builder().setLanguage("en").setRegion("US").build()
  def getLocale(): java.util.Locale = locale
  def setLocale(locale: java.util.Locale): Unit = mutate {
    this.locale = locale
  }

  private var charset = StandardCharsets.UTF_8.name
  def setCharacterEncoding(charset: String): Unit = mutate {
    this.charset = charset
  }

  def getCharacterEncoding(): String = charset

  private var contentLength: Long = Unused
  def setContentLength(len: Int): Unit = mutate {
    contentLength = len
  }

  def setContentLengthLong(len: Long): Unit = mutate {
    contentLength = len
  }

  def isCommitted(): Boolean = frozen

  // Not using these.
  def flushBuffer(): Unit = {}
  def reset(): Unit = {}
  def resetBuffer(): Unit = {}
  def setBufferSize(x$1: Int): Unit = {}
  def encodeRedirectURL(x$1: String): String = Unused
  def encodeRedirectUrl(x$1: String): String = Unused
  def encodeURL(x$1: String): String = Unused
  def encodeUrl(x$1: String): String = Unused
  def getBufferSize(): Int = Unused
}
// scalastyle:on number.of.methods
