package com.socrata.test
package common

import scala.language.implicitConversions

import com.rojoma.simplearm.v2.ResourceScope
import com.socrata.curator.{CuratedClientConfig, CuratedServiceClient, ServerProvider}

import com.socrata.http.client.{HttpClient, RequestBuilder, Response, SimpleHttpRequest}
import com.socrata.http.common.util.Acknowledgeable
import com.socrata.http.server.responses._
import com.socrata.http.server.{HttpRequest, HttpResponse}

import UnusedSugarCommon._

/** Extend this to create a customized `UnusedSugarCommon` trait for your project.
  *
  * Once an `UnusedSugar` trait has been mixed in `Unused` can be used in place of
  * any value that is irrelevant to the provided test (that is, it isn't
  * exercised on the current codepath).
  *s
  * NOTE: `UnusedSugar` will need to include both of the following:
  * ```
  * import com.socrata.test.common.UnusedSugarCommon
  * import com.socrata.test.common.UnusedSugarCommon._
  * ```
  *
  * This is designed to compliment using ScalaCheck, since adding generators for
  * arguments that aren't exercised is tedious.
  *
  * The pattern that exists here can be followed to add support for new types.
  * NOTE: If you are adding custom types you may need to inherit directly from
  * `UnusedSugarSimple` as some of these implicit conversions may conflict due
  * to type erasure.
  */
trait UnusedSugarCommon extends UnusedSugarSimple {
  val resourceScope = rs

  // Scala Collections
  implicit def unusedToArray[A: Manifest](u: UnusedValue): Array[A] = Array[A]()
  implicit def unusedToIndexedSeq[A](u: UnusedValue):IndexedSeq[A] = IndexedSeq.empty
  implicit def unusedToIterable[A](u: UnusedValue): Iterable[A] = Iterable.empty
  implicit def unusedToList[A](u: UnusedValue): List[A] = List.empty
  implicit def unusedToMap[A, B](u: UnusedValue): Map[A, B] = Map.empty
  implicit def unusedToOption[A](u: UnusedValue): Option[A] = None
  implicit def unusedToSeq[A](u: UnusedValue): Seq[A] = Seq.empty
  implicit def unusedToSet[A](u: UnusedValue): Set[A] = Set.empty

  // Java Collections
  implicit def unusedToJavaEnumeration[T](u: UnusedValue): java.util.Enumeration[T] =
    java.util.Collections.enumeration(new java.util.ArrayList())
  implicit def unusedToJavaList[T](u: UnusedValue): java.util.List[T] =
    new java.util.ArrayList()
  implicit def unusedToJavaMap[K, V](u: UnusedValue): java.util.Map[K, V] =
    new java.util.HashMap()
  implicit def unusedToJavaQueue[T](u: UnusedValue): java.util.Queue[T] =
    new java.util.LinkedList()
  implicit def unusedToJavaSet[T](u: UnusedValue): java.util.Set[T] =
    new java.util.HashSet()
  implicit def unusedToJavaSortedMap[K, V](u: UnusedValue): java.util.SortedMap[K, V] =
    new java.util.TreeMap()
  implicit def unusedToJavaSortedSet[T](u: UnusedValue): java.util.SortedSet[T] =
    new java.util.TreeSet()

  // Optional Dependencies.
  implicit def unusedToCuratedServiceClient(u: UnusedValue): CuratedServiceClient =
    EmptyCuratedServiceClient

  implicit def unusedToResourceScope(u: UnusedValue): ResourceScope = resourceScope

  implicit def unusedToRespToHttpResponse(
    u: UnusedValue): Response => HttpResponse = { r => OK }

  implicit def unusedToHttpRequest(u: UnusedValue): HttpRequest = new HttpRequest {
    def servletRequest: HttpRequest.AugmentedHttpServletRequest =
      new HttpRequest.AugmentedHttpServletRequest(mocks.AugmentedServletRequest())
    def resourceScope = rs
  }

  implicit def unusedToRequestBuilder(u: UnusedValue): RequestBuilder =
    RequestBuilder(UnusedObj)

  implicit def unusedToHttpClient(u: UnusedValue): HttpClient =
    mocks.StaticHttpClient()
}

/** This can be imported instead of extending the state to get similar functionality. */
object UnusedSugarCommon extends UnusedSugarCommon {
  type UnusedValue = UnusedSugarSimple.UnusedValue

  private object EmptyResponse extends Response {
    def charset: java.nio.charset.Charset = java.nio.charset.StandardCharsets.UTF_8
    def inputStream(maximumSizeBetweenAcks: Long): java.io.InputStream with Acknowledgeable =
      new mocks.AcknowledgeableInputStream()
    def streamCreated: Boolean = true

    // Members declared in com.socrata.http.client.ResponseInfo
    def headerNames: Set[String] = Set.empty
    def headers(name: String): Array[String] = Array.empty
    def resultCode: Int = OK.statusCode
  }

  // scalastyle:off number.of.methods
  private object EmptyConfig extends com.typesafe.config.Config {
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
  private object EmptyClientConfig extends CuratedClientConfig(EmptyConfig, UnusedObj)

  private object EmptyCuratedServiceClient
      extends CuratedServiceClient(EmptyProvider, EmptyClientConfig) {
    override def execute[T](request: RequestBuilder => SimpleHttpRequest,
                            callback: Response => T): T = callback(EmptyResponse)
  }

  // This has to live here so the companion object can extend the trait.
  private object UnusedObj extends UnusedValue

  private val rs = new ResourceScope
}
