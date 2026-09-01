package com.bookflow.email.entity;

public record EmailVerificationMessage(
        String email,
        String code) {

}
