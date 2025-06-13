package academy.api;

import academy.quotes.messages.UserRegistrationMessage;
import academy.quotes.producer.PublishRegisterUser;
import io.javalin.http.Handler;

import static academy.api.HandlerHelpers.*;


public class UserController {

    public static Handler registerUser = ctx -> {
        try{
            var message = ctx.bodyAsClass(UserRegistrationMessage.class);

            var publishRegisterUser = new PublishRegisterUser(Application.connection);

            if(publishRegisterUser.publishMessage(message)) {
                handleSuccess(ctx);
            } else {
                handleFailure(ctx);
            }

            System.out.println("Hello World from Javalin");

        } catch (Exception e) {
            handleError(ctx, e);
        }
    };


}