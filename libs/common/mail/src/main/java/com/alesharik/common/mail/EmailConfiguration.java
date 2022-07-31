package com.alesharik.common.mail;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@AutoConfiguration
@EnableConfigurationProperties(EmailProperties.class)
public class EmailConfiguration {
    @Bean
    public EmailService emailService(JavaMailSender javaMailSender, MailProperties properties, EmailProperties emailProperties) {
        return new EmailService(javaMailSender, properties, emailProperties);
    }

    @Bean
    @ConditionalOnClass(SpringTemplateEngine.class)
    public TemplatedEmailService templatedEmailService(EmailService emailService) {
        var engine = new SpringTemplateEngine();

        var resolver = new ClassLoaderTemplateResolver();
        resolver.setSuffix(".html");
        resolver.setPrefix("emails/");
        resolver.setTemplateMode(TemplateMode.HTML);
        engine.addTemplateResolver(resolver);

        return new TemplatedEmailService(engine, emailService);
    }
}
