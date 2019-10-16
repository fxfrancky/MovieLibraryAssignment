package com.assignment.movielibrary.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {
	  static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	  @Override
	  public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider)
	          throws IOException {
	      try {
	          String s = value.format(DATE_FORMATTER);
	          gen.writeString(s);
	      } catch (DateTimeParseException e) {
	          System.err.println(e);
	          gen.writeString("");
	      }
	  }
	}
