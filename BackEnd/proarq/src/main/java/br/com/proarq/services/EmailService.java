package br.com.proarq.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender; 
	
	@Value("${spring.mail.username}")
	private String de;
	
	public void send(String para, String assunto, String corpoEmail) throws Exception{
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
		
		messageHelper.setFrom(de);
		messageHelper.setTo(para);
		messageHelper.setSubject(assunto);
		messageHelper.setText(corpoEmail);
		
		javaMailSender.send(mimeMessage);
		
		
	}
	
}
