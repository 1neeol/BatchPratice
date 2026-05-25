package org.neeol.bachpratice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalyticsDTO {

    private String channel;
    private String status;
    private Long total;
}
