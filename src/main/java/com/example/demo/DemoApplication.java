package com.example.demo;

import com.example.demo.middlewares.AuthorizationMiddleware;
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
		ServiceBrokerConfig config = new ServiceBrokerConfig();

        config.setJsonReaders("jackson");
        config.setJsonWriters("jackson");

        config.setMetricsEnabled(true);

        config.setTransporter(new NatsTransporter("nats://192.168.1.144:4222"));

        ServiceBroker broker = new ServiceBroker(config);
        broker.use(new AuthorizationMiddleware());

		return broker;
	}

	@Bean
	public SpringRegistrator getSpringRegistrator() {
		return new SpringRegistrator();
	}

}
