package com.alesharik.common.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.lang.NonNull;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Email service
 */
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender sender;
    private final MailProperties properties;
    private final EmailProperties emailProperties;

    /**
     * Compose and send simple message. <code>To</code>, <code>Subject</code> and other fields should be set in customizer
     * @param customizer simple message customizer
     */
    public void composeAndSendSimple(@NonNull Consumer<SimpleMailMessage> customizer) {
        var message = new SimpleMailMessage();
        customizer.accept(message);
        message.setFrom(emailProperties.getFrom() == null ? properties.getUsername() : emailProperties.getFrom());
        sender.send(message);
    }

    /**
     * Compose and send mime message. <code>To</code>, <code>Subject</code> and other fields should be set in customizer
     * @param customizer mime message customizer
     */
    public void composeAndSend(@NonNull MimeMessageHelperConsumer customizer) {
        var message = sender.createMimeMessage();
        try {
            var helper = new MimeMessageHelper(message, true, "UTF-8");
            customizer.accept(helper);
            var from = emailProperties.getFrom() == null ? properties.getUsername() : emailProperties.getFrom();
            if (emailProperties.getSenderName() != null) {
                helper.setFrom(from, emailProperties.getSenderName());
            } else {
                helper.setFrom(from);
            }
            sender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new MailSendException(Map.of(message, e));
        }
    }
}
