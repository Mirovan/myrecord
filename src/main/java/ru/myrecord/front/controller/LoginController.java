package ru.myrecord.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.UserService;

import javax.validation.Valid;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	@RequestMapping(value="/login/", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(required = false) String error){
		ModelAndView modelAndView = new ModelAndView();
		//if (error != null) modelAndView.addObject("error", "Ошибка авторизации");
		modelAndView.setViewName("login");
		return modelAndView;
	}

//
//	@RequestMapping(value="/login/", method = RequestMethod.POST)
//	public ModelAndView loginPost(){
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("login");
//		return modelAndView;
//	}
//

	@RequestMapping(value="/register/", method = RequestMethod.GET)
	public ModelAndView register(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("register");
		return modelAndView;
	}


	@RequestMapping(value = "/register/", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("register");
		} else {
			if (user.getPhone() == null) user.setPhone("");
			user.setActive(true);
			//random password
			String password = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
			user.setPass( bCryptPasswordEncoder.encode(password) );
			userService.addSysUser(user);

			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.addObject("password", password);
			modelAndView.setViewName("register-complite");
		}
		return modelAndView;
	}
	
//	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
//	public ModelAndView home(){
//		ModelAndView modelAndView = new ModelAndView();
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User user = userService.findUserByEmail(auth.getName());
//		modelAndView.addObject("userName", "Welcome " + user.getId() + " (" + user.getEmail() + ")");
//		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
//		modelAndView.setViewName("admin/home");
//		return modelAndView;
//	}
	

}
