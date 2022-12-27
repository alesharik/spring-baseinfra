package com.alesharik.common.mail;

import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.MimeMessageHelper;

@FunctionalInterface
public interface MimeMessageHelperConsumer {
    void accept(MimeMessageHelper helper) throws MessagingException;
}
