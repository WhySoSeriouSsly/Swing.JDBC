package Business.Validation;

import javax.swing.JTextField;

public class Validator {
	public static Boolean validate(JTextField textfield) {
		
			if (textfield.getText().trim().isEmpty()) {
				return false;
			}
		return true;
	}
}
