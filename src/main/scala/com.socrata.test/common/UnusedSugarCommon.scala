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

  private object EmptyConfig extends com.typesafe.config.Config {
    def atKey(x$1: String): com.typesafe.config.Config = ???
    def atPath(x$1: String): com.typesafe.config.Config = ???
    def checkValid(x$1: com.typesafe.config.Config,x$2: String*): Unit = ???
    def entrySet(): java.util.Set[java.util.Map.Entry[String,com.typesafe.config.ConfigValue]] = ???
    def getAnyRef(x$1: String): Object = ???
    def getAnyRefList(x$1: String): java.util.List[_] = ???
    def getBoolean(x$1: String): Boolean = ???
    def getBooleanList(x$1: String): java.util.List[java.lang.Boolean] = ???
    def getBytes(x$1: String): java.lang.Long = ???
    def getBytesList(x$1: String): java.util.List[java.lang.Long] = ???
    def getConfig(x$1: String): com.typesafe.config.Config = ???
      // ScalaStyle chokes on this line.
      // scalastyle:off
    def getConfigList(x$1: String): java.util.List[_ <: com.typesafe.config.Config] = ???
      // scalastyle:on
    def getDouble(x$1: String): Double = ???
    def getDoubleList(x$1: String): java.util.List[java.lang.Double] = ???
    def getDuration(x$1: String,x$2: java.util.concurrent.TimeUnit): Long = ???
    def getDurationList(x$1: String,
                        x$2: java.util.concurrent.TimeUnit): java.util.List[java.lang.Long] = ???
    def getInt(x$1: String): Int = ???
    def getIntList(x$1: String): java.util.List[Integer] = ???
    def getList(x$1: String): com.typesafe.config.ConfigList = ???
    def getLong(x$1: String): Long = ???
    def getLongList(x$1: String): java.util.List[java.lang.Long] = ???
    def getMilliseconds(x$1: String): java.lang.Long = ???
    def getMillisecondsList(x$1: String): java.util.List[java.lang.Long] = ???
    def getNanoseconds(x$1: String): java.lang.Long = ???
    def getNanosecondsList(x$1: String): java.util.List[java.lang.Long] = ???
    def getNumber(x$1: String): Number = ???
    def getNumberList(x$1: String): java.util.List[Number] = ???
    def getObject(x$1: String): com.typesafe.config.ConfigObject = ???
    def getObjectList(x$1: String): java.util.List[_ <: com.typesafe.config.ConfigObject] = ???
    def getString(x$1: String): String = ???
    def getStringList(x$1: String): java.util.List[String] = ???
    def getValue(x$1: String): com.typesafe.config.ConfigValue = ???
    def hasPath(x$1: String): Boolean = ???
    def isEmpty(): Boolean = ???
    def isResolved(): Boolean = ???
    def origin(): com.typesafe.config.ConfigOrigin = ???
    def resolve(x$1: com.typesafe.config.ConfigResolveOptions): com.typesafe.config.Config = ???
    def resolve(): com.typesafe.config.Config = ???
    def resolveWith(x$1: com.typesafe.config.Config,
                    x$2: com.typesafe.config.ConfigResolveOptions): com.typesafe.config.Config = ???
    def resolveWith(x$1: com.typesafe.config.Config): com.typesafe.config.Config = ???
    def root(): com.typesafe.config.ConfigObject = ???
    def withFallback(x$1: com.typesafe.config.ConfigMergeable): com.typesafe.config.Config = ???
    def withOnlyPath(x$1: String): com.typesafe.config.Config = ???
    def withValue(x$1: String,x$2: com.typesafe.config.ConfigValue): com.typesafe.config.Config = ???
    def withoutPath(x$1: String): com.typesafe.config.Config = ???
  }

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
