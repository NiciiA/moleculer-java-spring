package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import services.moleculer.ServiceBroker;
import services.moleculer.config.ServiceBrokerConfig;
import services.moleculer.config.SpringRegistrator;
import services.moleculer.transporter.NatsTransporter;

@SpringBootApplication
public class DemoApplication {

	@Bean(initMethod = "start", destroyMethod = "stop")
	public ServiceBroker getServiceBroker() {
		ServiceBrokerConfig cfg = new ServiceBrokerConfig();

		cfg.setJsonReaders("jackson");
		cfg.setJsonWriters("jackson");

		cfg.setMetricsEnabled(true);

		cfg.setTransporter(new NatsTransporter("nats://localhost:4222"));

		return new ServiceBroker(cfg);
	}

	@Bean
	public SpringRegistrator getSpringRegistrator() {
		return new SpringRegistrator();
	}

}
