package com.example.wallet.converter;

import java.util.function.Function;

public interface Converter<REQUEST, PROCESS, PERSISTENT, RESPONSE> extends BaseConverter<REQUEST, PROCESS> {
  
  Function<PROCESS, PERSISTENT> dtoToPersistent();
  
  Function<PERSISTENT, PROCESS> persistentToDto();

  Function<PROCESS, RESPONSE> dtoToResponse();
}
