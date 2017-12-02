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
    libraryDependencies ++= List(
      sparkCore,
      sparkSql,
      scallop,
      cassConnector,
      elasticSearchSpark,
      awsSdk,
      hadoopAws,
      json4sNative,
      commonsCsv,
      httpclient,
      scalaTest,
      scalaLogging
    )
    //libraryDependencies ~= (_.map(excludeLog4j))
  )
  .configs( IntegrationTest )
  .settings( inConfig(IntegrationTest)(Defaults.testTasks) : _*)
  .settings(
    testOptions in Test := Seq(Tests.Filter(unitFilter)),
    testOptions in IntegrationTest := Seq(Tests.Filter(itFilter))
  )


assemblyShadeRules in assembly := Seq(
  ShadeRule.rename("org.apache.http.**" -> "shadehttp.@1").inAll
)

def itFilter(name: String): Boolean = name endsWith "IntegrationSpec"
def unitFilter(name: String): Boolean = (name endsWith "Spec") && !itFilter(name)

lazy val IntegrationTest: Configuration = config("it") extend Test

fork in run := true
javaOptions in run ++= Seq(
  "-Dlog4j.debug=true",
  "-Dlog4j.configuration=log4j.properties")
outputStrategy := Some(StdoutOutput)

run in Compile := Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run)).evaluated
