package com.socrata.testcommon

import scala.language.implicitConversions

import UnusedSugarStandard._

/** Like `UnusedSugarCommon` but limits conversions to types contained in the
  * standard library. */
trait UnusedSugarStandard extends UnusedSugarStandardSimple {
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

/** This can be imported instead of extending the trait to get similar functionality. */
object UnusedSugarStandard extends UnusedSugarStandard {
  type UnusedValue = UnusedSugarStandardSimple.UnusedValue
}
