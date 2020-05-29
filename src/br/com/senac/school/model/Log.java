package br.com.senac.school.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Log extends RecursiveTreeObject<Log> {

	StringProperty id;
	StringProperty data;
	StringProperty evento;
	StringProperty level;
	StringProperty ip;

	public Log(String data, String evento, String level, String ip) {
		this.evento = new SimpleStringProperty(evento);
		this.data = new SimpleStringProperty(data);
		this.ip = new SimpleStringProperty(ip);
		this.level = new SimpleStringProperty(level);
	}

	public void setId(String id) {
		this.id = new SimpleStringProperty(id);
	}

	public StringProperty getId() {
		return id;
	}

	public StringProperty getData() {
		return data;
	}

	public StringProperty getEvento() {
		return evento;
	}

	public StringProperty getLevel() {
		return level;
	}

	public StringProperty getIp() {
		return ip;
	}

}
