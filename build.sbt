name := "ApplicativeFutureOptionExample"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies += "org.typelevel" %% "cats" % "0.9.0"

libraryDependencies += "io.monix" %% "monix" % "2.3.0"

libraryDependencies += "io.monix" %% "monix-cats" % "2.3.0"

scalacOptions ++= Seq(
  "-deprecation"
  , "-encoding", "UTF-8"
  , "-feature"
  , "-language:postfixOps"
)
    