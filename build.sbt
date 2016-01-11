name := "socrata-test-common"

scalaVersion := "2.11.7"
crossScalaVersions := Seq("2.10.4", scalaVersion.value)

scalacOptions ++= Seq("-optimize", "-Ywarn-dead-code")

libraryDependencies ++= Seq(
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
