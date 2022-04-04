package ru.afanasev.email.domain.email

import cats.implicits._
import cats.effect._

import emil._

import org.http4s._
import org.http4s.circe._
import org.http4s.circe.CirceEntityCodec._

import io.circe.generic.auto._
import io.circe.syntax._

import ru.afanasev.email.domain.Route
import ru.afanasev.email.domain.Authentication._
import ru.afanasev.email.domain.email.model._

class EmailRoute[F[+_]: Async, C <: Connection](
    emailService: EmailService[F, C]
) extends Route[F] {

  import dsl._

  def request = AuthedRoutes.of[User, F] {
    case authReq @ POST -> Root / "email" as user =>
      for {
        email  <- authReq.req.as[EmailRequestDto]
        result <- emailService.send(email, user.username)
        response <- result match {
          case result: EmailSuccessResponseDto => Ok(result)
          case error: EmailFailedResponseDto   => Conflict(error)
        }
      } yield response
  }
}

object EmailRoute {
  def apply[F[+_]: Async, C <: Connection](
      emailService: EmailService[F, C]
  ) = new EmailRoute[F, C](emailService)
}
