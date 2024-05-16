package com.ttodampartners.ttodamttodam.domain.participation.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ParticipationStatus {
  WAIT("대기"),
  ACCEPT("수락"),
  REFUSE("거절");

  private final String label;

  public static ParticipationStatus fromLabel(String label) {
    return switch (label) {
      case "대기" -> ParticipationStatus.WAIT;
      case "수락" -> ParticipationStatus.ACCEPT;
      case "거절" -> ParticipationStatus.REFUSE;
      default -> throw new IllegalStateException("Unexpected value: " + label);
    };
  }
}