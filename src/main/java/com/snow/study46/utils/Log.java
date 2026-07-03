package com.snow.study46.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
  public static void info(String str) {
    Logger log = LoggerFactory.getLogger(Log.class);
    log.info(str);
  }
}
