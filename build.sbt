import sbtassembly.AssemblyPlugin.defaultShellScript
import Dependencies._

val appName = IO.read(file("NAME"))
val fileVersion = IO.read(file("VERSION"))

lazy val commonSettings = Seq(
  organization := "com.example",
  scalaVersion := "2.11.8",
  version := fileVersion
)

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    name := appName,
    assemblyJarName in assembly := s"${name.value}-$fileVersion.jar",
    libraryDependencies ~= { _.map(_.excludeAll(Exclusions.all: _*)) },  
    libraryDependencies += sparkCore,
    libraryDependencies += sparkSql,
    libraryDependencies += slf4jApi,
    libraryDependencies += log4jOverSlf4j,
    libraryDependencies += logbackCore,
    libraryDependencies += logbackClassic,
    libraryDependencies += scallop,
    libraryDependencies += cassConnector,
    libraryDependencies += awsSdk,
    libraryDependencies += hadoopAws,
    libraryDependencies += json4sNative,
    libraryDependencies += commonsCsv,
    libraryDependencies += scalaTest
  )

run in Compile := Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run)).evaluated
