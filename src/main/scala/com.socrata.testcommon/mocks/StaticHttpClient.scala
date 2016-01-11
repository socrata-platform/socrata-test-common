package com.socrata.testcommon.mocks

import java.io.Closeable

import com.socrata.http.client._
import com.socrata.http.server.responses._

/** A `HttpClient` that will return a user provided response (or a response
  * generated from a provided factory).
  *
  * Instances of this class should be created via function application on the
  * companion `object`.
  *
  * There are a variety of useful overloads provided, however more
  * customization points may be needed.
  *
  * Don't hesitate to submit a pull-request if you find any that are useful to
  * your project.
  */
class StaticHttpClient protected (val respBuilder: (SimpleHttpRequest => Response)) extends HttpClient {
  val close: Unit = ()

  private def rawResp(r: Response) = new RawResponse with Closeable {
    val responseInfo = r
    val body = r.inputStream()
    val close = ()
  }

  def executeRawUnmanaged(req: SimpleHttpRequest): RawResponse with Closeable =
    rawResp(respBuilder(req))
}

object StaticHttpClient {
  def apply(f: SimpleHttpRequest => Response): StaticHttpClient = new StaticHttpClient(f)
  def apply(resp: Response): StaticHttpClient = apply(_ => resp)
  def apply(resp: String, statusCode: Int = OK.statusCode): StaticHttpClient =
    apply(StaticResponse(resp, statusCode))
  def apply(): StaticHttpClient = apply("")
}
