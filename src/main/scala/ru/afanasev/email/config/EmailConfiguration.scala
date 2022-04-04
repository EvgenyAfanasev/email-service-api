package ru.afanasev.email.config

import cats.implicits._
import cats.effect._

import emil._
import emil.javamail._

import Properties._

object EmailConfiguration {

  def configSmtp[F[_]: Async](properties: SmtpProperties) =
    MailConfig(
      url = s"smtp://${properties.host}:${properties.port}",
      user = properties.username,
      password = properties.password,
      sslType = SSLType.StartTLS
    )

}
