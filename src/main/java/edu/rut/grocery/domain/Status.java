package edu.rut.grocery.domain;

import java.io.Serializable;

public enum Status implements Serializable {
	IN_PROGRESS("В сборке"),
	CREATED("Создан"),
	FINISHED("Завершен");

	private final String comment;

	Status(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}
}