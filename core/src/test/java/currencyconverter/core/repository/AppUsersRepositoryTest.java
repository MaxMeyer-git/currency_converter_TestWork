package currencyconverter.core.repository;

import currencyconverter.core.entity.user.Role;
import currencyconverter.core.entity.user.AppUsers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppUsersRepositoryTest {

        @Autowired
        private  UsersRepository usersRepository;

        @Test
        public void URtest (){
            Optional<AppUsers> res = usersRepository.findByName("Bob");

            AppUsers appUsers = res.orElseThrow(ArithmeticException::new);

            System.out.println("-------------------");
            System.out.println(
                    appUsers.getName() +"\n"+ appUsers.getPassword()
            );

            Set<Role> s = appUsers.getRoles();

            s.forEach(role ->
                System.out.println(role.getRole())
            );
            System.out.println("-------------------");


        }

}