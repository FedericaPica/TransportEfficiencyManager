package com.java.tem.model.programmacorseservice.repository;

import org.springframework.stereotype.Repository;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;

@Repository
public class ProgrammaManualeMaker implements Strategy {
	
	@Override
	public ProgrammaCorse doOperation() {
		System.out.println("Test");
		
		return new ProgrammaCorse();
	}
	
	@Override
	public StrategyType getStrategyType() { 
		return StrategyType.Manuale;
	}
	
}
