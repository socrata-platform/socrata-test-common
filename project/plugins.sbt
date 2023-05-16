externalResolvers ++= Seq("Socarata SBT Repo" at "https://repo.socrata.com/artifactory/socrata-sbt-repo/",
                          Resolver.url("Socrata", url("https://repo.socrata.com/artifactory/ivy-libs-release"))(Resolver.ivyStylePatterns))

