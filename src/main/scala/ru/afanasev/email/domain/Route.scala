package ru.afanasev.email.domain

import org.http4s._
import cats.effect._

import Authentication._

abstract class Route[F[_]: Async] {

  val dsl = org.http4s.dsl.Http4sDsl.apply[F]

  def request: AuthedRoutes[User, F]
}
