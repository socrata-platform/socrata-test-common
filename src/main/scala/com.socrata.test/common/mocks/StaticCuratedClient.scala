package com.socrata.test.common
package mocks

import com.typesafe.config.Config

import com.socrata.curator.{CuratedClientConfig, CuratedServiceClient, ServerProvider}
import com.socrata.http.client.{RequestBuilder, Response, SimpleHttpRequest}

import StaticCuratedClient._
import UnusedSugarCommon._

class StaticCuratedClient(resp: CuratedRequest => Response)
      extends CuratedServiceClient(EmptyProvider, EmptyClientConfig) {
    override def execute[T](request: RequestBuilder => SimpleHttpRequest,
                            callback: Response => T): T = callback(resp(request))
}

object StaticCuratedClient {
  type CuratedRequest = RequestBuilder => SimpleHttpRequest

  private object EmptyProvider extends ServerProvider(() => None, mocks.StaticHttpClient())
  private object EmptyClientConfig extends CuratedClientConfig(EmptyConfig, Unused)

  def apply(resp: CuratedRequest => Response): CuratedServiceClient = new StaticCuratedClient(resp)
  def apply(resp: () => Response): CuratedServiceClient = apply { r => resp() }
  def apply(resp: Response): CuratedServiceClient = apply { () => resp }
  def apply(): CuratedServiceClient = apply(EmptyResponse)
}
