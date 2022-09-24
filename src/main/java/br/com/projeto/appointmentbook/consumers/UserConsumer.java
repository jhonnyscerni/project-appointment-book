package br.com.projeto.appointmentbook.consumers;

import br.com.projeto.appointmentbook.integration.enums.ActionType;
import br.com.projeto.appointmentbook.integration.response.UserEventResponse;
import br.com.projeto.appointmentbook.models.integration.User;
import br.com.projeto.appointmentbook.services.UserService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    @Autowired
    private UserService userService;

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "${broker.queue.userEventQueue.name}", durable = "true"),
        exchange = @Exchange(value = "${broker.exchange.userEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")
    ))
    public void listenUserEvent(@Payload UserEventResponse userEventResponse){
        User user =  userEventResponse.convertToUser();
        switch (ActionType.valueOf(userEventResponse.getActionType())){
            case CREATE:
                userService.create(user);
                break;
            case UPDATE:
                userService.update(user);
                break;
            case DELETE:
                userService.delete(user);
                break;
        }
    }
}
