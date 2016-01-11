package com.socrata.testcommon
package mocks
package util

import java.io.ByteArrayInputStream
import javax.servlet.ServletInputStream

class StaticServletInputStream private (payload: Array[Byte]) extends ServletInputStream {
  val underlying = new ByteArrayInputStream(payload)
  private var nextRead: Option[Int] = Some(underlying.read()).filter(_ != -1)

  def read(): Int = {
    val ret = if (nextRead.isDefined) {
      nextRead.get
    } else {
      underlying.read()
    }

    val cur = underlying.read()
    nextRead = if (cur != -1) Some(cur) else None
    ret
  }

  def isFinished(): Boolean = nextRead.isEmpty
  def isReady(): Boolean = true
  def setReadListener(listener: javax.servlet.ReadListener): Unit =
    throw new UnsupportedOperationException()
}

object StaticServletInputStream {
  private[mocks] def apply(payload: Array[Byte]): StaticServletInputStream =
    new StaticServletInputStream(payload)

  private[mocks] def apply(): StaticServletInputStream = apply(Array.empty)
}
