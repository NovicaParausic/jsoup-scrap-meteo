package com.scrap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scrap.entity.Limit;
import com.scrap.repository.LimitRepository;
import com.scrap.service.LimitService;

@Service
public class LimitServiceImpl implements LimitService {

	@Autowired
	private LimitRepository limitRepository;
	
	@Override
	public Limit getLimit(Long id) {
		return limitRepository.findById(id).orElse(null);
	}

	@Override
	public Limit save(Limit limit) {
		return limitRepository.save(limit);
	}

	@Override
	public void delete(Long id) {
		limitRepository.deleteById(id);
	}

}
