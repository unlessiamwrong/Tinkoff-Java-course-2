package edu.java.kafka;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.dto.requests.LinkUpdateRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
public class KafkaConsumerTest {

    protected static KafkaContainer kafkaOne;

    static {
        kafkaOne =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));
        kafkaOne.start();
    }

    @MockBean
    private TelegramBot bot;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaOne::getBootstrapServers);
    }

    protected Map<String, Object> getProducerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaOne.getBootstrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "id");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Test
    void whenListenUpdate_ReceivedMessageIsNotEmpty() {
        try (KafkaProducer<String, LinkUpdateRequest> producer = new KafkaProducer<>(getProducerProps())) {
            LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(1L, null, null, null);
            ProducerRecord<String, LinkUpdateRequest> record = new ProducerRecord<>("update", linkUpdateRequest);
            producer.send(record);

            verify(bot).execute(any());

        }
    }
}
