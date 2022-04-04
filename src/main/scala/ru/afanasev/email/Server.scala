package ru.afanasev.email

import cats.implicits._
import cats.effect._

import emil._
import emil.javamail._

import ru.afanasev.email.domain.Authentication
import ru.afanasev.email.domain.email._
import ru.afanasev.email.config.Properties._
import ru.afanasev.email.config.EmailConfiguration

import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router

import com.typesafe.scalalogging.LazyLogging

object Server extends IOApp with LazyLogging {

  override def run(args: List[String]): IO[ExitCode] =
    buildServer[IO].use(server => server.serve.compile.drain).as(ExitCode.Success)

  def buildServer[F[+_]: Async] =
    for {
      properties <- Resource.pure[F, ApplicationProperties](loadProperties)
      email      <- Resource.pure[F, MailConfig](EmailConfiguration.configSmtp(properties.smtp))
      client     <- Resource.pure[F, Emil[F]](JavaMailEmil[F]())
      authnetication = Authentication[F](properties.security)
      emailService   = EmailService[F, client.C](client(email))
      emailRoute     = EmailRoute[F, client.C](emailService)
      router         = Router("/" -> authnetication.authMiddleware(emailRoute.request)).orNotFound
    } yield BlazeServerBuilder[F]
      .bindHttp(port = properties.server.port)
      .withHttpApp(router)

}
