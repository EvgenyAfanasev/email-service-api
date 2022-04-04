package ru.afanasev.email.domain.email

import cats.effect._
import cats.syntax.functor._
import cats.syntax.applicativeError._

import emil._
import emil.builder._

import ru.afanasev.email.domain.email.model._

class EmailService[F[+_]: Async, C <: Connection](
    sendWorker: Emil.Run[F, C],
) {

  def send(email: EmailRequestDto, user: String): F[EmailResponseDto] = {
    val mail = MailBuilder.build(
      From(user),
      To(email.to),
      Subject(email.subject),
      TextBody(email.text),
      HtmlBody(email.textHtml)
    )
    sendWorker
      .send(mail)
      .map(_ => EmailSuccessResponseDto("email was sent successful"): EmailResponseDto)
      .handleError(error => EmailFailedResponseDto(error.getMessage))
  }
}

object EmailService {
  def apply[F[+_]: Async, C <: Connection](
      sendWorker: Emil.Run[F, C],
  ) = new EmailService[F, C](sendWorker)
}
