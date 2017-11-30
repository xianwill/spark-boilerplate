import sbt._

object Exclusions {
  lazy val all = Seq(
    ExclusionRule(organization = "log4j", name = "log4j"),
    ExclusionRule(organization = "slf4j-log4j12", name = "slf4j-log4j12")
  )
}
