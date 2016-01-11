package com.socrata.testcommon
package extras

import scala.language.implicitConversions

import com.socrata.curator.{CuratedClientConfig, CuratedServiceClient, ServerProvider}

import UnusedSugarStandard._

/** Adds conversions from `Unused` to various classes used with Socrata-Curator-Utils. */
trait UnusedCurator extends UnusedSugarStandardSimple {
  implicit def unusedToCuratedServiceClient(u: UnusedValue): CuratedServiceClient =
    mocks.StaticCuratedClient()
}
