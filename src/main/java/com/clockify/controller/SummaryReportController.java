package com.clockify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clockify.service.ReportService;

@RestController
@RequestMapping("/api")
public class SummaryReportController {

	@Autowired
	private ReportService reportService;

	@GetMapping("/reports/projects")
	public List<ProjectReportDto> getProjectReports(@RequestParam(required = false) String projectName,
			@RequestParam String dateRangeStart, @RequestParam String dateRangeEnd) {
		return reportService.getProjectReports(projectName, dateRangeStart, dateRangeEnd);
	}

	@GetMapping("reports/users")
	public List<UserReportDto> getUserReports(@RequestParam(required = false) String userName,
			@RequestParam String dateRangeStart, @RequestParam String dateRangeEnd) {
		return reportService.getUserReports(userName, dateRangeStart, dateRangeEnd);
	}
}
