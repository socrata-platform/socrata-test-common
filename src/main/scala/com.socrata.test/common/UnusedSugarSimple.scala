package com.socrata.test.common

import scala.language.implicitConversions
import java.io._

import UnusedSugarSimple._

/** Like `UnusedSugarCommon` but avoids all conversions to types that would
  * require type erasure.
  */
trait UnusedSugarSimple {
  val Unused: UnusedValue = UnusedObj

  implicit def unusedToAny(u: UnusedValue): AnyVal = false: AnyVal
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

/** This can be imported instead of extending the state to get similar functionality. */
object UnusedSugarSimple extends UnusedSugarSimple {
  import UnusedSugarCommon._

  private val unusedString = "**UNUSED**"
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
