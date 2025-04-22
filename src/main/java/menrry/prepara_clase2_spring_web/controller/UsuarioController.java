package menrry.prepara_clase2_spring_web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import menrry.prepara_clase2_spring_web.modelo.Usuario;
import menrry.prepara_clase2_spring_web.repository.UsuarioRepository;


@RestController
@RequestMapping("/users")   // indica el padrao a seguir
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;
        
    @GetMapping() //Hace diferenciacion en el mapeo
    public List<Usuario> getUsuarios(){
        return repository.findAll();
    }
     
    @GetMapping("/{userlogin}")                                         
    public String getOne(@PathVariable("userlogin") String userlogin){  // funciona ok para buscar en List el login 
       return repository.verificarUsuario(getUsuarios(), userlogin);   
    }


    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable("id") Integer id){
        repository.remove(id);
    }
    @PostMapping()
    public void postUser(@RequestBody Usuario usuario){ // veo el post que realice en Postman por el terminal
        repository.save(usuario);
    }

}
