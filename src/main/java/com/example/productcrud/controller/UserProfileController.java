package com.example.productcrud.controller;

import com.example.productcrud.dto.PasswordRequest;
import com.example.productcrud.model.User;
import com.example.productcrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // WAJIB ADA
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // WAJIB ADA AGAR TIDAK MERAH

    @GetMapping
    public String viewProfile(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/edit")
    public String editProfileForm(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/edit-profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute User userDetails, Principal principal, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(principal.getName());
        user.setFullName(userDetails.getFullName());
        user.setEmail(userDetails.getEmail());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setAddress(userDetails.getAddress());
        user.setBio(userDetails.getBio());
        user.setProfileImageUrl(userDetails.getProfileImageUrl());

        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "Profil berhasil diperbarui!");
        return "redirect:/profile";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("passwordRequest", new PasswordRequest());
        return "user/change-password";
    }

    @PostMapping("/change-password")
    public String processChangePassword(Principal principal,
                                        @ModelAttribute PasswordRequest request,
                                        RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(principal.getName());

        // Validasi: Cek apakah password lama sesuai (BCrypt check)
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Password lama tidak sesuai!");
            return "redirect:/profile/change-password";
        }

        // Validasi: Cek apakah password baru & konfirmasi cocok
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("error", "Konfirmasi password baru tidak cocok!");
            return "redirect:/profile/change-password";
        }

        // Simpan: Encode password baru dengan BCrypt sebelum di-save
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Password berhasil diganti!");
        return "redirect:/profile";
    }
} // Pastikan kurung kurawal penutup ini ada di paling bawah!