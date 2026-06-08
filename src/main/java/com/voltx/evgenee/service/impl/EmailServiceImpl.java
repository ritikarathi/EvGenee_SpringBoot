package com.voltx.evgenee.service.impl;

import com.voltx.evgenee.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendEmail(String to, String subject, String title, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("\"EvGenee\" <" + fromEmail + ">");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(generateEmailTemplate(title, htmlContent), true);
            mailSender.send(message);
            log.info("[EmailService] Email sent to: {}", to);
        } catch (Exception e) {
            log.error("[EmailService] Failed to send email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Email send failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String buildReminderEmailContent(String userName, String startTime, String vehicleNumber) {
        String vehicle = (vehicleNumber != null && !vehicleNumber.isBlank()) ? vehicleNumber : "N/A";
        return "<p>Hello <span class=\"highlight\">" + userName + "</span>,</p>" +
               "<p>Your scheduled charging session starts in <span class=\"highlight\">15 minutes</span>. We've reserved the station for you.</p>" +
               "<div class=\"otp-box\" style=\"text-align: left;\">" +
               "<div style=\"margin-bottom: 15px;\">" +
               "<p style=\"margin: 0; color: #94a3b8; font-size: 11px; text-transform: uppercase; letter-spacing: 1px;\">Start Time</p>" +
               "<p style=\"margin: 5px 0 0 0; font-weight: 700; color: #ffffff; font-size: 18px;\">" + startTime + "</p>" +
               "</div>" +
               "<div>" +
               "<p style=\"margin: 0; color: #94a3b8; font-size: 11px; text-transform: uppercase; letter-spacing: 1px;\">Vehicle Number</p>" +
               "<p style=\"margin: 5px 0 0 0; font-weight: 700; color: #ffffff; font-size: 18px;\">" + vehicle + "</p>" +
               "</div>" +
               "</div>" +
               "<p>Please arrive at the station a few minutes early to ensure a smooth plug-in experience.</p>" +
               "<p style=\"margin-top: 30px;\">Drive safe,<br><span class=\"highlight\">The EvGenee Team</span></p>";
    }

    private String generateEmailTemplate(String title, String content) {
        int year = java.time.LocalDate.now().getYear();
        return "<!DOCTYPE html>" +
               "<html lang=\"en\">" +
               "<head>" +
               "<meta charset=\"UTF-8\">" +
               "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
               "<title>EvGenee</title>" +
               "<style>" +
               "@import url('https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;700&display=swap');" +
               "body { margin: 0; padding: 0; font-family: 'DM Sans', Arial, sans-serif; background-color: #000814; color: #e2e8f0; }" +
               ".wrapper { width: 100%; background-color: #000814; padding: 40px 0; }" +
               ".main { background-color: #0a1122; margin: 0 auto; width: 100%; max-width: 600px; border-radius: 24px; overflow: hidden; border: 1px solid rgba(255,255,255,0.08); box-shadow: 0 20px 50px rgba(0,0,0,0.5); }" +
               ".header { background: linear-gradient(135deg, #0a1122 0%, #000814 100%); padding: 40px 30px; text-align: center; border-bottom: 1px solid rgba(255,255,255,0.05); }" +
               ".logo-text { color: #ffffff; font-size: 26px; font-weight: 800; letter-spacing: -0.5px; margin: 0; }" +
               ".logo-accent { color: #10b981; }" +
               ".content { padding: 40px; line-height: 1.6; }" +
               ".title { font-size: 24px; font-weight: 700; color: #ffffff; margin-bottom: 24px; text-align: center; }" +
               ".body-text { color: #94a3b8; font-size: 16px; margin-bottom: 24px; }" +
               ".otp-box { background: rgba(16,185,129,0.05); border: 1px solid rgba(16,185,129,0.2); border-radius: 16px; padding: 30px; text-align: center; margin: 30px 0; }" +
               ".otp-code { font-size: 42px; font-weight: 800; color: #10b981; letter-spacing: 10px; margin: 0; }" +
               ".footer { background-color: #000814; padding: 40px 30px; text-align: center; border-top: 1px solid rgba(255,255,255,0.05); }" +
               ".footer-text { font-size: 12px; color: #475569; margin-bottom: 8px; font-weight: 500; }" +
               ".highlight { color: #10b981; font-weight: 600; }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<div class=\"wrapper\">" +
               "<table class=\"main\" align=\"center\">" +
               "<tr><td class=\"header\"><div class=\"logo-text\">Ev<span class=\"logo-accent\">Genee</span></div></td></tr>" +
               "<tr><td class=\"content\">" +
               "<h2 class=\"title\">" + title + "</h2>" +
               "<div class=\"body-text\">" + content + "</div>" +
               "</td></tr>" +
               "<tr><td class=\"footer\">" +
               "<p class=\"footer-text\">&copy; " + year + " EvGenee Network Pvt. Ltd.</p>" +
               "<p class=\"footer-text\">The premium EV charging infrastructure of India.</p>" +
               "</td></tr>" +
               "</table>" +
               "</div>" +
               "</body></html>";
    }
}
