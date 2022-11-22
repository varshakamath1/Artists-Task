/****************************************************/
// Filename: Artist.java
// Created: Varsha Kamath
// Change history:
// 22.11.2022 / Varsha Kamath
/****************************************************/

package com.mercedes.exception;


import jakarta.ws.rs.core.Response;

public interface ExceptionMapper<E extends Throwable> {

	  Response toResponse(E exception);

	}