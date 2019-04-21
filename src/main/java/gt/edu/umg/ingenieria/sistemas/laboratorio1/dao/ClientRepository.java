package gt.edu.umg.ingenieria.sistemas.laboratorio1.dao;

import gt.edu.umg.ingenieria.sistemas.laboratorio1.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Josué Barillas (jbarillas)
 */

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer>{
    
    public Client findClientByNit(String bus);
    
    public Client findClientById(Long id);
    
    
    
}
