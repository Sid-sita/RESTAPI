package com.example.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.datamodel.PassengerCheckInRequest;
import com.example.datamodel.PassengerCheckInResponse;

@RestController
public class PassengerRequestHandler {
	
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
		File file = new File(requestBody.getFlightNo());
		BufferedReader reader;
		String existing="";
		if(file.exists())
		{
			reader = new BufferedReader(new FileReader(requestBody.getFlightNo()));
			String temp;
			while((temp=reader.readLine())!=null)
			{
				existing+=temp;
			}
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(requestBody.getFlightNo()));
		writer.write(existing+requestBody.getPassengerName()+"\n");
		writer.close();
		return ResponseEntity.ok().build();
	}
}
