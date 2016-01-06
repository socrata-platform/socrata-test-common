package com.socrata.testcommon

import scala.language.implicitConversions

import UnusedSugarCommon._

/** Extend this to create a customized `UnusedSugar` trait for your project.
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
}

/** This can be imported instead of extending the state to get similar functionality. */
object UnusedSugarCommon extends UnusedSugarCommon {
  type UnusedValue = UnusedSugarSimple.UnusedValue
}
