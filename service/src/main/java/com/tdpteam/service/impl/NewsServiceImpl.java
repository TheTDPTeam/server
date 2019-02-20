package com.tdpteam.service.impl;

import com.tdpteam.repo.entity.News;
import com.tdpteam.repo.repository.NewsRepository;
import com.tdpteam.service.interf.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<News> getNewsList() {
        return newsRepository.findAllByOrderByUpdatedAtDesc();
    }
}
