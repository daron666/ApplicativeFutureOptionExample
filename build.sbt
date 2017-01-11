name := "ApplicativeFutureOptionExample"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies += "org.typelevel" %% "cats" % "0.8.1"

scalacOptions ++= Seq(
  "-deprecation"
  , "-encoding", "UTF-8"
  , "-feature"
  , "-language:postfixOps"
)
    