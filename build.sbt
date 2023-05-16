name := "socrata-test-common"
organization := "com.socrata"

externalResolvers := Seq("Socrata Artifactory Libs Release" at "https://repo.socrata.com/artifactory/libs-release/")

scalaVersion := "2.12.17"
crossScalaVersions := Seq("2.10.4", "2.11.7", scalaVersion.value)

scalacOptions ++= Seq("-optimize", "-Ywarn-dead-code")

libraryDependencies ++= Seq(
  "org.json4s"    %% "json4s-jackson"      % "3.6.11",
  "org.scalatest" %% "scalatest"           % "3.0.0",
  "com.rojoma"  %% "simple-arm-v2"         % "2.1.0" % "optional",
  "com.socrata" %% "socrata-curator-utils" % "1.2.0" % "optional",
  "com.socrata" %% "socrata-http-client"   % "3.16.0" % "optional",
  "com.socrata" %% "socrata-http-jetty"    % "3.16.0" % "optional",
  "com.socrata" %% "socrata-http-server"   % "3.16.0" % "optional"
)
val TestOptionNoTraces = "-oD"
val TestOptionShortTraces = "-oDS"
val TestOptionFullTraces = "-oDF"
