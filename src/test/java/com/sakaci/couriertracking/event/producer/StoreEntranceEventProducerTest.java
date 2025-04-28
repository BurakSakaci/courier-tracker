package com.sakaci.couriertracking.event.producer;

import com.sakaci.couriertracking.event.StoreEntranceEvent;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreEntranceEventProducerTest {
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;
    @InjectMocks
    private StoreEntranceEventProducer producer;

    @Test
    void sendStoreEntranceEvent_sendsToKafka() {
        StoreEntranceEvent event = Instancio.create(StoreEntranceEvent.class);
        ReflectionTestUtils.setField(producer, "storeEntrancesTopic", "test");
        producer.sendStoreEntranceEvent(event);
        verify(kafkaTemplate, times(1)).send(eq("test"), eq(event.getCourierId()), eq(event));
    }
}
