package com.arms.samplespringdata.controller;
import com.arms.samplespringdata.model.SpringDataEntity;
import com.arms.samplespringdata.service.SpringDataService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/arms/sample/springdata")
@RestController
@AllArgsConstructor
public class SampleSpringDataController {

    private final SpringDataService springDataService;


}
