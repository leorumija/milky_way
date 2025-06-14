package academy.pull_console;

import academy.quotes.consumer.ConsumeUserRegistration;
import academy.quotes.messages.UserRegistrationMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RegisterUserPollConsoleApplication {
    static DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody());
        var mapper = new ObjectMapper();
        var userRegistrationMessage = mapper.readValue(message, UserRegistrationMessage.class);
        System.out.println("Consumed in main : " + userRegistrationMessage);
    };

    public static void main(String[] args) throws IOException, TimeoutException {
        var factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        ConsumeUserRegistration consumeUserRegistration = new ConsumeUserRegistration(connection);
        consumeUserRegistration.pollQueue();
       // connection.close();
    }
}
