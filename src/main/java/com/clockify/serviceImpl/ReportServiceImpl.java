package com.clockify.serviceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.clockify.controller.ProjectReportDto;
import com.clockify.controller.UserReportDto;
import com.clockify.service.ReportService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportServiceImpl implements ReportService {

	private static final String CLOCKIFY_API_URL = "https://reports.api.clockify.me/v1/workspaces/666a8510c827a2561783e6cf/reports/summary";
	private static final String API_KEY = "OTBiNWNkODAtZWI4Ny00OGRhLWE5NWMtNzAxMjlkOWNhMGEx";

	@Override
	public List<ProjectReportDto> getProjectReports(String projectName, String dateRangeStart, String dateRangeEnd) {
		String response = fetchClockifyData(dateRangeStart, dateRangeEnd);
		return parseProjectReports(response, projectName);
	}

	@Override
	public List<UserReportDto> getUserReports(String userName, String dateRangeStart, String dateRangeEnd) {
		String response = fetchClockifyData(dateRangeStart, dateRangeEnd);
		return parseUserReports(response, userName);
	}

	private String fetchClockifyData(String dateRangeStart, String dateRangeEnd) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-api-key", API_KEY);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Convert dates from input format to LocalDate objects
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate startLocalDate = LocalDate.parse(dateRangeStart, inputFormatter);
		LocalDate endLocalDate = LocalDate.parse(dateRangeEnd, inputFormatter);

		// Set start of day and end of day in UTC
		ZonedDateTime startDateTime = startLocalDate.atStartOfDay(ZoneOffset.UTC);
		ZonedDateTime endDateTime = endLocalDate.atTime(23, 59, 59, 999000000).atZone(ZoneOffset.UTC);

		// Format to the required UTC format
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String startUtc = startDateTime.format(outputFormatter);
		String endUtc = endDateTime.format(outputFormatter);

		String requestBody = String.format(
				"{ \"dateRangeEnd\": \"%s\", \"dateRangeStart\": \"%s\", \"summaryFilter\": { \"groups\": [ \"PROJECT\", \"USER\" ] } }",
				endUtc, startUtc);

		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(CLOCKIFY_API_URL, HttpMethod.POST, entity,
				String.class);
		return response.getBody();
	}

	private List<ProjectReportDto> parseProjectReports(String response, String projectName) {
		List<ProjectReportDto> projectReports = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(response);
			JsonNode groupOne = root.path("groupOne");
			for (JsonNode projectNode : groupOne) {
				String name = projectNode.path("name").asText();
				if (projectName == null || name.equalsIgnoreCase(projectName)) {
					ProjectReportDto dto = new ProjectReportDto();
					dto.setProjectName(name);
					dto.setCurrency(projectNode.path("currency").asText());
					dto.setDuration(projectNode.path("duration").asInt());
					dto.setAmount(projectNode.path("amount").asDouble());
					projectReports.add(dto);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectReports;
	}

	private List<UserReportDto> parseUserReports(String response, String userName) {
		List<UserReportDto> userReports = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(response);
			JsonNode groupOne = root.path("groupOne");
			for (JsonNode projectNode : groupOne) {
				JsonNode children = projectNode.path("children");
				for (JsonNode userNode : children) {
					String name = userNode.path("name").asText();
					if (userName == null || name.equalsIgnoreCase(userName)) {
						UserReportDto dto = new UserReportDto();
						dto.setUserName(name);
						dto.setDuration(userNode.path("duration").asInt());
						dto.setAmount(userNode.path("amount").asDouble());
						userReports.add(dto);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userReports;
	}

}
