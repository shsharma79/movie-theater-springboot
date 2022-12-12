package com.jpmc.theater.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Configuration class responsible for <a href="https://github.com/FasterXML/jackson">jackson</a>
 * {@link ObjectMapper} configuration
 */
@Configuration
public class ObjectMapperConfig {
  public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm";
  public static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern(DATETIME_FORMAT);
  public static LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER =
      new LocalDateTimeSerializer(DATE_TIME_FORMATTER);

  public static LocalDateTimeDeserializer LOCAL_DATETIME_DESERIALIZER =
      new LocalDateTimeDeserializer(DATE_TIME_FORMATTER);

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    /*
     * Jackson library doesn't support java.time.* classes by default.
     * We need to register JavaTimeModule explicitly, so that we can
     * serialize and de-serialize java time classes as per the above datetime format
     */
    JavaTimeModule module = new JavaTimeModule();
    module.addSerializer(LOCAL_DATETIME_SERIALIZER);
    module.addDeserializer(LocalDateTime.class, LOCAL_DATETIME_DESERIALIZER);
    return new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .registerModule(module)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
  }
}
