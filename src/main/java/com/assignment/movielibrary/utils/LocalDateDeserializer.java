package com.assignment.movielibrary.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

	  @Override
	  public LocalDate deserialize(JsonParser p, DeserializationContext ctx)
	          throws IOException {
	      String str = p.getText();
	      try {
	          return LocalDate.parse(str, LocalDateSerializer.DATE_FORMATTER);
	      } catch (DateTimeParseException e) {
	          System.err.println(e);
	          return null;
	      }
	  }
	}