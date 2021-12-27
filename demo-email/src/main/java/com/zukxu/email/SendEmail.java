package com.zukxu.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * <p>
 * 发送邮件
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/2 0002 17:30
 */
@Component
public class SendEmail {
	private final JavaMailSender mailSender;

	/**
	 * 发送者
	 */
	@Value("${spring.mail.username}")
	private String sender;

	/**
	 * 接受者
	 */
	@Value("${she.mail}")
	private String receive;

	public SendEmail(JavaMailSender mailSender) {this.mailSender = mailSender;}

	/**
	 * @param subject 邮件标题
	 * @param message 邮件内容
	 */
	public void sendMessage(String subject, String message) {

		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			//发送者邮件邮箱
			helper.setFrom(sender);
			//收邮件者邮箱
			helper.setTo(receive);
			//发件主题
			helper.setSubject(subject);
			//发件内容
			helper.setText(message);
			//发送邮件
			mailSender.send(helper.getMimeMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
