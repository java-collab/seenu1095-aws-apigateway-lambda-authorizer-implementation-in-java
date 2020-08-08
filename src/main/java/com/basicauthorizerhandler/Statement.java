package com.demo.basicauthorizationhandler;

import com.google.gson.annotations.SerializedName;

public class Statement {

	@SerializedName("Action")
	private final String action = "execute-api:Invoke";
	
	@SerializedName("Effect")
	private String effect;
	
	@SerializedName("Resource")
	private String resource;

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getAction() {
		return action;
	}

	@Override
	public String toString() {
		return "Statement [action=" + action + ", effect=" + effect + ", resource=" + resource + "]";
	}

}
