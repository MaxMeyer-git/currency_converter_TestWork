package currencyconverter.core.repository;

import currencyconverter.core.entity.user.Role;
import currencyconverter.core.entity.user.AppUser;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppUserRepositoryTest {

        @Autowired
        private  UsersRepository usersRepository;

        @Test
        public void URtest (){
            Optional<AppUser> res = usersRepository.findByName("Bob");

            AppUser appUser = res.orElseThrow(ArithmeticException::new);

            assertNotNull(appUser);
            TestCase.assertEquals("123456", appUser.getPassword() );

            Set<Role> s = appUser.getRoles();

            s.forEach(role ->
                    TestCase.assertEquals("ADMIN", role.getRole() )
            );


        }

}