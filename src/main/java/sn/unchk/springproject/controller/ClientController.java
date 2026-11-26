package sn.unchk.springproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.unchk.springproject.dto.ClientDto;
import sn.unchk.springproject.service.ClientService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientService clientService;
    @PostMapping("")
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto clientDto){
        return new ResponseEntity<>(clientService.AddClient(clientDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<ClientDto>> getClients(){
        return ResponseEntity.ok(clientService.findAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable("id") Long client_id){
        return ResponseEntity.ok(clientService.findClientById(client_id));
    }

    @PutMapping("{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable("id") Long client_id,@Valid @RequestBody ClientDto clientDto){
        return ResponseEntity.ok(clientService.updateClient(client_id,clientDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long client_id){
        clientService.deleteClient(client_id);
        return ResponseEntity.noContent().build();
    }
}
