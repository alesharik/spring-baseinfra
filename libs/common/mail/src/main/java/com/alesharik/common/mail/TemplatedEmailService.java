package com.alesharik.common.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Email service with Thymeleaf template engine connected
 */
@RequiredArgsConstructor
public class TemplatedEmailService {
    private final SpringTemplateEngine springTemplateEngine;
    private final EmailService mailService;

    /**
     * Compose message from template and send
     * @param view message view/template name
     * @param context view context
     * @param customizer message customizer. <code>To</code>, <code>Subject</code> and other fields should be set in here
     */
    public void composeTemplateAndSend(@NonNull String view, @NonNull Context context, @NonNull MimeMessageHelperConsumer customizer) {
        mailService.composeAndSend(helper -> {
            var processedHtml = springTemplateEngine.process(view, context);
            helper.setText(processedHtml, true);
            customizer.accept(helper);
        });
    }
}
