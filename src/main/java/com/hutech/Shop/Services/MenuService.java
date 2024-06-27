package com.hutech.Shop.Services;

import com.hutech.Shop.model.Menu;
import com.hutech.Shop.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> findAllMenu() {
        return menuRepository.findAll();
    }
}
