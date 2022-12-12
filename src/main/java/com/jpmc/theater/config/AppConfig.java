package com.jpmc.theater.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.math.RoundingMode;

/** Application configuration class responsible for defining application level configuration */
@Configuration
public class AppConfig {

  private static int MONEY_SCALE;
  private static RoundingMode MONEY_ROUNDING_MODE;

  public AppConfig(
      @Value("${application.money.scale:2}") int moneyScale,
      @Value("${application.money.rounding:HALF_UP}") RoundingMode moneyRoundingMode) {
    MONEY_SCALE = moneyScale;
    MONEY_ROUNDING_MODE = moneyRoundingMode;
  }

  public static int moneyScale() {
    return MONEY_SCALE;
  }

  public static RoundingMode moneyRoundingMode() {
    return MONEY_ROUNDING_MODE;
  }
}
