package br.com.projeto.appointmentbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProjectAppointmentBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectAppointmentBookApplication.class, args);
    }

}
