package com.coffeegrains.serviceutils.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResponseEntityContainer implements Serializable {

	private static final long serialVersionUID = -8738149412927258141L;
	
	private List<ResponseEntityError> errors;
	private Object body;
	
	public ResponseEntityContainer(Object body, List<ResponseEntityError> errors) {
		this.body = body;
		this.errors = errors;
	}
	
	public ResponseEntityContainer(Object body) {
		this.body = body;
		this.errors = null;
	}
	
	public ResponseEntityContainer(List<ResponseEntityError> errors) {
		this.body = null;
		this.errors = errors;
	}
	
	public ResponseEntityContainer(ResponseEntityError error) {
		this.errors = new ArrayList<ResponseEntityError>();
		this.errors.add(error);
		this.body = null;
	}

}
