name := "socrata-test-common"
organization := "com.socrata"

externalResolvers := Seq("Socrata Artifactory Libs Release" at "https://repo.socrata.com/artifactory/libs-release/")

scalaVersion := "2.11.7"
crossScalaVersions := Seq("2.10.4", scalaVersion.value)

scalacOptions ++= Seq("-optimize", "-Ywarn-dead-code")

libraryDependencies ++= Seq(
  "org.json4s"    %% "json4s-jackson"      % "[3.3.0, 4.0.0)",
  "org.scalatest" %% "scalatest"           % "[2.2.4, 3.0.0)",
  "com.rojoma"  %% "simple-arm-v2"         % "[2.1.0, 3.0.0)" % "optional",
  "com.socrata" %% "socrata-curator-utils" % "[1.0.1, 2.0.0)" % "optional",
  "com.socrata" %% "socrata-http-client"   % "[3.5.0, 4.0.0)" % "optional",
  "com.socrata" %% "socrata-http-jetty"    % "[3.5.0, 4.0.0)" % "optional",
  "com.socrata" %% "socrata-http-server"   % "[3.5.0, 4.0.0)" % "optional"
)
val TestOptionNoTraces = "-oD"
val TestOptionShortTraces = "-oDS"
val TestOptionFullTraces = "-oDF"

com.socrata.sbtplugins.findbugs.JavaFindBugsPlugin.JavaFindBugsKeys.findbugsFailOnError in Compile := false

com.socrata.sbtplugins.findbugs.JavaFindBugsPlugin.JavaFindBugsKeys.findbugsFailOnError in Test := false

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}
