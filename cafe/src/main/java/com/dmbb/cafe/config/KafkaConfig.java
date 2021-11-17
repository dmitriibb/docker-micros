package com.dmbb.cafe.config;


import com.dmbb.cafe.model.kafka.SupplyFoodDTO;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static com.dmbb.cafe.constants.Constants.KAFKA_GROUP_ID;
import static com.dmbb.cafe.constants.Constants.KAFKA_TOPIC_SUPPLY_FOOD_ORDER;

@EnableKafka
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

    // supply food order produce
    @Bean
    public NewTopic topicSupplyFoodOrder() {
        return new NewTopic(KAFKA_TOPIC_SUPPLY_FOOD_ORDER, 1, (short) 1);
    }

    @Bean
    public ProducerFactory<String, SupplyFoodDTO> supplyFoodOrderProducerFactory() {
        Map<String, Object> props = baseProducerFactoryProps();
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, SupplyFoodDTO> supplyFoodOrderKafkaTemplate(ProducerFactory<String, SupplyFoodDTO> supplyFoodOrderProducerFactory) {
        return new KafkaTemplate<>(supplyFoodOrderProducerFactory);
    }

    // supply food consume
    @Bean
    public ConsumerFactory<String, SupplyFoodDTO> supplyFoodConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(baseConsumerFactoryProps(), new StringDeserializer(), new JsonDeserializer<>(SupplyFoodDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SupplyFoodDTO> kafkaListenerContainerFactorySupplyFood(ConsumerFactory<String, SupplyFoodDTO> supplyFoodConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, SupplyFoodDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(supplyFoodConsumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactoryString() {
        Map<String, Object> props = baseConsumerFactoryProps();
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactoryString(ConsumerFactory<String, String> consumerFactoryString) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryString);
        return factory;
    }

    private Map<String, Object> baseConsumerFactoryProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_GROUP_ID);
        return props;
    }

    private Map<String, Object> baseProducerFactoryProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return props;
    }

}
