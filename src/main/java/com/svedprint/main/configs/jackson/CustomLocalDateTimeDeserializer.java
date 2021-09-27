package com.svedprint.main.configs.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

	public LocalDateTime deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {

		if (parser.hasTokenId(JsonTokenId.ID_STRING)) {
			String date = parser.getText().trim();

			if (date.isEmpty()) {
				return null;
			}

			return LocalDateTime.ofInstant(Instant.parse(date), ZoneOffset.UTC);

		}

		return null;
	}
}
