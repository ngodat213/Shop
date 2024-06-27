package com.hutech.Shop.Services;

import com.hutech.Shop.model.Slide;
import com.hutech.Shop.repository.SlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlideService {
    @Autowired
    private SlideRepository slideRepository;

    public List<Slide> findAllSlide() {
        return slideRepository.findAll();
    }
}
