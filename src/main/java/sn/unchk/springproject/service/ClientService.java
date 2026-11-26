package sn.unchk.springproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.unchk.springproject.dto.ClientDto;
import sn.unchk.springproject.entity.Client;
import sn.unchk.springproject.mapper.ClientMapper;
import sn.unchk.springproject.repository.ClientRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientDto AddClient(ClientDto clientDto){
        if (clientRepository.existsClientByEmail(clientDto.getEmail())){
            throw new RuntimeException( "Cet Email est deja associer a un compte" );
        }
        Client client = ClientMapper.mapToClient(clientDto);
        return ClientMapper.mapToClientDto(clientRepository.save(client));
    }

    public List<ClientDto> findAllClients(){
        return clientRepository.findAll().stream().map(ClientMapper::mapToClientDto).toList();
    }

    public ClientDto findClientById(Long id){
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client ID not found with this id : "+ id));
        return  ClientMapper.mapToClientDto(client);
    }

    public ClientDto updateClient(Long id, ClientDto clientDto){
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client ID not found with this id : "+ id));
        client.setFirstname(clientDto.getFirstname());
        client.setLastname(clientDto.getLastname());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        Client updatedClient = clientRepository.save(client);
        return ClientMapper.mapToClientDto(updatedClient);
    }

    public void deleteClient(Long id){
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client ID not found with this id : "+ id));
        clientRepository.delete(client);
    }
}
