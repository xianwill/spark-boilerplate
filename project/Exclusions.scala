import sbt._

object Exclusions {
  lazy val all = Seq(
    ExclusionRule(name = "log4j"),
    ExclusionRule(name = "slf4j-log4j12")
  )
}