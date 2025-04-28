package com.sakaci.couriertracking.event.producer;

import com.sakaci.couriertracking.event.LocationUpdateEvent;
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
class LocationEventProducerTest {
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;
    @InjectMocks
    private LocationEventProducer producer;

    @Test
    void sendLocationUpdate_sendsToKafka() {
        LocationUpdateEvent event = Instancio.create(LocationUpdateEvent.class);
        ReflectionTestUtils.setField(producer, "locationUpdatesTopic", "test");
        producer.sendLocationUpdate(event);
        verify(kafkaTemplate, times(1)).send(eq("test"), eq(event.getCourierId()), eq(event));
    }
}
