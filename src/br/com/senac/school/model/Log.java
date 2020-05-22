package br.com.senac.school.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Log extends RecursiveTreeObject<Log> {

	StringProperty id;
	StringProperty data;
	StringProperty logger;
	StringProperty level;
	StringProperty message;

	public Log(String id, String data, String logger, String level, String message) {
		this.id = new SimpleStringProperty(id);
		this.data = new SimpleStringProperty(data);
		this.logger = new SimpleStringProperty(logger);
		this.level = new SimpleStringProperty(level);
		this.message = new SimpleStringProperty(message);
	}

	public StringProperty getId() {
		return id;
	}

	public StringProperty getData() {
		return data;
	}

	public StringProperty getLogger() {
		return logger;
	}

	public StringProperty getLevel() {
		return level;
	}

	public StringProperty getMessage() {
		return message;
	}

}
