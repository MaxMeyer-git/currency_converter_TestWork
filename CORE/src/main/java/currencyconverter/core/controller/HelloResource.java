package currencyconverter.core.controller;


import currencyconverter.core.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/")
@RestController
public class HelloResource {

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("all")
    public String hello() {
        StringBuilder sb = new StringBuilder();
        usersRepository.findAll().forEach(appUsers -> sb.append(appUsers.getName()).append(" "));
        sb.append("END");
        return sb.toString();
    }

    @GetMapping("curr")
    public String getCurrency (){


        return "Foo";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("secured/all")
    public String securedHello() {
        return "Secured Hello";
    }

    @GetMapping("secured/alternate")
    public String alternate() {
        return "alternate";
    }
}
