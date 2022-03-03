package kz.team.sdu.user;

import kz.team.sdu.system.Status;
import kz.team.sdu.user.User;
import kz.team.sdu.user.UserPayload;
import kz.team.sdu.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("api/project/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable("id") Integer id)  {
        return userRepository.findById(id);
    }

    @GetMapping("/edit/{id}")
    public Optional<User> editUser(@PathVariable("id") Integer id)  {
        return userRepository.findById(id);
    }

//    @PostMapping("/register")
//    public ResponseEntity createRecord(@Valid @RequestBody Record record)  {
//        Status status = new Status();
//        recordRepository.save(record);
//        status.status = 1;
//        status.message = "Created";
//        return new ResponseEntity(status, HttpStatus.OK);
//    }

    @PostMapping("/edit/{id}")
//    @PostMapping("/edit")
    public ResponseEntity editUser(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UserPayload payload
    )  {
        Status status = new Status();
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setName(payload.name);
            user.get().setEmail(payload.email);
            user.get().setNumber(payload.number);
            userRepository.save(user.get());
            status.status = 1;
            status.message = "Saved";
        }
        return new ResponseEntity(status, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
//    @DeleteMapping("/delete")
    public ResponseEntity removeUser(@PathVariable("id") Integer id)  {
        Status status = new Status();
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.delete(user.get());
            status.status = 1;
            status.message = "Deleted";
        }
        return new ResponseEntity(status, HttpStatus.OK);
    }

}
