package sn.unchk.springproject.mapper;

import sn.unchk.springproject.dto.ClientDto;
import sn.unchk.springproject.entity.Client;

public class ClientMapper {

    public static ClientDto mapToClientDto (Client client){
        return new ClientDto(
                client.getId(),
                client.getFirstname(),
                client.getLastname(),
                client.getEmail(),
                client.getPhone()
        );
    }

    public static Client mapToClient(ClientDto clientDto){
        return new Client(
                clientDto.getId(),
                clientDto.getFirstname(),
                clientDto.getLastname(),
                clientDto.getEmail(),
                clientDto.getPhone()
        );
    }
}
