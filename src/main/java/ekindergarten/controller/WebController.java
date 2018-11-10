package ekindergarten.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = "/")
    public String homePage() {
        return "redirect:/index.html";
    }

    @GetMapping(value = "/index")
    public String index() {
        return "redirect:index.html";
    }

    @GetMapping(value = "/register")
    public String register() {
        return "redirect:register.html";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "redirect:login.html";
    }

    @GetMapping(value = "/admin")
    public String adminHomePage() {
        return "redirect:admin.html";
    }

    @GetMapping(value = "/parent")
    public String parentHomePage() {
        return "redirect:parent.html";
    }

    @GetMapping(value = "/teacher")
    public String teacherHomePage() {
        return "redirect:teacher.html";
    }

    @GetMapping(value = "/add-child")
    public String addChild() {
        return "redirect:add-child.html";
    }

    @GetMapping(value = "/child")
    public String child() {
        return "redirect:child.html";
    }

    @GetMapping(value = "/absence")
    public String absence() {
        return "redirect:absence.html";
    }

    @GetMapping(value = "/fees")
    public String fees() {
        return "redirect:fees.html";
    }

    @GetMapping(value = "/remarks_teacher")
    public String remarksTeacher() {
        return "redirect:remarks_teacher.html";
    }

    @GetMapping(value = "/child2")
    public String childTrustedPeople() {
        return "redirect:child2.html";
    }
}
