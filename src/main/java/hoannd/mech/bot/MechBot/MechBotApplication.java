package hoannd.mech.bot.MechBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "hoannd.mech.bot.MechBot.repository")
@EntityScan(basePackages = "hoannd.mech.bot.MechBot.model")
@SpringBootApplication

public class MechBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MechBotApplication.class, args);
	}

}
