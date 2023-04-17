package com.greedy.springjpa.menu.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greedy.springjpa.menu.dto.CategoryDTO;
import com.greedy.springjpa.menu.dto.MenuDTO;
import com.greedy.springjpa.menu.entity.Category;
import com.greedy.springjpa.menu.entity.Menu;
import com.greedy.springjpa.menu.repository.MenuRepository;

@Service
public class MenuService {
	
	private MenuRepository menuRepository;
	private ModelMapper modelMapper;
	@PersistenceContext	// 스프링 부트가 영속성 컨텍스트를 관리하는 엔티티 매니저를 주입한다.
	private EntityManager entityManager;
	
	public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
		this.menuRepository = menuRepository;
		this.modelMapper = modelMapper;
	}
	
	/* 영속성 객체(엔티티)를 그대로 사용하면 데이터가 훼손 될 가능성이 있으므로 비영속 객체로 변경해서 반환한다. */
	
	public MenuDTO findMenuByCode(int menuCode) {
		
		/* Menu -> MenuDTO로 변환할 수 있는 ModelMapper 라이브러리 의존성 추가 후 사용 */
		return modelMapper.map(menuRepository.findMenuByCode(entityManager, menuCode), MenuDTO.class);
	}
	
	public List<MenuDTO> findAllMenu() {
		
		List<Menu> menuList = menuRepository.findAllMenu(entityManager);
		
		return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
		
	}
	
	public List<CategoryDTO> findAllCategory() {
		
		List<Category> categoryList = menuRepository.findAllCategory(entityManager);
		
		return categoryList.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
	}
	
	/* 스프링에서는 트랜잭션 처리를 지원한다.
	 * 어노테이션으로 @Transactional을 선언하는 선언적 트랜잭션이 보편적인 방식이다.
	 * 클래스 레벨과 메소드 레벨에 작성 될 수 있고 클래스 레벨에 작성 시 하위 모든 메소드에 적용된다.
	 * 어노테이션이 선언 되면 메소드 호출 시 자동으로 프록시 객체가 생성 되며 해당 프록시 객체는 정상 수행 여부에 따라
	 * commit, rollback 처리를 한다. */
	@Transactional
	public void registNewMenu(MenuDTO newMenu) {
		
		menuRepository.registNewMenu(entityManager, modelMapper.map(newMenu, Menu.class));
	}
	
	@Transactional
	public void modifyMenu(MenuDTO menu) {
		
		menuRepository.modifyMenu(entityManager, modelMapper.map(menu, Menu.class));
		
	}
	
}

