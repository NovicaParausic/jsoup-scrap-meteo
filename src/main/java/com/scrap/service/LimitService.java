package com.scrap.service;

import com.scrap.entity.Limit;

public interface LimitService {

	Limit getLimit(Long id);
	
	Limit save(Limit limit);
	
	void delete(Long id);
}
