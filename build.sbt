name := "csrftest"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  filters
)     

play.Project.playScalaSettings
