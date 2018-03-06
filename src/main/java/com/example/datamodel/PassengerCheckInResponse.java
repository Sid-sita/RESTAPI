package com.example.datamodel;

public class PassengerCheckInResponse {
	String passengerName;
	String ticketVerified;
	
	public PassengerCheckInResponse()
	{
		
	}
	
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getTicketVerified() {
		return ticketVerified;
	}

	public void setTicketVerified(String ticketVerified) {
		this.ticketVerified = ticketVerified;
	}
	
	
}


