package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("springboot.repository")
@EntityScan("springboot.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class MasterApplication {
    public static void main(String[] args) {
        SpringApplication.run(MasterApplication.class, args);
    }

}
