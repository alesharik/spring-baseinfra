package com.alesharik.common.mail;

import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;

@FunctionalInterface
public interface MimeMessageHelperConsumer {
    void accept(MimeMessageHelper helper) throws MessagingException;
}
