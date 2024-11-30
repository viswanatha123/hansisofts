package com.DIC.model;

import java.io.Serializable;

public class SMSModel implements Serializable{
	
	
	private int smsId;
	private int statusCode;
	private String returnStatus;
	private String requestId;
	private String status;
	private String toNumber;
	private String agentName;
	private String custName;
	private String custNumber;
	private String comment;
	private String createDate;
	public int getSmsId() {
		return smsId;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public String getReturnStatus() {
		return returnStatus;
	}
	public String getRequestId() {
		return requestId;
	}
	public String getStatus() {
		return status;
	}
	public String getToNumber() {
		return toNumber;
	}
	public String getAgentName() {
		return agentName;
	}
	public String getCustName() {
		return custName;
	}
	public String getCustNumber() {
		return custNumber;
	}
	public String getComment() {
		return comment;
	}

	public void setSmsId(int smsId) {
		this.smsId = smsId;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public void setCustNumber(String custNumber) {
		this.custNumber = custNumber;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


}
