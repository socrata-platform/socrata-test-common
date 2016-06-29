package com.socrata.testcommon

import com.fasterxml.jackson.core.JsonParseException
import org.json4s._
import org.json4s.jackson.JsonMethods.{parse, pretty, render}
import org.scalatest.Matchers._

import scala.util.Try

object JsonUtils {
  import scala.language.implicitConversions

  class AssertionJson(actual: => String) {
    def shouldBeJson(expected: String) = {
      val actualObj = Try(parse(actual)).recover({ case jpe: JsonParseException =>
                  fail(s"""Unable to parse actual value "$actual" as JSON""", jpe)}).get
      val expectObj = Try(parse(expected)).recover({ case jpe: JsonParseException =>
                        fail(s"""Unable to parse expected value "$expected" as JSON""", jpe)}).get

      withClue(
        "\nTextual actual:\n\n" + pretty(render(actualObj)) + "\n\n\n" +
        "Textual expected:\n\n" + pretty(render(expectObj)) + "\n\n")
        { actualObj should be (expectObj) }
    }
  }
  implicit def convertJsonAssertion(actual: => String): AssertionJson = new AssertionJson(actual)
}