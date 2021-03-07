package com.java.tem.model.programmacorseservice.repository;

import org.springframework.stereotype.Repository;

import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;

@Repository
public interface Strategy {
	public ProgrammaCorse doOperation(ProgrammaCorse programmaCorse);
	
	public StrategyType getStrategyType();
}