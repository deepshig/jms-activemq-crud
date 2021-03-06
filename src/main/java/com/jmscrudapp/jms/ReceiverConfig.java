package com.jmscruddapp.jms;

import com.jmscruddapp.Client;
import com.jmscruddapp.JmsServer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
@EnableJms
public class ReceiverConfig {

  @Value("${activemq.broker-url}")
  private String brokerUrl;

  @Bean
  public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
    ActiveMQConnectionFactory activeMQConnectionFactory =
        new ActiveMQConnectionFactory();
    activeMQConnectionFactory.setBrokerURL(brokerUrl);

    return activeMQConnectionFactory;
  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(receiverActiveMQConnectionFactory());
    factory.setErrorHandler(e -> {
      System.out.println("Error in listener : " + e);
    });
    return factory;
  }

  @Bean
  public Receiver receiver() {
    return new Receiver();
  }

  @Bean
  public JmsServer server() {
    return new JmsServer();
  }

  @Bean
  public Client client() {
    return new Client();
  }
}
