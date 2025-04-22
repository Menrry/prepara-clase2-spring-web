package menrry.prepara_clase2_spring_web.repository;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Repository;

import menrry.prepara_clase2_spring_web.modelo.Usuario;

@Repository  // nociones de contrato de usabilidad
public class UsuarioRepository {
    public void save(Usuario usuario){
        //if(usuario.getId() == null)
        System.out.println("SAVE - Recebendo o usuário na camada de repositório");
        //else
        System.out.println(usuario);
    }
    /*
     * public void deleteById(Integer id){
     * System.out.println(String.format("DELETE/id - Recebendo o id: %d para excluir", id));
     * System.out.println(id);
     * }
     */
    public void update(Usuario usuario){
        System.out.println("UPDATE - Recebendo o usuário na camada de repositório");
        System.out.println(usuario);
    }
    public void remove(Integer id){
        System.out.println(String.format("DELETE/id - Recebendo o id: %d para excluir um usuário", id));
        System.out.println(id);
    }
    public List<Usuario> listAll(){
        System.out.println("LIST - Listando os usários do sistema");
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("gleyson","password"));
        usuarios.add(new Usuario("frank","masterpass"));
        return usuarios;
    }
    public Usuario finById(Integer id){
        System.out.println(String.format("FIND/id - Recebendo o id: %d para localizar um usuário", id));
        return new Usuario("gleyson","password");
    }
    public List<Usuario> findAll(){
        System.out.println("LIST - Listando os usuários do sistema");
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Menrry","1234"));
        usuarios.add(new Usuario("Angie","9568"));
        return usuarios;
    }
    public Usuario findByUserLogin(String userlogin){
       // System.out.println(String.format(userlogin, "objeto"));
       System.out.println(String.format("FIND/userlogin - Recebendo o userlogin : %s para localizar um usuario ", userlogin));
        return new Usuario("Menrry", "password");
    }




//********************** Probar a pedal */ userlogin

    public String verificarUsuario(List<Usuario> listaUsuarios, String userlogin) {
    if (listaUsuarios == null || listaUsuarios.isEmpty() || userlogin == null || userlogin.isEmpty()) {
        return "La lista de usuarios está vacía o el login a buscar es inválido.";
    }

        
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getLogin().equals(userlogin)) {
              return "El login del usuario encontrado es: " + usuario.getLogin() + ", con el password: " + usuario.getPassword();
            }
        }   
        return "No se encontró ningún usuario con el login: " + userlogin;
    }
}

