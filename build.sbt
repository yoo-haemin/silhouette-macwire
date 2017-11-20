name := """silhouette-macwire"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).disablePlugins(PlayLayoutPlugin).configs(IntegrationTest)

Defaults.itSettings

import com.typesafe.sbt.SbtScalariform._
scalariformSettingsWithIt

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  filters,
  cache,
  ws,
  specs2 % Test,
  specs2 % IntegrationTest,
  "com.h2database" % "h2" % "1.4.191",
  "org.flywaydb" %% "flyway-play" % "3.0.0",
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "com.softwaremill.macwire" %% "macros" % "2.2.1",
  "com.mohiva" %% "play-silhouette" % "3.0.2",
  "com.mohiva" %% "play-silhouette-testkit" % "3.0.2" % "test",
  "net.ceedubs" %% "ficus" % "1.1.2",
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "com.adrianhurt" %% "play-bootstrap3" % "0.4.4-P24"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

parallelExecution in IntegrationTest := false
sourceDirectory in IntegrationTest <<= baseDirectory / "tests/it"
scalaSource in IntegrationTest <<= baseDirectory / "tests/it"
sourceDirectory in Test <<= baseDirectory / "tests/test"
scalaSource in Test <<= baseDirectory / "tests/test"

scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xlint", // Enable recommended additional warnings.
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
  "-Ywarn-nullary-override", // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
  "-Ywarn-numeric-widen" // Warn when numerics are widened.
)
