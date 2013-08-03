name := "EmailTemplates"

version := "0.1.0"

scalaVersion := "2.10.2"

organization := "ar.com.caeldev"

libraryDependencies ++= {
  	Seq(
		    "org.specs2" %% "specs2" % "1.14" % "test",
    		"org.scalatest" %% "scalatest" % "1.9.1" % "test"
  	)
}

resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
                "releases"        at "http://oss.sonatype.org/content/repositories/releases",
                "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
                "Typesafe Snaps Repo" at "http://repo.typesafe.com/typesafe/snapshots/"
                )
 
scalacOptions ++= Seq("-unchecked", "-deprecation")

logLevel := Level.Info


