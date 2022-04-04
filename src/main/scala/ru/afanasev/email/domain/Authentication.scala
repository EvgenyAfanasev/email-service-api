package ru.afanasev.email.domain

import cats.data._
import cats.implicits._
import cats.effect._
import org.typelevel.ci._

import org.http4s.server._
import org.http4s._

import ru.afanasev.email.config.Properties._

import java.util._

class Authentication[F[_]: Sync](
    properties: SecurityProperties
) {

  import Authentication._

  type Auth[A] = OptionT[F, A]

  private def authUser: Kleisli[Auth, Request[F], User] = Kleisli { reqest =>
    OptionT.fromOption[F](
      for {
        header <- reqest.headers.get(ci"Authorization").map(_.head)
        user   <- authneticate(header.value)
      } yield user
    )
  }

  private def authneticate(token: String) = {
    val usernamePassword = new String(
      Base64.getDecoder().decode(token)
    ).split(":")
    if (usernamePassword(0) == properties.username
        && usernamePassword(1) == properties.password)
      User(usernamePassword(0), usernamePassword(1)).some
    else None
  }

  val authMiddleware = AuthMiddleware(authUser)
}

object Authentication {

    case class User(username: String, password: String)

    def apply[F[_]: Sync](
        properties: SecurityProperties
    ) = new Authentication[F](properties)
}