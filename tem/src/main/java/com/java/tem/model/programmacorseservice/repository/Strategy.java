package com.java.tem.model.programmacorseservice.repository;

import com.java.tem.exceptions.GenerationFailedException;
import com.java.tem.model.programmacorseservice.entity.ProgrammaCorse;
import org.springframework.stereotype.Repository;

/** Strategy Interface.
 *
 */
@Repository
public interface Strategy {
  ProgrammaCorse doOperation(ProgrammaCorse programmaCorse) throws GenerationFailedException;

  StrategyType getStrategyType();
}