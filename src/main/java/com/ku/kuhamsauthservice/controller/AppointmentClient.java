package com.ku.kuhamsauthservice.controller;

import com.ku.kuhamsauthservice.dto.user.DoctorCreateRequest;
import com.ku.kuhamsauthservice.dto.user.PatientCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "appointment-service", url = "${appointment.service.url}")
public interface AppointmentClient {

  @PostMapping("/api/doctors")
  void createDoctor(@RequestBody DoctorCreateRequest dto);

  @PostMapping("/api/patients")
  void createPatient(@RequestBody PatientCreateRequest dto);
}
