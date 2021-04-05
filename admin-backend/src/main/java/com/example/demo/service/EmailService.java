package com.example.demo.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.AllArgsConstructor;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailService {

	private final static String ACTIVATE_URL = "https://localhost:4200/activate?q=%s";

	private final JavaMailSender emailSender;
	private final SpringTemplateEngine templateEngine;


	@Async
	public void sendActivationLink(String to, String firstName, String link) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
		Context context = new Context();

		Map<String, Object> variables = Map.of("firstName", firstName, "link", String.format(ACTIVATE_URL, link));
		context.setVariables(variables);

		String html = this.templateEngine.process("activation-mail", context);
		helper.setTo(to);
		helper.setText(html, true);
		helper.setSubject("Activation email - Bezbednost");
		helper.setFrom("bezbednost.ftn@gmail.com");
		this.emailSender.send(message);
	}

	@Async
	public void sendInfoMail(String to, String certFileName, String secondVariable, String subject, String template) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
		Context context = new Context();

		Map<String, Object> variables = Map.of("certFileName", certFileName, "secondVariable", secondVariable);
		context.setVariables(variables);

		String html = this.templateEngine.process(template, context);
		helper.setTo(to);
		helper.setText(html, true);
		helper.setSubject(subject);
		helper.setFrom("bezbednost.ftn@gmail.com");
		this.emailSender.send(message);
	}

}
