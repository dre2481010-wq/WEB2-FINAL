package com.example.productcrud.controller;

import com.example.productcrud.dto.RegisterRequest;
import com.example.productcrud.model.User;
import com.example.productcrud.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //login -> menampilkan halaman login
    @GetMapping("/login")
    public String login(Model model{
        model.addAttribute("registerrequest")
        return "login";
    }

    //Get Register -> menampilkan halaman register
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    //POST register -> Proses registrasi
    @PostMapping("/register")
    public String processRegister(@ModelAttribute RegisterRequest registerRequest, RedirectAttributes redirectAttributes) {
        //validasi username tidak boleh kosong
        if (registerRequest.getUsername() == null || registerRequest.getUsername().trim().isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Password tidak boleh kosong");
            return "redirect:/register";
        }
        //validasi password tidak boleh kosong
        if (registerRequest.getPassword()== null || registerRequest.getPassword().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Password tidak boleh kosong");
            return "redirect:/register";
        }
        //validasi password harus cocok
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
            redirectAttributes.addFlashAttribute("error", "Password dan konfirmasi password harus sama!");
            return "redirect:/register";
        }
        //validasi username belum terdaftar

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            redirectAttributes.addFlashAttribute("error", "Username sudah terdaftar");
            return "redirect:/login";
        }
        //simpan user baru dengan password ter-encode
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);


        userRepository.save(new User(username, passwordEncoder.encode(password)));
        return "redirect:/login";
    }

}
