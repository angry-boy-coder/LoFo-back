package kz.team.sdu.record;

import kz.team.sdu.system.Status;
import kz.team.sdu.user.User;
import kz.team.sdu.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("api/project/record")
public class RecordController {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/list")
    public List<Record> getRecords()  {
        return recordRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Record> getRecord(@PathVariable("id") Integer id)  {
        return recordRepository.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity removeRecord(@PathVariable("id") Integer id)  {
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
