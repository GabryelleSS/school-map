package br.com.senac.school.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Funções para se aplicar máscaras aos controles do JavaFX
 *
 * @author Paulo Henrique Luvisoto - paulobitfranca@gmail.com
 */
public class MaskFX {

	private static List<KeyCode> ignoreKeyCodes = new ArrayList<>();

	public static void ignoreKeys(TextField textField) {
		textField.addEventFilter(KeyEvent.KEY_PRESSED, (EventHandler) new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent keyEvent) {
				if (ignoreKeyCodes.contains(keyEvent.getCode())) {
					keyEvent.consume();
				}
			}
		});
	}

	// xxxxx-xxxxx-xxxxx-xxxxx
	public static void serialTextField(final TextField textField) {
		MaskFX.maxField(textField, 23);
		textField.lengthProperty()
				.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
					if (newValue.intValue() < 24) {
						String value = textField.getText();
						value = value.replaceAll("[^\\w]", "");
						value = value.replaceFirst("(\\w{5})(\\w)", "$1-$2");
						value = value.replaceFirst("(\\w{5})\\-(\\w{5})(\\w)", "$1-$2-$3");
						value = value.replaceFirst("(\\w{5})\\-(\\w{5})\\-(\\w{5})(\\w)", "$1-$2-$3-$4");
						textField.setText(value.toUpperCase());
						MaskFX.positionCaret(textField);
					}
				});
	}

	public static void dateField(final TextField textField) {
		MaskFX.maxField(textField, 10);
		textField.lengthProperty().addListener((ChangeListener) new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() < 11) {
					String value = textField.getText();
					value = value.replaceAll("[^0-9]", "");
					value = value.replaceFirst("(\\d{2})(\\d)", "$1/$2");
					value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d)", "$1/$2/$3");
					textField.setText(value);
					MaskFX.positionCaret(textField);
				}
			}
		});
	}

	public static void dateField(DatePicker datePicker) {

		datePicker.getEditor().setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando
				if (datePicker.getEditor().getText().length() == 3) {
					datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, 2));
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				}
				if (datePicker.getEditor().getText().length() == 6) {
					datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, 5));
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				}

			} else { // escrevendo

				if (datePicker.getEditor().getText().length() == 10)
					event.consume();

				if (datePicker.getEditor().getText().length() == 2) {
					datePicker.getEditor().setText(datePicker.getEditor().getText() + "/");
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				}
				if (datePicker.getEditor().getText().length() == 5) {
					datePicker.getEditor().setText(datePicker.getEditor().getText() + "/");
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				}

			}
		});

		datePicker.getEditor().setOnKeyReleased((KeyEvent evt) -> {

			if (!datePicker.getEditor().getText().matches("\\d/*")) {
				datePicker.getEditor().setText(datePicker.getEditor().getText().replaceAll("[^\\d/]", ""));
				datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
			}
		});

	}

	public static void mascaraCPF(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 4) {
					textField.setText(textField.getText().substring(0, 3));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 8) {
					textField.setText(textField.getText().substring(0, 7));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 12) {
					textField.setText(textField.getText().substring(0, 11));
					textField.positionCaret(textField.getText().length());
				}

			} else { // escrevendo

				if (textField.getText().length() == 14)
					event.consume();

				if (textField.getText().length() == 3) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 7) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 11) {
					textField.setText(textField.getText() + "-");
					textField.positionCaret(textField.getText().length());
				}

			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d.-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d.-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	public static String formattedCep(String cep) {
		String parte1, parte2;
		if (cep.toString().length() < 8) {
			parte1 = "0".concat(cep.substring(0, 4));
			parte2 = cep.substring(4);

		} else {
			parte1 = cep.substring(0, 5);
			parte2 = cep.substring(5);
		}

		return parte1.concat("-").concat(parte2);
	}

	public static void numericField(final TextField textField) {
		textField.lengthProperty().addListener((ChangeListener) new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				char ch;
				if (newValue.intValue() > oldValue.intValue()
						&& ((ch = textField.getText().charAt(oldValue.intValue())) < '0' || ch > '9')) {
					textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
				}
			}
		});
	}

	public static void monetaryField(final TextField textField) {
		textField.setAlignment(Pos.CENTER_RIGHT);
		textField.lengthProperty().addListener((observable, oldValue, newValue) -> {
			String value = textField.getText();
			value = value.replaceAll("[^0-9]", "");
			value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
			value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
			value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
			value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
			value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
			textField.setText(value);
			MaskFX.positionCaret(textField);
			textField.textProperty().addListener((ChangeListener) new ChangeListener<String>() {

				public void changed(ObservableValue<? extends String> observableValue, String oldValue,
						String newValue) {
					if (newValue.length() > 17) {
						textField.setText(oldValue);
					}
				}
			});
		});
		textField.focusedProperty().addListener((observableValue, aBoolean, fieldChange) -> {
			int length;
			if (!(fieldChange || (length = textField.getText().length()) <= 0 || length >= 3)) {
				textField.setText(textField.getText() + "00");
			}
		});
	}

	public static BigDecimal monetaryValueFromField(TextField textField) {
		if (textField.getText().isEmpty()) {
			return null;
		}
		BigDecimal retorno = BigDecimal.ZERO;
		NumberFormat nf = NumberFormat.getNumberInstance();
		try {
			Number parsedNumber = nf.parse(textField.getText());
			retorno = new BigDecimal(parsedNumber.toString());
		} catch (ParseException ex) {
			Logger.getLogger(MaskFX.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	public static void cpfCnpjField(TextField textField) {
		MaskFX.maxField(textField, 18);
		textField.lengthProperty().addListener((observableValue, number, number2) -> {
			String value = textField.getText();
			if (number2.intValue() <= 14) {
				value = value.replaceAll("[^0-9]", "");
				value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
				value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
				value = value.replaceFirst("(\\d{3})(\\d)", "$1-$2");
			} else {
				value = value.replaceAll("[^0-9]", "");
				value = value.replaceFirst("(\\d{2})(\\d)", "$1.$2");
				value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
				value = value.replaceFirst("(\\d{3})(\\d)", "$1/$2");
				value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
			}
			textField.setText(value);
			MaskFX.positionCaret(textField);
		});
	}

	public static void cepField(TextField textField) {
		MaskFX.maxField(textField, 9);

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 6) {
					textField.setText(textField.getText().substring(0, 5));
					textField.positionCaret(textField.getText().length());
				}

			} else { // escrevendo

				if (textField.getText().length() == 9)
					event.consume();

				if (textField.getText().length() == 5) {
					textField.setText(textField.getText() + "-");
					textField.positionCaret(textField.getText().length());
				}

			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	public static void foneField(TextField textField) {
		MaskFX.maxField(textField, 14);
		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}
			try {

				if (event.getCharacter().trim().length() == 0) { // apagando

					if (textField.getText().length() == 10 && textField.getText().substring(9, 10).equals("-")) {
						textField.setText(textField.getText().substring(0, 9));
						textField.positionCaret(textField.getText().length());
					}
					if (textField.getText().length() == 9 && textField.getText().substring(8, 9).equals("-")) {
						textField.setText(textField.getText().substring(0, 8));
						textField.positionCaret(textField.getText().length());
					}
					if (textField.getText().length() == 4) {
						textField.setText(textField.getText().substring(0, 3));
						textField.positionCaret(textField.getText().length());
					}
					if (textField.getText().length() == 1) {
						textField.setText("");
					}

				} else {

					String value = textField.getText();
					value = value.replaceAll("[^0-9]", "");
					int tam = value.length();
					value = value.replaceFirst("(\\d{2})(\\d)", "($1)$2");
					value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
					if (tam > 10) {
						value = value.replaceAll("-", "");
						value = value.replaceFirst("(\\d{5})(\\d)", "$1-$2");
					}
					textField.setText(value);
					MaskFX.positionCaret(textField);

				}

			} catch (Exception ex) {
			}
		});

	}

	public static void cpfField(TextField textField) {
		MaskFX.maxField(textField, 14);
		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 4) {
					textField.setText(textField.getText().substring(0, 3));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 8) {
					textField.setText(textField.getText().substring(0, 7));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 12) {
					textField.setText(textField.getText().substring(0, 11));
					textField.positionCaret(textField.getText().length());
				}

			} else { // escrevendo

				if (textField.getText().length() == 14)
					event.consume();

				if (textField.getText().length() == 3) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 7) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 11) {
					textField.setText(textField.getText() + "-");
					textField.positionCaret(textField.getText().length());
				}

			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d.-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d.-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	public static void cnpjField(TextField textField) {
		MaskFX.maxField(textField, 18);
		textField.lengthProperty().addListener((observableValue, number, number2) -> {
			String value = textField.getText();
			value = value.replaceAll("[^0-9]", "");
			value = value.replaceFirst("(\\d{2})(\\d)", "$1.$2");
			value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
			value = value.replaceFirst("(\\d{3})(\\d)", "$1/$2");
			value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
			textField.setText(value);
			MaskFX.positionCaret(textField);
		});
	}

	private static void positionCaret(TextField textField) {
		Platform.runLater(() -> {
			if (textField.getText().length() != 0) {
				textField.positionCaret(textField.getText().length());
			}
		});
	}

	public static void maxField(TextField textField, Integer length) {
		textField.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && newValue.length() > length) {
				textField.setText(oldValue);
			}
		});
	}

	public static String onlyDigitsValue(TextField field) {
		String result = field.getText();
		if (result == null) {
			return null;
		}
		return result.replaceAll("[^0-9]", "");
	}

	public static String onlyAlfaNumericValue(TextField field) {
		String result = field.getText();
		if (result == null) {
			return null;
		}
		return result.replaceAll("[^0-9]", "");
	}

	static {
		Collections.addAll(ignoreKeyCodes, new KeyCode[] { KeyCode.F1, KeyCode.F2, KeyCode.F3, KeyCode.F4, KeyCode.F5,
				KeyCode.F6, KeyCode.F7, KeyCode.F8, KeyCode.F9, KeyCode.F10, KeyCode.F11, KeyCode.F12 });
	}

}