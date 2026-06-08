package com.voltx.evgenee.service;

public interface EmailService {
    void sendEmail(String to, String subject, String title, String htmlContent);
    String buildReminderEmailContent(String userName, String startTime, String vehicleNumber);
}
