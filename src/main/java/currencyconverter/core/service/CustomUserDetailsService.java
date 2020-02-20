package currencyconverter.core.service;


import currencyconverter.core.entity.user.CustomAppUsersDetails;
import currencyconverter.core.entity.user.AppUsers;
import currencyconverter.core.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUsers> optionalUsers = usersRepository.findByName(username);

        optionalUsers
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        return optionalUsers
                .map(CustomAppUsersDetails::new).get();
    }
}
