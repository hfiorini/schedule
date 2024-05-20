package com.teladoc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
@Setter
public class AvailableSlot {

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;


    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof AvailableSlot availableSlot)) {
            return false;
        }


        return this.dayOfWeek == availableSlot.dayOfWeek && this.startTime == availableSlot.startTime
                && this.endTime == availableSlot.endTime;
    }
}
