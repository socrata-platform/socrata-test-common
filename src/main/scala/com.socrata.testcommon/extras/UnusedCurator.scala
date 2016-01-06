package com.socrata.testcommon
package extras

import scala.language.implicitConversions

import com.socrata.curator.{CuratedClientConfig, CuratedServiceClient, ServerProvider}

import UnusedSugarCommon._

trait UnusedCurator extends UnusedSugarSimple {
  implicit def unusedToCuratedServiceClient(u: UnusedValue): CuratedServiceClient =
    mocks.StaticCuratedClient()
}
