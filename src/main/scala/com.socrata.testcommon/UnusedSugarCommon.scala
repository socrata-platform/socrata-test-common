package com.socrata.testcommon

/** Extend this to create a customized `UnusedSugar` trait for your project.
  *
  * Once an `UnusedSugar` trait has been mixed in `Unused` can be used in place of
  * any value that is irrelevant to the provided test (that is, it isn't
  * exercised on the current codepath).
  *s
  * NOTE: `UnusedSugar` will need to include both of the following:
  * ```
  * import com.socrata.testcommon.UnusedSugarCommon
  * import com.socrata.testcommon.UnusedSugarCommon._
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
trait UnusedSugarCommon
    extends UnusedSugarStandard
    with extras.UnusedSimpleArm
    with extras.UnusedHttp
    with extras.UnusedCurator

/** This can be imported instead of extending the trait to get similar functionality. */
object UnusedSugarCommon extends UnusedSugarCommon {
  type UnusedValue = UnusedSugarStandard.UnusedValue
}
