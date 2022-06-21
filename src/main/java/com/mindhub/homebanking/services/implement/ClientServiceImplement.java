package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service //lo primero que tenemos que hacer es decir que esto va hacer la implementacion de un servicio
public class ClientServiceImplement implements ClientServices { //aca implementamos, asi que ah implementar

    @Autowired
    ClientRepository clientRepository;

    @Override//avisa que estas sobreescribiendo
    public List<ClientDTO> getClientsDto() { //obtener lista de clienteDTO
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @Override//avisa que estas sobreescribiendo
    public Client getClientCurrent(Authentication authentication) { //obtener cliente autenticado
        return clientRepository.findByEmail(authentication.getName());
    }

    @Override//avisa que estas sobreescribiendo
    public ClientDTO getClientDTO(Long id) { //obtener un solo clientDTO
        return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);//map es asociar
    }

    @Override//avisa que estas sobreescribiendo
    public void saveClient(Client client) { //guardar un cliente DTOs
        clientRepository.save(client);
    }

    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }


    ;
}
