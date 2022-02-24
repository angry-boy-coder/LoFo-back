//package kz.team.sdu.test;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class Controller {
//    @Autowired
//    private Repository studentRepo;
//
//    @GetMapping("/students")
//    public String listAll(Model model) {
//        List<Model> liastStudents = studentRepo.findAll();
//        model.addAttribute("listStudents", listStudents);
//
//        return "students";
//    }
//
//}
