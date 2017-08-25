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
  .configs( IntegrationTest )
  .settings( inConfig(IntegrationTest)(Defaults.testTasks) : _*)
  .settings(
    testOptions in Test := Seq(Tests.Filter(unitFilter)),
    testOptions in IntegrationTest := Seq(Tests.Filter(itFilter))
  )

def itFilter(name: String): Boolean = name endsWith "IntegrationSpec"
def unitFilter(name: String): Boolean = (name endsWith "Spec") && !itFilter(name)

lazy val IntegrationTest: Configuration = config("it") extend Test
  

run in Compile := Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run)).evaluated
