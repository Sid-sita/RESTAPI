package com.example.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.datamodel.PassengerCheckInRequest;
import com.example.datamodel.PassengerCheckInResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

@RestController
public class PassengerRequestHandler {
	
	@Autowired
	private ObjectMapper mapper;
	
	
	
	@GetMapping("/search/{flightNo}/{passengerName}")
	public PassengerCheckInResponse getHandler(@PathVariable String flightNo,@PathVariable String passengerName) throws IOException
	{
		PassengerCheckInResponse response = new PassengerCheckInResponse();
		response.setPassengerName(passengerName);
		File file = new File(flightNo);
		BufferedReader reader;
		String existing="";
		if(file.exists())
		{
			reader = new BufferedReader(new FileReader(flightNo));
			String temp;
			while((temp=reader.readLine())!=null)
			{
				existing+=temp;
			}
		}
		else
			response.setTicketVerified("false");
		if(existing.contains(passengerName))
			response.setTicketVerified("true");
		else
			response.setTicketVerified("false");
		return response;
	}
	
	@PostMapping("/passengerInfo")
	public ResponseEntity<Void> postHandler(@RequestBody PassengerCheckInRequest requestBody) throws IOException
	{
		String requestJson =mapper.writeValueAsString(requestBody);
		JsonNode requestDataNode = getJsonNode(requestJson);
		System.out.println(requestJson);
		JsonSchema requestSchemaNode = getSchemaNode(new File("requestSchema.json"));
		if(requestDataNode==null || requestSchemaNode==null)
		{
			System.out.println("one of the nodes was null");
			return ResponseEntity.badRequest().build();
		}
		else if(!isRequestValid(requestDataNode,requestSchemaNode))
		{
			System.out.println("validation failed");
			return ResponseEntity.badRequest().build();
		}
		else 
		{
			System.out.println("everything is fine!!");
			File file = new File(requestBody.getFlightNo());
			BufferedReader reader;
			String existing = "";
			if (file.exists()) {
				reader = new BufferedReader(new FileReader(requestBody.getFlightNo()));
				String temp;
				while ((temp = reader.readLine()) != null) {
					existing += temp+"\n";
				}
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(requestBody.getFlightNo()));
			writer.write(existing  + requestBody.getPassengerName() );
			writer.close();
			return ResponseEntity.ok().build();
		}
	}
	
	private JsonNode getJsonNode(String jsonText)
	{
		try {
			return JsonLoader.fromString(jsonText);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	private JsonNode getJsonNode(File jsonFile)
	{
		try {
			return JsonLoader.fromFile(jsonFile);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	private JsonSchema getSchemaNode(File SchemaFile)
	{
		JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		try {
			return factory.getJsonSchema(getJsonNode(SchemaFile));
		} catch (ProcessingException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	private boolean isRequestValid(JsonNode data, JsonSchema schema)
	{
		try {
			return schema.validate(data).isSuccess();
		} catch (ProcessingException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}
