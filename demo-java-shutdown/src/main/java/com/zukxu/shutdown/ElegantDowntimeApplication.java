package com.zukxu.shutdown;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * <p>
 *
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/16 0016 14:46
 */
@SpringBootApplication
public class ElegantDowntimeApplication {
	public static void main(String[] args) {

		/* method 4: exit this application using static method */
		ConfigurableApplicationContext ctx = SpringApplication.run(ElegantDowntimeApplication.class, args);
		exitApplication(ctx);

		/* method 3 : generate a pid in a specified path, while use command to shutdown pid :
            'cat /Users/demo/app.pid | xargs kill' */
	/*	SpringApplication application = new SpringApplication(ElegantDowntimeApplication.class);
		application.addListeners(new ApplicationPidFileWriter("/Users/demo/app.pid"));
		application.run();*/

		//method2
		/*ConfigurableApplicationContext ctx = SpringApplication.run(ElegantDowntimeApplication.class, args);

		try {
			System.out.println("10s 后自动停止");
			TimeUnit.SECONDS.sleep(10);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ctx.close();*/

		//method1
		// SpringApplication.run(ElegantDowntimeApplication.class);
	}

	public static void exitApplication(ConfigurableApplicationContext context) {
		int exitCode = SpringApplication.exit(context, (ExitCodeGenerator) () -> 0);

		System.exit(exitCode);
	}
}
