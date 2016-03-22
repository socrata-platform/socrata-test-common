package com.socrata.testcommon

import javax.servlet.http.HttpServletResponse
import scala.collection.JavaConverters._

import com.socrata.http.server.HttpResponse

import ResponseSugar._

/** This trait should be used when you need to handle Socrata-Http responses in
  * your tests.
  *
  * The `unpackResponse` allows HttpResponse objects to be treated as though
  * they are immutable in test assertions.
  *
  * If you are asserting on something you think would be useful for other
  * consumers of this library, please submit a pull request adding appropriate
  * methods/`val`s to the `UnpackedResponse` class.
  */
trait ResponseSugar {
  def unpackResponse(serverResp: HttpResponse): UnpackedResponse = {
    val os = new mocks.util.CapturingServletOutputStream
    val resp = os.responseFor
    serverResp(resp) // Mutates `resp`
    resp.freeze()    // Now we can pretend this is a reasonable class!
    new UnpackedResponse(resp, os)
  }
}

object ResponseSugar extends ResponseSugar {
  class Body(stream: mocks.util.CapturingServletOutputStream) {
    val toByteArray: Array[Byte] = stream.getBytes
    override lazy val toString: String = stream.getString
    lazy val toLowStr: String = stream.getLowStr
    lazy val toLowerCaseString: String = stream.getLowStr
  }

  class UnpackedResponse private[ResponseSugar] (val underlying: HttpServletResponse,
                                                 stream: mocks.util.CapturingServletOutputStream) {
    private val Truncate = 24

    val status: Int = underlying.getStatus
    lazy val statusCode: Int = status
    lazy val contentType: String = underlying.getContentType
    lazy val body: Body = new Body(stream)
    lazy val headers: Map[String, String] = allHeaders.map { case (key, value) =>
      key -> allHeaders(key).head
    }

    lazy val allHeaders: Map[String, Seq[String]] = {
      val keys = underlying.getHeaderNames().asScala
      keys.map { key =>
        key -> underlying.getHeaders(key).asScala.toSeq
      }.toMap
    }

    private def ellipsize(s: String): String = if (s.length > Truncate) s.slice(0, Truncate) + "..." else s

    override def toString: String = {
      val fields = Seq(
        s"status=${status}",
        s"contentType=${ellipsize(contentType)}",
        s"body=${ellipsize(body.toString)}",
        s"headers=${headers}").mkString(", ")

      s"UnpackedResponse(${fields})"
    }

  }
}
