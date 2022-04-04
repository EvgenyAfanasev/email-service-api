package ru.afanasev.email.domain.email.model

final case class EmailRequestDto(
    to: String,
    subject: String,
    text: String,
    textHtml: String
)
