package kz.team.sdu.record;

import kz.team.sdu.system.Status;
import kz.team.sdu.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


    @PostMapping("/create")
    public ResponseEntity createRecord(@Valid @RequestBody Record record)  {
        Status status = new Status();
        recordRepository.save(record);
        status.status = 1;
        status.message = "Created";
        return new ResponseEntity(status, HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity editRecord(
            @PathVariable("id") Integer id,
            @Valid @RequestBody Record editRecord
    )  {
        Status status = new Status();
        Optional<Record> record = recordRepository.findById(id);
        if(record.isPresent()){
            record.get().setTitle(editRecord.getTitle());
            record.get().setCategory(editRecord.getCategory());
            record.get().setDescription(editRecord.getDescription());
            record.get().setPlace(editRecord.getPlace());
            recordRepository.save(record.get());
            status.status = 1;
            status.message = "Saved";
        }
        return new ResponseEntity(status, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity removeRecord(@PathVariable("id") Integer id)  {
        Status status = new Status();
        Optional<Record> record = recordRepository.findById(id);
        if(record.isPresent()){
            recordRepository.delete(record.get());
            status.status = 1;
            status.message = "Deleted";
        }
        return new ResponseEntity(status, HttpStatus.OK);
    }

}
