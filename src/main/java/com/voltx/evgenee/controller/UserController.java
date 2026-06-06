import com.voltx.evgenee.dto.requests.LoginRequest;
import com.voltx.evgenee.dto.requests.UserRequestDto;
import com.voltx.evgenee.dto.responses.LoginResponse;
import com.voltx.evgenee.dto.responses.UserResponseDto;
import com.voltx.evgenee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(
            @RequestBody UserRequestDto requestDto) {

        return ResponseEntity.ok(userService.register(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getProfile(
            Authentication authentication) {

        return ResponseEntity.ok(
                userService.getProfile(authentication.getName())
        );
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponseDto> updateProfile(
            @RequestBody UserRequestDto requestDto,
            Authentication authentication) {

        return ResponseEntity.ok(
                userService.updateProfile(
                        authentication.getName(),
                        requestDto
                )
        );
    }
}