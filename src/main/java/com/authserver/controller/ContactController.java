package com.authserver.controller;

import com.authserver.entity.AppUser;
import com.authserver.entity.Contact;
import com.authserver.repository.ContactRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/contacts")
public class ContactController {
    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping("/findOne/{id}")
    ResponseEntity<EntityModel<Contact>> findOne(@PathVariable Long id){
        return contactRepository.findById(id) //
                .map(contact -> EntityModel.of(contact, //
                        linkTo(methodOn(ContactController.class).findOne(contact.getId())).withSelfRel(), //
                        linkTo(methodOn(ContactController.class).findAll()).withRel("contacts"))) //
                .map(ResponseEntity::ok) //
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findAll")
    ResponseEntity<CollectionModel<EntityModel<Contact>>> findAll(){
        List<EntityModel<Contact>> contacts = StreamSupport.stream(contactRepository.findAll().spliterator(), false)
                .map(contact -> EntityModel.of(contact, //
                        linkTo(methodOn(ContactController.class).findOne(contact.getId())).withSelfRel(), //
                        linkTo(methodOn(ContactController.class).findAll()).withRel("contacts"))) //
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(contacts,
                linkTo(methodOn(ContactController.class).findAll()).withSelfRel()));
    }
//    @PostMapping("addContact")
//    ResponseEntity<EntityModel<Contact>> addContact(@RequestBody Contact contact, @RequestBody AppUser appUser){}

}
