package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
    "category",
    "months",
    "state"
})
public class CsvData {
  @JsonProperty("category")
  private String category;
  @JsonProperty("months")
  private int months;
  @JsonProperty("state")
  private int state;
}
