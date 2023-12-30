package com.example.Backend.Services;

import com.example.Backend.Model.Adopter;
import com.example.Backend.Model.Application;
import com.example.Backend.Model.Pet;
import com.example.Backend.Repositories.AdopterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final AdopterRepository adopterRepository;

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

    public void notifyStatusUpdate(Application application) {
        // Create a Thymeleaf context
        Context context = new Context();
        context.setVariable("name", application.getAdopter().getFirstName());
        context.setVariable("appID", application.getId());
        String template = application.getStatus().equals("Approved") ? "notifyApprove" : "notifyReject";
        String body = templateEngine.process(template, context);
        String to = application.getAdopter().getEmail();
        String subject = "Application Status Update";
        try {
            emailService.sendEmail(to, subject, body);
        } catch (Exception e) {
            System.out.println("Error sending email");
        }
    }

    public void notifyAllUsers(Pet pet) {
        Context context = new Context();
        context.setVariable("petID", pet.getId());
        context.setVariable("petName", pet.getName());
        context.setVariable("shelterName", pet.getShelter().getName());
        context.setVariable("contact", pet.getShelter().getContactPhone());
        context.setVariable("shelterEmail", pet.getShelter().getContactEmail());
        context.setVariable("location", pet.getShelter().getLocation());
        List<Adopter> adopters = adopterRepository.findAll();
        for (Adopter adopter : adopters) {
            context.setVariable("name", adopter.getFirstName());
            String body = templateEngine.process("notifyAll", context);
            String subject = "New Pet Added";
            try {
                emailService.sendEmail(adopter.getEmail(), subject, body);
            } catch (Exception e) {
                System.out.println("Error sending email");
            }
        }
    }
}
