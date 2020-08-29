package Business.Validation;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Core.Utilities.Messages.Messages;

public class Validator  {    
	public static Boolean validate(JTextField textfield){                

	    if(textfield.getText().trim().isEmpty()){
	    	return false;
	    }
	    return true;
	}
}

