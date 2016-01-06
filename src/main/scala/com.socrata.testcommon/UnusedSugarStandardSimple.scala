package com.socrata.testcommon

import scala.language.implicitConversions
import java.io._

import UnusedSugarStandard._
import UnusedSugarStandardSimple._

/** Like `UnusedSugarStandard` but avoids all conversions to types that would
  * require type erasure. */
trait UnusedSugarStandardSimple {
  val Unused: UnusedValue = UnusedSugarStandardSimple.UnusedObj

  implicit def unusedToJavaBoolean(u: UnusedValue): java.lang.Boolean = false
  implicit def unusedToJavaByte(u: UnusedValue): java.lang.Character = 'u'
  implicit def unusedToJavaDouble(u: UnusedValue): java.lang.Double = 0.0
  implicit def unusedToJavaFloat(u: UnusedValue): java.lang.Float = 0.0f
  implicit def unusedToJavaInt(u: UnusedValue): java.lang.Integer = 0
  implicit def unusedToJavaLong(u: UnusedValue): java.lang.Long = 0
  implicit def unusedToJavaShort(u: UnusedValue): java.lang.Short = 0: Short

  implicit def unusedToAnyVal(u: UnusedValue): AnyVal = false: AnyVal
  implicit def unusedToBoolean(u: UnusedValue): Boolean = false
  implicit def unusedToByte(u: UnusedValue): Char = 'u'
  implicit def unusedToDouble(u: UnusedValue): Double = 0.0
  implicit def unusedToFloat(u: UnusedValue): Float = 0.0f
  implicit def unusedToInt(u: UnusedValue): Int = 0
  implicit def unusedToLong(u: UnusedValue): Long = 0
  implicit def unusedToShort(u: UnusedValue): Short = 0
  implicit def unusedToString(u: UnusedValue): String = unusedString

  implicit def unusedToInputStream(u: UnusedValue): InputStream = unusedInputStream
  implicit def unusedToOutputStream(u: UnusedValue): OutputStream = unusedOutputStream
  implicit def unusedToStringBuffer(u: UnusedValue): StringBuffer = new StringBuffer()
  implicit def unusedToReader(u: UnusedValue): java.io.Reader = new EmptyReader()
  implicit def unusedToBufferedReader(u: UnusedValue): java.io.BufferedReader =
    new java.io.BufferedReader(new EmptyReader())
}

/** This can be imported instead of extending the trait to get similar functionality. */
object UnusedSugarStandardSimple extends UnusedSugarStandardSimple {
  private val unusedString = "UNUSED"
  private val unusedInputStream = new ByteArrayInputStream(UnusedObj)
  private val unusedOutputStream = new ByteArrayOutputStream()

  private class EmptyReader extends java.io.Reader {
    override def close(): Unit = {}
    override def read(): Int = -1
    override def read(x$1: Array[Char], x$2: Int, x$3: Int): Int = -1
  }

  trait UnusedValue {
    override val toString = unusedString
  }

  // This has to live here so the companion object can extend the trait.
  private object UnusedObj extends UnusedValue
}
