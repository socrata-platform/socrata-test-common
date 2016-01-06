package com.socrata.testcommon
package extras

import scala.language.implicitConversions

import com.rojoma.simplearm.v2.ResourceScope

import UnusedSimpleArm._
import UnusedSugarSimple._

/** Unused conversions around SimpleArm, this creates a ResourceScope under the covers. */
trait UnusedSimpleArm extends UnusedSugarSimple {
  val resourceScope = rs

  implicit def unusedToResourceScope(u: UnusedValue): ResourceScope = resourceScope
}

object UnusedSimpleArm {
  private val rs = new ResourceScope
}
