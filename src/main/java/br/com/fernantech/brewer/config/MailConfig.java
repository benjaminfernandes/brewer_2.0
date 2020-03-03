package br.com.fernantech.brewer.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
	
	@Autowired
	private Environment env;

	@Bean
	public JavaMailSender mailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.guaira.pr.gov.br");
		mailSender.setPort(465);
		mailSender.setUsername(env.getProperty("brewer.mail.username"));
		mailSender.setPassword(env.getProperty("brewer.mail.password"));
		
		System.out.println(">>>>>>>>>>> " + env.getProperty("brewer.mail.username"));
		
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.debug", false);
		props.put("mail.smtp.ssl.enable", true);
		props.put("mail.smtp.connectiontimeout", 10000);
		
		mailSender.setJavaMailProperties(props);
		
		return mailSender;		
	}
}
