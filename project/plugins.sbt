
organization := "com.socrata"

resolvers ++= Seq("Socarata SBT Repo" at "https://repo.socrata.com/artifactory/socrata-sbt-repo/",
                  "Socrata Artifactory" at "https://repo.socrata.com/artifactory/libs-release")

addSbtPlugin("com.socrata" % "socrata-sbt-plugins" %"1.6.8")
