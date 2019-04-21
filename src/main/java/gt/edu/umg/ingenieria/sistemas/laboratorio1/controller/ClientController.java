package gt.edu.umg.ingenieria.sistemas.laboratorio1.controller;

import gt.edu.umg.ingenieria.sistemas.laboratorio1.model.Client;
import gt.edu.umg.ingenieria.sistemas.laboratorio1.service.ClientService;
import gt.edu.umg.ingenieria.sistemas.laboratorio1.service.ReportService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Josué Barillas (jbarillas)
 */

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;
    
    @GetMapping("/clientes/buscarTodos")
    public List<Client> todos() {
        return this.clientService.findAll();
    }
    
    @GetMapping("/clientes/buscarPorNit")
    public Client por_nit(@RequestParam(name = "nit", required = true) String bus) {
        return this.clientService.findNit(bus);
    }
    
   @GetMapping("/clientes/buscarPorNombreApellido")
    public List<Client> getByName(@RequestParam(name = "query", required = true) String query) {
        
        List<Client> respuesta= new ArrayList<Client>();
        List<Client> aux= null;
        int como=query.indexOf(" ");
        if(como>0){
            aux = this.clientService.findAll();
            for (int i=0; i<aux.size();i++){
                if(aux.get(i).getFirstName().equals(query.substring(0,como)) && aux.get(i).getFirstName().equals(query.substring(como+1))){
                    respuesta.add(aux.get(i));
                }
            }
        }
        else{
            int asterisco=query.indexOf("*");
            if(asterisco>=0){
                if(asterisco==0){
                    aux = this.clientService.findAll();
                    for (int i=0; i<aux.size();i++){
                        if(aux.get(i).getFirstName().equals(query.substring(como+1))){
                            respuesta.add(aux.get(i));
                        }
                    }
                }
                else{
                    if(asterisco==(query.length()-1)){
                        aux = this.clientService.findAll();
                        for (int i=0; i<aux.size();i++){
                            if(aux.get(i).getLastName().equals(query.substring(como+1))){
                                respuesta.add(aux.get(i));
                            }
                        }
                    }
                    else{
                    aux = this.clientService.findAll();
                    String combinado="";
                    for (int i=0; i<aux.size();i++){
                        combinado = aux.get(i).getFirstName()+aux.get(i).getLastName();
                        if(combinado.startsWith(query.substring(0,asterisco)) && combinado.endsWith(query.substring(asterisco+1))){
                            respuesta.add(aux.get(i));
                        }
                    }
                    }
                }
            }
            else{
                aux = this.clientService.findAll();
                String combinado="";
                for (int i=0; i<aux.size();i++){
                        
                        combinado = aux.get(i).getFirstName()+aux.get(i).getLastName();
                        
                    if(combinado.equals(query)){
                        respuesta.add(aux.get(i));
                    }
                }
            }
        }
        return respuesta;
    }
    @PostMapping("/clientes/crearCliente")
    public Client crearCl(@RequestBody(required = true) Client cli) {
        
        Client clie= new Client();
        if(cli.getNit().length()>10){
            clie.setId(Long.parseLong("0"));
            clie.setFirstName("");
            clie.setLastName("");
            clie.setAddress("");
            clie.setPhone("");
            clie.setNit("NIT Invalido");
        }
        else if(cli.getNit().isEmpty()==true){
            clie.setNit("Lo sentimos. El sistema solo permite el registro de usuarios mayores de edad.");
            clie.setId(Long.parseLong("0"));
            clie.setFirstName("");
            clie.setLastName("");
            clie.setAddress("");
            clie.setPhone("");
        }
        else{
            try {
                cli.setFirstName(cli.getFirstName().substring(0,1).toUpperCase()+cli.getFirstName().substring(1).toLowerCase());
                cli.setLastName(cli.getLastName().substring(0,1).toUpperCase()+cli.getLastName().substring(1).toLowerCase());
            } catch (Exception e) {
            }
            
            clie = this.clientService.setNew(cli);
           
            
        }
        return clie;
    }

    
    @PutMapping("/clientes/editarCliente")
    public Client editarCl(@RequestBody(required = true) Client cli) {
        
        Client clie = new Client();
        
        if(cli.getNit().length()>10){
            clie.setId(Long.parseLong("0"));
            clie.setFirstName("");
            clie.setLastName("");
            clie.setAddress("");
            clie.setPhone("");
            clie.setNit("NIT Invalido");
        }
        else if(cli.getNit().isEmpty()==true){
            clie.setNit("Lo sentimos. El sistema solo permite el registro de usuarios mayores de edad.");
            clie.setId(Long.parseLong("0"));
            clie.setFirstName("");
            clie.setLastName("");
            clie.setAddress("");
            clie.setPhone("");
        }
        else{
            cli.setFirstName(cli.getFirstName().substring(0,1).toUpperCase()+cli.getFirstName().substring(1).toLowerCase());
            cli.setLastName(cli.getLastName().substring(0,1).toUpperCase()+cli.getLastName().substring(1).toLowerCase());
            clie = this.clientService.setNew(cli);
           
            
        }
        return clie;
    }
    
   @PutMapping("/clientes/editarCliente/{identificador}/{numero}")
    public Client setUpdateNit(@PathVariable(required = true) long identificador,@PathVariable(required = true) String numero) {
        
        Client clie= new Client();
        clie = this.clientService.findId(identificador);
        if(clie.getNit().length()>10){
            clie.setId(Long.parseLong("0"));
            clie.setFirstName("");
            clie.setLastName("");
            clie.setAddress("");
            clie.setPhone("");
            clie.setNit("NIT Invalido");
        }
        else{
            clie.setNit(numero);
            this.clientService.setUpdate(clie);
        }
        return clie;
    }
    
    @PutMapping("/clientes/editarCliente/{identificador}/{nombre}/{apellido}")
    public Client setUpdateNombre(@PathVariable(required = true) long identificador,@PathVariable(required = true) String nombre,@PathVariable(required = true) String apellido) {
        
        Client clie= new Client();
        
        clie = this.clientService.findId(identificador);
        try{
            clie.setFirstName(nombre.substring(0,1).toUpperCase()+nombre.substring(1).toLowerCase());
            clie.setLastName(apellido.substring(0,1).toUpperCase()+apellido.substring(1).toLowerCase());
        }
        catch(Exception a){
            
        }
        
        return this.clientService.setUpdate(clie);
    }
 
    @GetMapping("/clientes/generarReporteClientes")
    public String reporte() {
        
        String htmlCadena = "Reporte Clientes <br><br>";
        
        String carpeta = "/var/www/";
        File f = new File(carpeta);
        File[] ficheros = f.listFiles();
        int contador=1;
        for (int x=0;x<ficheros.length;x++){
            contador++;            
        }
        
        File reportes = new File("/var/www/Clientes_"+String.valueOf(contador)+".html");
        List<Client> respuesta = this.clientService.findAll();
        
        try{
            BufferedWriter buffer = new BufferedWriter(new FileWriter(reportes));
        for(int i=0; i<respuesta.size();i++){
            htmlCadena= htmlCadena+"  \t" +
            respuesta.get(i).getId()+"  \t" +
            respuesta.get(i).getFirstName()+"  \t" +
            respuesta.get(i).getLastName()+"  \t" +
            respuesta.get(i).getNit()+"  \t" +
            respuesta.get(i).getPhone()+"  \t" +
            respuesta.get(i).getAddress()+"  \t" +
            "<br>" ;
        }
        
        buffer.write(htmlCadena);
        buffer.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return "El reporte  var/www/Clientes_"+String.valueOf(contador)+".html ha sido generado.";
    }
    
    
}
