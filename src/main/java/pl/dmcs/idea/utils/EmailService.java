package pl.dmcs.idea.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendRegistrationMail(String mail, String token, String url, String path) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(mail);
        helper.setSubject("IDEA");
        String link = url.substring(0, url.length() - path.length()).concat("/confirm?token=");
        helper.setText("<a href=\"" + link + token + "\">" + "link" + "</a>", true);
        mailSender.send(message);
    }
}
