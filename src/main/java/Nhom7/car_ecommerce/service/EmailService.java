package Nhom7.car_ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	public void sendVerifycationOtpEmail(String email,String newPassword) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
		String subject = "Verify Otp";
		String text = "Your new password is "+newPassword;
		
		helper.setSubject(subject);
		helper.setText(text);
		helper.setTo(email);
		try {
			javaMailSender.send(mimeMessage);
		} catch (MailException e) {
			// TODO: handle exception
			throw new MailSendException(e.getMessage());
		}
	}
}
