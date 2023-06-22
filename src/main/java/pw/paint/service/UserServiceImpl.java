package pw.paint.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pw.paint.DTOs.mappers.UserMapper;
import pw.paint.DTOs.model.UserDto;
import pw.paint.exception.UserNotFoundException;
import pw.paint.model.User;
import pw.paint.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDto getUserById(ObjectId id){
        System.out.println(id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException();
        return UserMapper.toUserDto(user.orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserDetails u = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println(u.getUsername());
        System.out.println(u.getPassword());
        return u;
    }
}
