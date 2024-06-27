package com.hutech.Shop.Services;

import com.hutech.Shop.model.Blog;
import com.hutech.Shop.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> findAllBlogs(){
        return blogRepository.findAll();
    }
}
