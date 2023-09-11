package com.iddev.http.controller;

import com.iddev.dto.ClientCreateEditDto;
import com.iddev.dto.PageResponse;
import com.iddev.enums.Role;
import com.iddev.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping()
    public String findAll(Model model, Pageable pageable) {
        var page = clientService.findAll(pageable);
        model.addAttribute("clients", PageResponse.of(page));
        return "client/clients";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return clientService.findById(id)
                .map(client -> {
                    model.addAttribute("client", client);
                    model.addAttribute("roles", Role.values());
                    return "/client/client";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(ClientCreateEditDto user) {
        clientService.create(user);
        return "redirect:/login";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, ClientCreateEditDto client) {
        return clientService.update(id, client)
                .map(it -> "redirect:/clients/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!clientService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/clients";
    }
}
