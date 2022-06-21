package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientServices { //en las interfaces se declara los cabezales de los metodos
//se genera primero la interface y defino, esta interface va a trabajar con tal metodo. pero no se define como va a trabaar

//en la misma carpeta se servicio se genera otra carpeta llamada implementaciones(implement)
//dentro se crea una clase que implemente todos los Servicio en este caso de los Cliente


    //aca solo llamo sus cabezales
    //lo que van hacer estos servicios es devolver lo que dice en su nombre,
    List<ClientDTO> getClientsDto();

    Client getClientCurrent(Authentication authentication);

    ClientDTO getClientDTO(Long id);

    void saveClient(Client client);

    Client getClientByEmail(String email);
}
