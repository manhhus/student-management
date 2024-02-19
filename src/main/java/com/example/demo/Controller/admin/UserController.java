package com.example.demo.Controller.admin;
//
//import java.util.List;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.example.demo.dto.UserDTO;
//import com.example.demo.entity.User;
//import com.example.demo.mappers.UserMapper;
//import com.example.demo.repositories.UserRepository;
//
//import org.springframework.ui.Model;
//
////import org.springframework.web.bind.annotation.RequestParam;
//
////import jakarta.servlet.http.HttpServletRequest;
//
//@RequestMapping(value = "/admin/users")
//@Controller
//public class UserController {
////	@Autowired
////	private HttpServletRequest request;
//	
//	@GetMapping()
//	public String index() {
//		return "admin/users/index";
//	}
////test truy van csdl
////	@Autowired
////	private UserMapper mapper;
////	@Autowired
////	private UserRepository userRepo;
////	@GetMapping()
////	public String index(Model model) {
////		List<User> listUser = this.userRepo.findAll();
////		model.addAttribute("listUser", listUser);
////		return "admin/users/index";
////	}
//		
////	@GetMapping(params = "id")
////	public String show(@RequestParam(name="id") Integer id) {
//////		this.request.getParameter("id");
////		System.out.print("id: " + id);
////		return "admin/users/show";
////	}
//	
//	
//////	@GetMapping(params = "id")
////	@GetMapping("/{id}")
////	public String show(@PathVariable("id") Integer id) {
//////		this.request.getParameter("id");
////		System.out.print("id: " + id);
////		return "admin/users/show";
////	}
//	
////	@GetMapping(value="{id}")
////	public String show(
////		Model model,
////		@PathVariable("id") User entity
////	) {
////		UserDTO userDTO = this.mapper.convertToDTO(entity);
////		model.addAttribute("user", userDTO);
////
////		return "admin/users/show";
////	}
//	
//	@GetMapping(value = "/create")
//	public String create() {
//		return "admin/users/create";
//	}
//	@PostMapping(value = "/store")
//	public String store() {
//		return "admin/users/store";
//	}
//	@GetMapping(value = "/edit", params = "id")
//	public String edit() {
//		return "admin/users/edit";
//	}
//	@PostMapping(value = "/update", params = "id")
//	public String update() {
//		return "admin/users/update";
//	}
//	@PostMapping(value = "/delete", params = "id")
//	public String delete() {
//		return "admin/users/delete";
//	}
//}
import java.util.Date;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.mappers.UserMapper;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utilities.HashUtil;

@Controller
@RequestMapping(value="/admin/users")
public class UserController {
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private UserRepository userRepo;

	@GetMapping()
	public String index(Model model)
	{
		String sortBy = request.getParameter("sort_by");
		String sortDirection = request.getParameter("sort_direction");
		String pageParam = request.getParameter("page");
		String limitParam = request.getParameter("limit");

		String sortField = sortBy == null ? "id" : sortBy;
		Sort sort = ( sortDirection == null || sortDirection.equals("asc") ) ?
			Sort.by(Direction.ASC, sortField):
			Sort.by(Direction.DESC, sortField);

		int page = pageParam == null ? 0 : Integer.parseInt(pageParam);
		int limit = limitParam == null ? 10 : Integer.parseInt(limitParam);
		Pageable pageable = PageRequest.of(page, limit, sort);
		
		Page pageData = this.userRepo.findAll(pageable);

		model.addAttribute("pageData", pageData);
		
		return "admin/users/index";
	}

	@GetMapping(value="{id}")
	public String show(
		Model model,
		@PathVariable("id") User entity
	) {
		UserDTO userDTO = this.mapper.convertToDTO(entity);
		model.addAttribute("user", userDTO);

		return "admin/users/show";
	}

	@GetMapping(value="/create")
	public String create()
	{
		return "admin/users/create";
	}

	@PostMapping(value="/store")
	public String store(
		Model model,
		@Valid UserDTO user,
		BindingResult result
	) {
		if (result.hasErrors()) {
			// do something
			return "admin/users/create";
		} else {
			User entity = this.mapper.convertToEntity(user);

			String hashedPassword = HashUtil.hash(entity.getPassword());
			entity.setPassword(hashedPassword);

			this.userRepo.save(entity);
			return "redirect:/admin/users";
		}
	}

	@GetMapping(value="/edit/{id}")
	public String edit(
		Model model,
		@PathVariable("id") User entity
	) {
		UserDTO user = this.mapper.convertToDTO(entity);
		model.addAttribute("user", user);
		return "admin/users/edit";
	}

	@PostMapping(value="/update/{id}")
	public String update(
		Model model,
		@Valid UserDTO user,
		BindingResult result
	) {
		if (result.hasErrors()) {
//			return "redirect:/admin/users/edit/1";
			model.addAttribute("errors", result.getAllErrors());
			model.addAttribute("user", user);
			return "admin/users/edit";
		} else {
			User entity = this.mapper.convertToEntity(user);
			this.userRepo.save(entity);
			return "redirect:/admin/users";
		}
	}

	@PostMapping(value="/delete/{id}")
	public String delete(@PathVariable("id") Integer id)
	{
		this.userRepo.deleteById(id);
		return "redirect:/admin/users";
	}
}