name := """silhouette-macwire"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).disablePlugins(PlayLayoutPlugin).configs(IntegrationTest)

Defaults.itSettings

import com.typesafe.sbt.SbtScalariform._
scalariformSettingsWithIt

scalaVersion in ThisBuild := "2.12.4"

libraryDependencies ++= Seq(
  jdbc,
  filters,
  ehcache,
  ws,
  specs2 % Test,
  specs2 % IntegrationTest,
  //"com.h2database" % "h2" % "1.4.191",
  //"io.getquill" %% "quill-jdbc" % "2.2.0",
  "io.getquill" %% "quill-async-mysql" % "2.2.0",
  "mysql" % "mysql-connector-java" % "8.0.8-dmr",// "5.1.36",
  //"org.flywaydb" %% "flyway-play" % "4.0.0",
  //"com.typesafe.play" %% "play-slick" % "3.0.0",
  "com.softwaremill.macwire" %% "macros" % "2.3.0",
  "com.mohiva" %% "play-silhouette" % "5.0.2",
  "com.mohiva" %% "play-silhouette-persistence" % "5.0.2",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "5.0.2",
  "com.mohiva" %% "play-silhouette-testkit" % "5.0.2" % "test",
  "com.iheart" %% "ficus" % "1.4.3",
  "org.webjars" %% "webjars-play" % "2.6.1",
  "com.adrianhurt" %% "play-bootstrap" % "1.2-P26-B3"
)

resolvers ++= Seq(
  Resolver.jcenterRepo,
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  "Atlassian Releases" at "https://maven.atlassian.com/public/"
)

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
