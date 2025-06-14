package academy.api;

import academy.quotes.messages.CarDetailsMessage;
import academy.quotes.producer.PublishFanOutPriceQuotes;
import io.javalin.http.Handler;

import static academy.api.HandlerHelpers.*;


public class FanOutQuotesController {

    public static Handler getQuotes = ctx -> {

        try {
            var message = ctx.bodyAsClass(CarDetailsMessage.class);

            var publish = new PublishFanOutPriceQuotes(Application.connection);

            if(publish.publishMessage(message)) {
                handleSuccess(ctx);
            } else {
                handleFailure(ctx);
            }


        } catch (Exception e) {
            handleError(ctx, e);
        }

    };


}