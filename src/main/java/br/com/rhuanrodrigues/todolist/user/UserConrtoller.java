package br.com.rhuanrodrigues.todolist.user;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/user")
public class UserConrtoller {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        UserModel findUser = this.userRepository.findByUsername(userModel.getUsername());
        UserModel user = this.userRepository.save(userModel);

        if(findUser != null) {
            return ResponseEntity.status(400).body("Username já existe !!");
        }

        return ResponseEntity.status(200).body(user);
    }
}
