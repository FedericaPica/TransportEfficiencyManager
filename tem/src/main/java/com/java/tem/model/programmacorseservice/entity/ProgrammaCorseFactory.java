package com.java.tem.model.programmacorseservice.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.tem.model.programmacorseservice.repository.Strategy;
import com.java.tem.model.programmacorseservice.repository.StrategyType;


@Component
public class ProgrammaCorseFactory {
	
//	private Strategy strategy;
	
	private Map<StrategyType, Strategy> strategies;


	
	@Autowired
	public ProgrammaCorseFactory(Set<Strategy>strategyType) {
		this.createStrategy(strategyType);
	}
	
	public Strategy findStrategy(StrategyType strategyType) { 
		return strategies.get(strategyType);
	}
	
	private void createStrategy(Set<Strategy> strategySet) {
		this.strategies = new HashMap<StrategyType, Strategy>();
		strategySet.forEach(strategy -> strategies.put(strategy.getStrategyType(), strategy));
	}
	

}
