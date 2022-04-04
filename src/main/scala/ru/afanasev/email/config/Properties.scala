package ru.afanasev.email.config

import pureconfig._
import pureconfig.generic.auto._

object Properties {

  def loadProperties =
    ConfigSource.default
      .loadOrThrow[ApplicationProperties]

  case class ApplicationProperties(
      smtp: SmtpProperties,
      server: ServerProperties,
      security: SecurityProperties
  )

  case class ServerProperties(
      port: Int
  )

  case class SecurityProperties(
      username: String,
      password: String
  )

  case class SmtpProperties(
      host: String,
      port: String,
      username: String,
      password: String
  )
}
