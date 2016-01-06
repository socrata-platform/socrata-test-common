package com.socrata.testcommon
package mocks

import com.typesafe.config.Config

import com.socrata.curator.{CuratedClientConfig, CuratedServiceClient, ServerProvider}
import com.socrata.http.client.{RequestBuilder, Response, SimpleHttpRequest}
import com.socrata.http.common.util.Acknowledgeable
import com.socrata.http.server.responses._

import StaticCuratedClient._
import UnusedSugarCommon._

/** A `CuratedServiceClient` that will return a user provided response (or a
  * response generated from a provided factory).
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
class StaticCuratedClient(resp: CuratedRequest => Response)
    extends CuratedServiceClient(EmptyProvider, EmptyClientConfig) {
  override def execute[T](request: RequestBuilder => SimpleHttpRequest,
                          callback: Response => T): T = callback(resp(request))
}

object StaticCuratedClient {
  type CuratedRequest = RequestBuilder => SimpleHttpRequest

  // scalastyle:off number.of.methods
  object EmptyConfig extends com.typesafe.config.Config {
    def atKey(x$1: String): com.typesafe.config.Config = this
    def atPath(x$1: String): com.typesafe.config.Config = this
    def checkValid(x$1: com.typesafe.config.Config,x$2: String*): Unit = {}
    def entrySet(): java.util.Set[java.util.Map.Entry[String,com.typesafe.config.ConfigValue]] =
      new java.util.HashSet()
    def getAnyRef(x$1: String): Object = this
    def getAnyRefList(x$1: String): java.util.List[_] = Unused
    def getBoolean(x$1: String): Boolean = Unused
    def getBooleanList(x$1: String): java.util.List[java.lang.Boolean] = Unused
    def getBytes(x$1: String): java.lang.Long = Unused
    def getBytesList(x$1: String): java.util.List[java.lang.Long] = Unused
    def getConfig(x$1: String): com.typesafe.config.Config = this
    def getConfigList(x$1: String): java.util.List[com.typesafe.config.Config] = Unused
    def getDouble(x$1: String): Double = Unused
    def getDoubleList(x$1: String): java.util.List[java.lang.Double] = Unused
    def getDuration(x$1: String,x$2: java.util.concurrent.TimeUnit): Long = Unused
    def getDurationList(x$1: String,
                        x$2: java.util.concurrent.TimeUnit): java.util.List[java.lang.Long] = Unused
    def getInt(x$1: String): Int = Unused
    def getIntList(x$1: String): java.util.List[Integer] = Unused
    def getList(x$1: String): com.typesafe.config.ConfigList =
      throw new UnsupportedOperationException()
    def getLong(x$1: String): Long = Unused
    def getLongList(x$1: String): java.util.List[java.lang.Long] = Unused
    def getMilliseconds(x$1: String): java.lang.Long = Unused
    def getMillisecondsList(x$1: String): java.util.List[java.lang.Long] = Unused
    def getNanoseconds(x$1: String): java.lang.Long = Unused
    def getNanosecondsList(x$1: String): java.util.List[java.lang.Long] = Unused
    def getNumber(x$1: String): Number = (Unused: java.lang.Long)
    def getNumberList(x$1: String): java.util.List[Number] = Unused
    def getObject(x$1: String): com.typesafe.config.ConfigObject = throw new UnsupportedOperationException()
    def getObjectList(x$1: String): java.util.List[com.typesafe.config.ConfigObject] = Unused
    def getString(x$1: String): String = Unused
    def getStringList(x$1: String): java.util.List[String] = Unused
    def getValue(x$1: String): com.typesafe.config.ConfigValue =
      throw new UnsupportedOperationException()
    def hasPath(x$1: String): Boolean = Unused
    def isEmpty(): Boolean = Unused
    def isResolved(): Boolean = Unused
    def origin(): com.typesafe.config.ConfigOrigin = throw new UnsupportedOperationException()
    def resolve(x$1: com.typesafe.config.ConfigResolveOptions): com.typesafe.config.Config =
      throw new UnsupportedOperationException()
    def resolve(): com.typesafe.config.Config = throw new UnsupportedOperationException()
    def resolveWith(x$1: com.typesafe.config.Config,
                    x$2: com.typesafe.config.ConfigResolveOptions): com.typesafe.config.Config =
      throw new UnsupportedOperationException()
    def resolveWith(x$1: com.typesafe.config.Config): com.typesafe.config.Config =
      throw new UnsupportedOperationException()
    def root(): com.typesafe.config.ConfigObject = throw new UnsupportedOperationException()
    def withFallback(x$1: com.typesafe.config.ConfigMergeable): com.typesafe.config.Config = this
    def withOnlyPath(x$1: String): com.typesafe.config.Config = this
    def withValue(x$1: String,x$2: com.typesafe.config.ConfigValue): com.typesafe.config.Config =
      throw new UnsupportedOperationException()
    def withoutPath(x$1: String): com.typesafe.config.Config = this
  }
  // scalastyle:on number.of.methods

  private object EmptyProvider extends ServerProvider(() => None, mocks.StaticHttpClient())
  private object EmptyClientConfig extends CuratedClientConfig(EmptyConfig, Unused)
  object EmptyResponse extends Response {
    def charset: java.nio.charset.Charset = java.nio.charset.StandardCharsets.UTF_8
    def inputStream(maximumSizeBetweenAcks: Long): java.io.InputStream with Acknowledgeable =
      new mocks.util.AcknowledgeableInputStream()
    def streamCreated: Boolean = true

    // Members declared in com.socrata.http.client.ResponseInfo
    def headerNames: Set[String] = Set.empty
    def headers(name: String): Array[String] = Array.empty
    def resultCode: Int = OK.statusCode
  }

  def apply(resp: CuratedRequest => Response): CuratedServiceClient = new StaticCuratedClient(resp)
  def apply(resp: () => Response): CuratedServiceClient = apply { r => resp() }
  def apply(resp: Response): CuratedServiceClient = apply { () => resp }
  def apply(): CuratedServiceClient = apply(EmptyResponse)
}
