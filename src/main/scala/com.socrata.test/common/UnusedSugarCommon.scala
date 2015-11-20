package com.socrata.test
package common

import scala.language.implicitConversions

import com.rojoma.simplearm.v2.ResourceScope
import com.socrata.curator.CuratedServiceClient

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
    def charset: java.nio.charset.Charset = java.nio.charset.Charset.defaultCharset()
    def inputStream(maximumSizeBetweenAcks: Long): java.io.InputStream with Acknowledgeable =
      new mocks.AcknowledgeableInputStream()
    def streamCreated: Boolean = true

    // Members declared in com.socrata.http.client.ResponseInfo
    def headerNames: Set[String] = Set.empty
    def headers(name: String): Array[String] = Array.empty
    def resultCode: Int = OK.statusCode
  }

  // scalastyle:off null
  private object EmptyCuratedServiceClient extends CuratedServiceClient(null, null) {
    override def execute[T](request: RequestBuilder => SimpleHttpRequest,
                            callback: Response => T): T = callback(EmptyResponse)
  }
  // scalastyle:on null

  // This has to live here so the companion object can extend the trait.
  private object UnusedObj extends UnusedValue

  private val rs = new ResourceScope
}
