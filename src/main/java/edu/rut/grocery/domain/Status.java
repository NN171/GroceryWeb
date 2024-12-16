package edu.rut.grocery.domain;

public enum Status {
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