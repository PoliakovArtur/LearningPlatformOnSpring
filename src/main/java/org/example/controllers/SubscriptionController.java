package org.example.controllers;

import org.example.dto.MessageDTO;
import org.example.dto.subscription_dto.SubscriptionIncomingDTO;
import org.example.dto.subscription_dto.SubscriptionOutGoingDTO;
import org.example.facade.SubscriptionServiceAdapterToMapper;
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
    private SubscriptionServiceAdapterToMapper adapter;

    public SubscriptionController(SubscriptionServiceAdapterToMapper adapter) {
        this.adapter = adapter;
    }

    @GetMapping
    public SubscriptionOutGoingDTO findById(@RequestBody SubscriptionIncomingDTO incomingDto) {
        return adapter.findById(incomingDto);
    }

    @GetMapping("/all")
    public List<SubscriptionOutGoingDTO> findAll() {
        return adapter.findAll();
    }

    @PutMapping
    public MessageDTO update(@RequestBody SubscriptionIncomingDTO incomingDto) {
        adapter.update(incomingDto);
        return new MessageDTO("Подписка обновлена");
    }

    @PostMapping
    public MessageDTO save(@RequestBody SubscriptionIncomingDTO incomingDto) {
        adapter.save(incomingDto);
        return new MessageDTO("Подписка добавлена");
    }

    @DeleteMapping
    public MessageDTO delete(@RequestBody SubscriptionIncomingDTO incomingDto) {
        adapter.deleteById(incomingDto);
        return new MessageDTO("Подписка удалена");
    }
}
