package com.example.narxoz.restController;

import com.example.narxoz.config.SecurityConfig;
import com.example.narxoz.models.JwtResponse;
import com.example.narxoz.models.LoginRequest;
import com.example.narxoz.models.User;
import com.example.narxoz.services.UserDetailService;
import com.example.narxoz.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.security.PermitAll;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {
    private final UserService userService;
    private final UserDetailService userDetailService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/main")
    public ResponseEntity<User> getUser(Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        log.info("Login start");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse = generateToken(userDetails.getUsername());
            return ResponseEntity.ok(jwtResponse);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
    private JwtResponse generateToken(String username) {
        // 1. Build the JWT claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username); // Include user information in the claims

        // 2. Set the token expiration time (optional)
        long expirationTimeLong = System.currentTimeMillis() + (1 * 3600); // 1 hour from now
        Date expirationTime = new Date(expirationTimeLong);

        // 3. Generate the JWT token
        String token = Jwts.builder()
                .setClaims(claims) // Set the claims
                .setIssuedAt(new Date()) // Set the token issuance time
                .setExpiration(expirationTime) // Set the token expiration time (optional)
                .signWith(SignatureAlgorithm.HS512, "1234") // Sign the token with the secret key
                .compact();

        return new JwtResponse(token, "Login successful!");
    }


    @PostMapping("/registration")
    @PermitAll
    public ResponseEntity<?> registerUser(@RequestBody User user) throws MessagingException, UnsupportedEncodingException {
        if (!userService.createUser(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with email " + user.getSkFk() + " already exists.");

        }
        return ResponseEntity.ok("User registered successfully");
    }
    @GetMapping("/verify/{SkFk}")
    @PermitAll
    public ResponseEntity<String> verifyUser(@Param("code") String code, @PathVariable String SkFk) {
        if (userService.verify(code, SkFk)) {
            return ResponseEntity.ok("verify_success");
        } else {
            return ResponseEntity.ok("verify_fail");
        }
    }

    @PutMapping("/forget/{SkFk}")
    @PermitAll
    public ResponseEntity<String> forgetPassword(@PathVariable String SkFk) throws MessagingException, UnsupportedEncodingException {
        if(userService.forget(SkFk)) return ResponseEntity.ok("Password change");
        return ResponseEntity.ok("Such User doesn't find");
    }
}
