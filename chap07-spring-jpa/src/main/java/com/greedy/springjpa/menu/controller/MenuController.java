package com.greedy.springjpa.menu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greedy.springjpa.menu.dto.CategoryDTO;
import com.greedy.springjpa.menu.dto.MenuDTO;
import com.greedy.springjpa.menu.service.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController {

	private MenuService menuService;
	
	public MenuController(MenuService menuService) {
		this.menuService = menuService;
	}
	
	@GetMapping("/{menuCode}")
	public String findMenuByCode(@PathVariable int menuCode, Model model) {
		
		MenuDTO menu = menuService.findMenuByCode(menuCode);
		//System.out.println("menu = " + menu);
		
		model.addAttribute("menu", menu);
		
		return "menu/one";
	}
	
	@GetMapping("/list")
	public String findAllMenu(Model model) {
		
		List<MenuDTO> menuList = menuService.findAllMenu();
		
		model.addAttribute("menuList", menuList);
		
		return "menu/list";
	}
	
	@GetMapping("/regist")
	public void registPage() {}
	
	@GetMapping(value="category", produces="application/json; charset=UTF-8")
	@ResponseBody
	public List<CategoryDTO> findCategoryList() {
		
		return menuService.findAllCategory();
	}
	
	@PostMapping("/regist")
	public String registMenu(@ModelAttribute MenuDTO newMenu) {
		
		menuService.registNewMenu(newMenu);
		
		return "redirect:/menu/list";
	}
	
	@GetMapping("/modify")
	public void modifyPage() {}
	
	@PostMapping("/modify")
	public String menuModify(@ModelAttribute MenuDTO menu) {
		
		menuService.modifyMenu(menu);
		
		return "redirect:/menu/" + menu.getMenuCode();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
