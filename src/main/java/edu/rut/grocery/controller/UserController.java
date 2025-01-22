package edu.rut.grocery.controller;

import edu.rut.grocery.dto.UserDto;
import edu.rut.grocery.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.logging.LogManager;
import java.util.logging.Logger;

@Controller
@RequestMapping("/users")
public class UserController {

	private final AuthService authService;
	private static final Logger LOG = LogManager.getLogManager().getLogger(UserController.class.getName());

	public UserController(AuthService authService) {
		this.authService = authService;
	}

	@ModelAttribute("userDto")
	public UserDto init() {
		return new UserDto();
	}

	@GetMapping("/register")
	public String registerForm() {

		return "register";
	}

	@PostMapping("/login-fail")
	public String onFailedLogin(
			@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
			RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
		redirectAttributes.addFlashAttribute("badCredentials", true);

		return "redirect:/users/login";
	}

	@PostMapping("/register")
	public String doRegister(@Valid @ModelAttribute("userDto") UserDto userDto,
							 BindingResult bindingResult,
							 RedirectAttributes redirectAttributes) {

		if (authService.userExists(userDto))
			bindingResult.rejectValue("username", "user.exist", "User already exist");

		if (!authService.passwordsCheck(userDto)) {
			bindingResult.rejectValue("passwordConfirm", "passwords.match", "Passwords not matches");
		}


		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("userDto", userDto);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDto", bindingResult);

			return "redirect:/users/register";
		}

		authService.register(userDto);
		return "redirect:/users/login";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
