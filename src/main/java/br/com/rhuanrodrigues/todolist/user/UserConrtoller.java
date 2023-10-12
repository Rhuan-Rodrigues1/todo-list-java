package br.com.rhuanrodrigues.todolist.user;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/user")
public class UserConrtoller {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserModel userModel) {

        try {
            UserModel findUser = this.userRepository.findByUsername(userModel.getUsername());
            
            if(findUser != null) {
                return ResponseEntity.status(400).body("Username já existe !!");
            }
    
            String passwordHashString = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
            userModel.setPassword(passwordHashString);
    
            UserModel user = this.userRepository.save(userModel);
            return ResponseEntity.status(200).body(user);
         
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage() + "\n" + e.getStackTrace());
        }
    }
}
