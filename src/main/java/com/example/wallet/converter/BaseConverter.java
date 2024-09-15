package com.example.wallet.converter;

import java.util.function.Function;

public interface BaseConverter<REQUEST, PROCESS> {
  
  Function<REQUEST, PROCESS> requestToDto();
}
