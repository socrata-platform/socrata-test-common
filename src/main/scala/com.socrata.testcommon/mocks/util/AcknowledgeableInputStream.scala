package com.socrata.testcommon.mocks.util

import java.io.{ByteArrayInputStream, InputStream}
import java.nio.charset.{Charset, StandardCharsets}

import com.socrata.http.common.util.Acknowledgeable

/** Will return user provided data to its consumers. */
class AcknowledgeableInputStream private (payload: Array[Byte])
    extends InputStream
    with Acknowledgeable {
  def this(payload: String, charset: Charset) = this(payload.getBytes(charset))
  def this(payload: String) = this(payload, StandardCharsets.UTF_8)
  def this() = this(Array.empty[Byte])

  val underlying = new ByteArrayInputStream(payload)

  override def acknowledge(): Unit = ()
  override def read(): Int = underlying.read
}

object AcknowledgeableInputStream {
  private[mocks] def apply(payload: Array[Byte]): AcknowledgeableInputStream =
    new AcknowledgeableInputStream(payload)
  private[mocks] def apply(payload: String, charset: Charset): AcknowledgeableInputStream =
    apply(payload.getBytes(charset))
  private[mocks] def apply(payload: String): AcknowledgeableInputStream =
    apply(payload, StandardCharsets.UTF_8)
  private[mocks] def apply(): AcknowledgeableInputStream =
    apply(Array.empty[Byte])
}
