package fullstack.fleamarket.controller;

import javax.validation.Valid;

import fullstack.fleamarket.message.request.LoginForm;
import fullstack.fleamarket.message.request.SignUpForm;
import fullstack.fleamarket.message.response.JwtResponse;
import fullstack.fleamarket.model.User;
import fullstack.fleamarket.repository.UserDAO;
import fullstack.fleamarket.security.jwt.JwtProvider;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDAO userDAO;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        JSONObject answer = new JSONObject();

        if(userDAO.existsByUsernameEquals(signUpRequest.getUsername())) {
            answer.put("message", "Fail -> Username is already taken!");
            return new ResponseEntity<String>(answer.toString(),
                    HttpStatus.BAD_REQUEST);
        }

        if(userDAO.existsByEmailEquals(signUpRequest.getEmail())) {
            answer.put("message", "Fail -> Email is already in use!");
            return new ResponseEntity<String>(answer.toString(),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getName(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setRoles("USER");
        userDAO.save(user);


        answer.put("message", "User registered successfully!");
        return ResponseEntity.ok().body(answer.toString());
    }
}