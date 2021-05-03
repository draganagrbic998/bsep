package com.example.demo.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.example.demo.utils.Constants;

import lombok.AllArgsConstructor;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmailService {

	private final static String ACTIVATION_URL = Constants.FRONTEND + "/activate?q=%s";

	private final JavaMailSender emailSender;
	private final SpringTemplateEngine templateEngine;

	@Async
	public void sendActivationLink(String to, String firstName, String link) {
		try {
			MimeMessage message = this.emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			Context context = new Context();
			Map<String, Object> variables = Map.of("firstName", firstName, "link", String.format(ACTIVATION_URL, link));
			context.setVariables(variables);

			helper.setTo(to);
			helper.setFrom("bezbednost.ftn@gmail.com");
			helper.setSubject("Activation email - Bezbednost");
			helper.setText(this.templateEngine.process("activation-mail", context), true);
			this.emailSender.send(message);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Async
	public void sendInfoMail(String to, String certFileName, String secondVariable, String subject, String template) {
		try {
			MimeMessage message = this.emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			Context context = new Context();
			Map<String, Object> variables = Map.of("certFileName", certFileName, "secondVariable", secondVariable);
			context.setVariables(variables);

			helper.setTo(to);
			helper.setFrom("bezbednost.ftn@gmail.com");
			helper.setSubject(subject);
			helper.setText(this.templateEngine.process(template, context), true);
			this.emailSender.send(message);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
