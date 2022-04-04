scalaVersion := "2.13.8"
name := "email-service-api"
organization := "ru.afanasev.email"
version := "1.0"

val mailVersion    = "0.11.2"
val configVersion  = "0.17.1"
val http4sVersion  = "1.0.0-M32"
val circeVersion   = "0.14.1"
val loggingVersion = "3.9.4"
val logbackVersion = "1.2.11"

libraryDependencies += "com.github.eikek"           %% "emil-common"         % mailVersion
libraryDependencies += "com.github.eikek"           %% "emil-javamail"       % mailVersion
libraryDependencies += "com.github.pureconfig"      %% "pureconfig"          % configVersion
libraryDependencies += "org.http4s"                 %% "http4s-dsl"          % http4sVersion
libraryDependencies += "org.http4s"                 %% "http4s-blaze-server" % http4sVersion
libraryDependencies += "org.http4s"                 %% "http4s-circe"        % http4sVersion
libraryDependencies += "io.circe"                   %% "circe-generic"       % circeVersion
libraryDependencies += "io.circe"                   %% "circe-literal"       % circeVersion
libraryDependencies += "ch.qos.logback"             % "logback-classic"      % logbackVersion
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging"       % loggingVersion

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}