package org.example.сontrollers;

import org.example.dto.MessageDTO;
import org.example.dto.subscription_dto.SubscriptionIncomingDTO;
import org.example.dto.subscription_dto.SubscriptionOutGoingDto;
import org.example.facade.SubscriptionFacade;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private SubscriptionFacade facade;

    public SubscriptionController(SubscriptionFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public SubscriptionOutGoingDto findById(@RequestBody SubscriptionIncomingDTO incomingDto) {
        return facade.findById(incomingDto);
    }

    @GetMapping("/all")
    public List<SubscriptionOutGoingDto> findAll() {
        return facade.findAll();
    }

    @PutMapping
    public MessageDTO update(@RequestBody SubscriptionIncomingDTO incomingDto) {
        facade.update(incomingDto);
        return new MessageDTO("Подписка обновлена");
    }

    @PostMapping
    public MessageDTO save(@RequestBody SubscriptionIncomingDTO incomingDto) {
        facade.save(incomingDto);
        return new MessageDTO("Подписка добавлена");
    }

    @DeleteMapping
    public MessageDTO delete(@RequestBody SubscriptionIncomingDTO incomingDto) {
        facade.deleteById(incomingDto);
        return new MessageDTO("Подписка удалена");
    }
}
