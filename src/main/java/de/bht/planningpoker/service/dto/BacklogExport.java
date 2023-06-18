package de.bht.planningpoker.service.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.core.io.Resource;

@Value
@RequiredArgsConstructor
public class BacklogExport {

    String fileName;
    Resource resource;

}
