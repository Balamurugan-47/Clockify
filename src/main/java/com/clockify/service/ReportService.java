package com.clockify.service;

import java.util.List;

import com.clockify.controller.ProjectReportDto;
import com.clockify.controller.UserReportDto;

public interface ReportService {

	List<ProjectReportDto> getProjectReports(String projectName, String dateRangeStart, String dateRangeEnd);

	List<UserReportDto> getUserReports(String userName, String dateRangeStart, String dateRangeEnd);

}
