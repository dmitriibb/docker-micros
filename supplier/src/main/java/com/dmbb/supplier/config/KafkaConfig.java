package com.dmbb.supplier.config;

import com.dmbb.supplier.constants.Constants;
import com.dmbb.supplier.model.kafka.FoodSupplyDTO;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    // consume food supply order
    @Bean
    public ConsumerFactory<String, FoodSupplyDTO> foodSupplyConsumerFactory() {
        return new DefaultKafkaConsumerFactory(baseConsumerProps());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FoodSupplyDTO> foodSupplyKafkaListenerContainerFactory(ConsumerFactory<String, FoodSupplyDTO> foodSupplyConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, FoodSupplyDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(foodSupplyConsumerFactory);
        return factory;
    }

    //produce food supply
    @Bean
    public NewTopic foodSupplyTopic() {
        return new NewTopic(Constants.KAFKA_TOPIC_SUPPLY_FOOD, 1, (short) 1);
    }

    @Bean
    public ProducerFactory<String, FoodSupplyDTO> foodSupplyProducerFactory() {
        return new DefaultKafkaProducerFactory<>(baseProducerProps());
    }

    @Bean
    public KafkaTemplate<String, FoodSupplyDTO> foodSupplyKafkaTemplate(ProducerFactory<String, FoodSupplyDTO> foodSupplyProducerFactory) {
        return new KafkaTemplate<>(foodSupplyProducerFactory);
    }

    private Map<String, Object> baseProducerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    private Map<String, Object> baseConsumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.KAFKA_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }

}
