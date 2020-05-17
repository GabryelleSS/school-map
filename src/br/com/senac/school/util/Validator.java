package br.com.senac.school.util;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;

public class Validator {

	public static void validate(ValidatorBase validator, IFXValidatableControl... fields) {

		for (IFXValidatableControl field : fields) {
			validateFields(validator, field);
		}

	}

	private static void validateFields(ValidatorBase validator, IFXValidatableControl field) {
		field.setValidators(validator);
		((Node) field).focusedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
			if (!newValue)
				field.validate();
		});
	}
}
