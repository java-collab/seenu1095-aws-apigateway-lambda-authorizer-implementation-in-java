package com.demo.basicauthorizationhandler;

public class AuthorizerResponse {

	private String principalId;

	private PolicyDocument policyDocument;

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public PolicyDocument getPolicyDocument() {
		return policyDocument;
	}

	public void setPolicyDocument(PolicyDocument policyDocument) {
		this.policyDocument = policyDocument;
	}

	@Override
	public String toString() {
		return "AuthorizerResponse [principalId=" + principalId + ", policyDocument=" + policyDocument + "]";
	}

}