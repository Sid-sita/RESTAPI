package com.example.datamodel;

public class PassengerCheckInRequest {
	private String passengerName;
	private String ticketNo;
	private String flightNo;
	private String departureTime; 
	
	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public PassengerCheckInRequest()
	{
		
	}
	
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	@Override
	public String toString() {
		return "PassengerCheckInRequest [passengerName=" + passengerName + ", ticketNo=" + ticketNo + ", flightNo="
				+ flightNo + ", departureTime=" + departureTime + "]";
	}

	
}

