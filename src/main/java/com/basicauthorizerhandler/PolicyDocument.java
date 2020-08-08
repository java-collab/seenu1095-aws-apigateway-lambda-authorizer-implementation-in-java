package com.demo.basicauthorizationhandler;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PolicyDocument {

	@SerializedName("Version")
	private final String version = "2012-10-17";
	
	@SerializedName("Statement")
	private List<Statement> statement;

	public List<Statement> getStatement() {
		return statement;
	}

	public void setStatement(List<Statement> statement) {
		this.statement = statement;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return "PolicyDocument [version=" + version + ", statement=" + statement + "]";
	}

}