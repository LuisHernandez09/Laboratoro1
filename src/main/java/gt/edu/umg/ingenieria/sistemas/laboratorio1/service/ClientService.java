package gt.edu.umg.ingenieria.sistemas.laboratorio1.service;

import gt.edu.umg.ingenieria.sistemas.laboratorio1.dao.ClientRepository;
import gt.edu.umg.ingenieria.sistemas.laboratorio1.model.Client;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;    
  
     public List<Client> findAll() {
        return (List<Client>) this.clientRepository.findAll();
    }
    
    public Client findNit(String criterio) {
        return this.clientRepository.findClientByNit(criterio);
    }
    
    public Client findId(Long id) {
        return this.clientRepository.findClientById(id);
    }
   
    public Client setNew(Client cliente) {               
        return this.clientRepository.save(cliente);
    }
    
    public Client setUpdate(Client cliente) {
        return this.clientRepository.save(cliente);        
    }    
    
}
