package Business.Validation;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Core.Utilities.Messages.Messages;

public class ValidatorToolTest {
	public static Boolean validate(JTextField textfield) {		
		ArrayList<Boolean> result=new ArrayList<Boolean>();
		if (textfield.getText().trim().isEmpty()) {
			result.add(false);
		}
		
		if(result.contains(false)) {
			JOptionPane.showMessageDialog(null, Messages.ValidationException);
			return false;
		}
		return true;
	}
}
