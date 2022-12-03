package com.uva.users.controller;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uva.users.exception.UserException;
import com.uva.users.model.User;
import com.uva.users.repository.UserRepository;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserRepository repository;
    private static final String DEFUSEREXCEP = "Sin resultado";

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    // GET

    /**
     * Devuelve lista de usuarios.
     * 
     * @return Lista de usuarios.
     */
    @GetMapping()
    public List<User> getUsers() {
        return repository.findAll();
    }

    /**
     * Devuelve los usuarios filtrando entre si están habilitados o no.
     * 
     * @param enable Estado de habilitado.
     * 
     * @return Lista de usuarios que cumplen las características.
     */
    @GetMapping(params = "enable")
    public List<User> getUsersWithStatus(@RequestParam boolean enable) {
        return repository.findAllByEnabled(enable);
    }

    /**
     * Devuelve el usuario con el ID introducido.
     * 
     * @param id ID de usuario.
     * @return Usuario.
     */
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new UserException(DEFUSEREXCEP));    
    }

    /**
     * Devuelve los usuarios filtrando entre si están habilitados o no.
     * 
     * @param enable Estado de habilitado.
     * 
     * @return Lista de usuarios que cumplen las características.
     */
    @GetMapping(params = "email")
    public User getUsersWithEmail(@RequestParam String email) {
        return repository.findByEmail(email);
    }

    // POST

    /**
     * Crea un nuevo usuario.
     * 
     * @param user Datos del usuario.
     * @return Mensaje de confirmacion.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String newUser(@RequestBody User user) {
        try {
            repository.save(user);
            return "Nuevo registro creado";
        } catch (Exception e) {
            return "Error al crear el nuevo registro.";
        }
    }

    // PUT

    /**
     * Modifica un usuario.
     * 
     * @param id   ID del usuario.
     * @param body Datos del usuario.
     * @return
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateUser(@PathVariable int id, @RequestBody User body) {
        try {
            User user = repository.findById(id).orElseThrow(() -> new UserException(DEFUSEREXCEP));
            User tmp = new User();
            tmp.setFirstName(body.getFirstName());
            tmp.setLastName(body.getLastName());
            tmp.setEmail(body.getEmail());
            tmp.setPassword(body.getPassword());
            User.copyNonNullProperties(tmp, user);
            user.modified();
            repository.save(user);
            return "Registro actualizado";
        } catch (Exception e) {
            return "Error al actualizar el registro.";
        }
    }

    /**
     * Habilita los usarios con ids introducidos.
     * Política de actualización: Todos o ninguno.
     * 
     * @param user_id Lita de IDs.
     * 
     * @return Mensaje de confrmación.
     */
    @PutMapping("/enable")
    public String enableUsers(@RequestParam List<Integer> user_id) {
        User user;
        boolean ok = true;
        try {
            int i = 0;
            while (i < user_id.size() && ok) {
                ok = repository.existsById(user_id.get(i));
                i++;
            }
            if(!ok){
                throw new UserException(DEFUSEREXCEP);
            }
        } catch (Exception e) {
            return "Error al buscar uno de los registros.";
        }
        try {
            for (int id : user_id) {
                user = repository.findById(id).orElseThrow(() -> new UserException(DEFUSEREXCEP));
                user.setEnabled(true);
                user.modified();
                repository.save(user);
            }
            return "Registros actualizados";
        } catch (Exception e) {
            return "Error al actualizar uno de los registros.";
        }
    }

    /**
     * Deshabilita los usarios con ids introducidos.
     * 
     * @param user_id Lita de IDs.
     * 
     * @return Mensaje de confrmación.
     */
    @PutMapping("/disable")
    public String disableUsers(@RequestParam List<Integer> user_id) {
        User user;
        boolean ok = true;
        try {
            int i = 0;
            while (i < user_id.size() && ok) {
                ok = repository.existsById(user_id.get(i));
                i++;
            }
            if(!ok){
                throw new UserException(DEFUSEREXCEP);
            }
        } catch (Exception e) {
            return "Error al buscar uno de los registros.";
        }
        try {
            for (int id : user_id) {
                user = repository.findById(id).orElseThrow(() -> new UserException(DEFUSEREXCEP));
                user.setEnabled(false);
                user.modified();
                repository.save(user);
            }
            return "Registros actualizados";
        } catch (Exception e) {
            return "Error al actualizar uno de los registros.";
        }
    }

    // DELETE

    /**
     * Elimina el usuario con el id introducido.
     * 
     * @param id Id del usuario
     * 
     * @return Mensaje de confirmación de eliminacion.
     */
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        try {
            repository.deleteById(id);
            return "Registro eliminado";
        } catch (Exception e) {
            return "Error al eliminar registro.";
        }
    }

}
