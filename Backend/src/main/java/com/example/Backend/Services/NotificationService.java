package com.example.Backend.Services;

import com.example.Backend.Model.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final EmailService emailService;
    private final TemplateEngine templateEngine;

    @Async
    public void notifyAdopter(Application application) {
        // Create a Thymeleaf context
        Context context = new Context();
        context.setVariable("name", application.getAdopter().getFirstName());
        context.setVariable("appID", application.getId());
        context.setVariable("email", application.getAdopter().getEmail());
        context.setVariable("petID", application.getPet().getId());
        context.setVariable("petName", application.getPet().getName());
        context.setVariable("shelterName", application.getShelter().getName());
        context.setVariable("contact", application.getShelter().getContactPhone());
        context.setVariable("shelterEmail", application.getShelter().getContactEmail());
        context.setVariable("location", application.getShelter().getLocation());
        context.setVariable("date", application.getDate());
        context.setVariable("status", application.getStatus());
        String body = templateEngine.process("notifyAdopter", context);
        String to = application.getAdopter().getEmail();
        String subject = "Application Status Update";
        try {
            emailService.sendEmail(to, subject, body);
        } catch (Exception e) {
            System.out.println("Error sending email");
        }
    }
}
