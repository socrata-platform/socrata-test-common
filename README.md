# Socrata Test Common #
Various utilities to make testing easier (especially using Socrata
libraries).

## com.socrata.testcommon ##
Generally applicable test code/patterns.

### UnusedSugarCommon ###
Mixing in this trait gives you access to a val named `Unused`, and a
series of conversions to make `Unused` a substitute for most common
built-in classes.

However, ideally you would mix in a subclass of `UnusedSugarCommon`
that you create for your project so that you can add conversions for
types custom to your project.

Also, when using `UnusedSugarCommon` one should generally call
`resourceScope.close()` between tests to avoid leaking resources.

### UnusedSugarStandard ###
This class is like `UnusedSugarCommon`, except it doesn't include any
references to any of the optional dependencies (socrata-http,
socrata-curator-utils, etc).

### UnusedSugarStandardSimple ###
This is worth extending if some of the generic conversions are causing
trouble for you inside of `UnusedSugarCommon` or
`UnusedSugarStandard`.

For example `Map[K, V]` extends `K => V` and thus could cause issues
if you want to overload a specific function.

The downside of extending this is that you lose those same generic
conversions so will have to define any you need yourself.

### ResponseSugar ###
When `ResponseSugar` is mixed in provides `unpackResponse`.

This is useful because it allows accessing responses via the Scala API
that Socrata-Http provides instead of having to fall back to capturing
output and using the underlying `HttpServletResponse`

### com.socrata.testcommon.mocks ###
This package contains various useful mocks, mostly related to
Socrata-Http.

It can be helpful to refer to mocks with some namespacing, to make it
clear they are test implementations.

A useful pattern is to put project specific mock classes in a package
called `mocks` and to import these mocks as `common.mocks` to avoid
confusing/shadowing of packages.

# A Simple Example #
TestBase.scala:
```scala
import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.prop.PropertyChecks
import com.socrata.testcommon.ResponseSugar

// scalastyle:off import.grouping
trait TestBase
    extends FunSuite
    with org.scalatest.MustMatchers
    with ResponseSugar
    with PropertyChecks {
    // Useful functions to be shared with all of your test classes.
}
```

UnusedSugar.scala:
```scala
import scala.language.implicitConversions

import com.socrata.testcommon.UnusedSugarCommon

trait UnusedSugar extends UnusedSugarCommon {
  // Custom Conversions.
  // eg. implicit def unusedToFoo(u: UnusedValue): Foo = Foo.empty
}
```

VersionServiceTest.scala:
```scala
import com.socrata.http.server.responses._

class VersionServiceTest extends TestBase with UnusedSugar {
  test("Endpoint must return health = alive") {
    val resp = unpackResponse(VersionService.get(Unused))

    resp.status must equal (OK.statusCode)
    resp.contentType must equal ("application/json; charset=UTF-8")
    resp.body.toLowStr must include ("health")
    resp.body.toLowStr must include ("alive")
  }
}
```
