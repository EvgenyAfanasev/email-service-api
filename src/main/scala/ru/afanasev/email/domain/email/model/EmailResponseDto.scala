package ru.afanasev.email.domain.email.model

trait EmailResponseDto

final case class EmailSuccessResponseDto(
    message: String
) extends EmailResponseDto

final case class EmailFailedResponseDto(
    message: String
) extends EmailResponseDto
