name := "EmailTemplates"

version := "0.1.0"

scalaVersion := "2.10.2"

organization := "ar.com.caeldev"

libraryDependencies ++= {
  	Seq(
		    "org.specs2" %% "specs2" % "1.14" % "test",
    		"org.scalatest" %% "scalatest" % "1.9.1" % "test",
            "ru.circumflex" % "circumflex-ftl" % "2.1" % "compile->default",
            "joda-time" % "joda-time" % "1.4",
            "org.jdom" % "jdom2" % "2.0.5",
            "org.mockito" % "mockito-all" % "1.9.5",
            "com.fasterxml.jackson.core" % "jackson-core" % "2.1.3",
            "com.fasterxml.jackson.core" % "jackson-databind" % "2.1.3",
            "com.fasterxml.jackson.jaxrs" % "jackson-jaxrs-json-provider" % "2.1.3",
            "com.fasterxml.jackson.module" % "jackson-module-scala_2.10" % "2.1.3",
            "net.sf.json-lib" % "json-lib" % "2.4" classifier "jdk15",
            "xom" % "xom" % "1.0",
            "org.fusesource.scalate" % "scalate-core_2.10" % "1.6.1"
  	)
}

resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
                "releases"        at "http://oss.sonatype.org/content/repositories/releases",
                "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
                "Typesafe Snaps Repo" at "http://repo.typesafe.com/typesafe/snapshots/"
                )
 
scalacOptions ++= Seq("-unchecked", "-deprecation")

logLevel := Level.Info


