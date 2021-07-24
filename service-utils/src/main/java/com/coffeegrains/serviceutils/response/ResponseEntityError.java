package com.coffeegrains.serviceutils.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseEntityError implements Serializable {

	private static final long serialVersionUID = -8738149412927258141L;
	
	private String errorCode;
	private String errorMessage;
	
	public ResponseEntityError(ResponseEntityErrorEnumerable enumerable) {
		this.errorCode = enumerable.getResponseEntityError().getErrorCode();
		this.errorMessage = enumerable.getResponseEntityError().getErrorMessage();
	}

}
