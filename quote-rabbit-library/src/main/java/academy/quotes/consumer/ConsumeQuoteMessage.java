package academy.quotes.consumer;


import academy.quotes.constants.Constants;
import com.rabbitmq.client.*;

import java.io.IOException;

public class ConsumeQuoteMessage {
    private final Channel channel;
    private final String queueName;

    public ConsumeQuoteMessage(Connection connection) {
        try {
            channel = connection.createChannel();
            queueName = channel.queueDeclare().getQueue();
            //if the exchange is not declared yet,
            //calling many times exchangeDeclare method will not affect anything
            channel.exchangeDeclare(Constants.QuotesExchange, BuiltinExchangeType.FANOUT);

            channel.queueBind(queueName, Constants.QuotesExchange, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void consumeQuoteMessage(DeliverCallback deliverCallback) {
        try {
            channel.basicConsume(queueName, true, deliverCallback, (CancelCallback) null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
