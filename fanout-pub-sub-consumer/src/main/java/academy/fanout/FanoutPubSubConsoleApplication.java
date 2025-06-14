package academy.fanout;

import academy.quotes.consumer.ConsumeQuoteMessage;
import academy.quotes.consumer.ConsumeUserRegistration;
import academy.quotes.messages.CarDetailsMessage;
import academy.quotes.messages.UserRegistrationMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class FanoutPubSubConsoleApplication {
    static DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        var mapper = new ObjectMapper();
        var carDetailsMessage = mapper.readValue(message, CarDetailsMessage.class);
        System.out.println("Consumed in main : " + carDetailsMessage);
        System.out.println("consumerTag : " + consumerTag);
    };

    public static void main(String[] args) throws IOException, TimeoutException {
        var factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        ConsumeQuoteMessage consumeQuoteMessage = new ConsumeQuoteMessage(connection);

        consumeQuoteMessage.consumeQuoteMessage(deliverCallback);
        // consumeUserRegistration.pullMessage();
        //connection.close();
    }
}
