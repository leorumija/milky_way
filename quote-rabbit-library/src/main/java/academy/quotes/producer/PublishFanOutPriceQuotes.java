package academy.quotes.producer;

import academy.quotes.constants.Constants;
import academy.quotes.messages.CarDetailsMessage;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static academy.quotes.JsonHelpers.toJson;
import static academy.quotes.constants.Constants.QuotesExchange;

public class PublishFanOutPriceQuotes {

    private Connection connection;

    public PublishFanOutPriceQuotes(Connection connection) {
        this.connection = connection;
    }

    public boolean publishMessage(CarDetailsMessage message) {
            try(Channel channel = connection.createChannel()) {
                byte [] jsonFormatMsg = toJson(message);
                channel.exchangeDeclare(QuotesExchange, BuiltinExchangeType.FANOUT);
                channel.basicPublish(QuotesExchange, "", null, jsonFormatMsg);
                System.out.println(" [x] Published '" + message + "'");
            } catch (IOException | TimeoutException e) {
                System.out.println(" [x] " + e.getMessage());
                return false;
            }
            return true;
    }
}
