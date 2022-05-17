package com.authserver.controller;

import com.authserver.entity.Contact;
import com.authserver.repository.ContactRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/contact")
public class ContactController {
    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping("/findContactWithId/{id}")
    ResponseEntity<EntityModel<Optional<Contact>>> findContactWithId(@PathVariable Long id){
        return ResponseEntity.ok(
                EntityModel.of(contactRepository.findById(id),
                        linkTo(methodOn(ContactController.class).findContactWithId(id)).withSelfRel()));
    }

}
