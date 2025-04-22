package menrry.prepara_clase2_spring_web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//Para transformar esta clase en un componente Rest uso la siguiente notacion
@RestController
public class WelcomeController {
    //Servicios o recursos Http. debo mapear el metodo
    @GetMapping
    public String welcome(){
        return "Welcom to My Spring Boot Web API";
    }
}
