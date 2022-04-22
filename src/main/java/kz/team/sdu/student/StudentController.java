package kz.team.sdu.student;

import kz.team.sdu.system.Status;
import kz.team.sdu.system.auth.AuthenticationRequest;
import kz.team.sdu.system.auth.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/project/user")
public class StudentController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StudentService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/{id}")
    public Optional<Student> getUser(@PathVariable("id") Integer id)  {
        return studentRepository.findById(id);
    }

//    @GetMapping("/edit/{id}")
//    public Optional<Student> editUser(@PathVariable("id") Integer id)  {
//        return studentRepository.findById(id);
//    }

    @PostMapping("/register")
    public ResponseEntity<?> createRecord(@Valid @RequestBody Student student)  {
        Status status = new Status();

        studentRepository.save(student);
        status.status = 1;
        status.message = "Created";

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> createRecord(@Valid @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        Status status = new Status();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new Exception("Username or password wrong", ex);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        status.value = jwtUtil.generateToken(userDetails);
        status.status = 1;
        status.message = "Authorized";

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

//    @PostMapping("/edit/{id}")
    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> editUser(
            @PathVariable("id") Integer id,
            @Valid @RequestBody StudentPayload payload,
            Authentication authentication
    )  {
        Status status = new Status();
        authentication.getName();
//        Optional<Student> user = studentRepository.findById(id);
        Optional<Student> user = studentRepository.findByUsername(authentication.getName());
        if(user.isPresent()){
            user.get().setName(payload.name);
            user.get().setEmail(payload.email);
            user.get().setNumber(payload.number);
            studentRepository.save(user.get());
            status.status = 1;
            status.message = "Saved";
        }
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

//    @DeleteMapping("/delete/{id}")
    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> removeUser(Authentication authentication)  {
        Status status = new Status();
//        Optional<Student> user = studentRepository.findById(id);
        Optional<Student> user = studentRepository.findByUsername(authentication.getName());
        if(user.isPresent()){
            studentRepository.delete(user.get());
            status.status = 1;
            status.message = "Deleted";
        }
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
