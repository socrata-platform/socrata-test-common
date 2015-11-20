package com.socrata.test.mocks

import java.io.Closeable

import com.socrata.http.client._
import com.socrata.http.server.responses._

class StaticHttpClient(val respBuilder: (SimpleHttpRequest => Response)) extends HttpClient {
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
  def apply(resp: String, resultCode: Int = OK.statusCode): StaticHttpClient =
    apply(StaticResponse(resp, resultCode))
  def apply(): StaticHttpClient = apply("")
}
