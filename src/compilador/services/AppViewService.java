package compilador.services;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class AppViewService {
	
	public AppViewService() {
		
	}

	public void configButtonShortcut(JButton btn, String shortcut) {
		Action action = btn.getAction();
		// configure the Action with the accelerator (aka: short cut)
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(shortcut));
		// manually register the accelerator in the button's component input map
		btn.getActionMap().put("myAction", action);
		btn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		        (KeyStroke) action.getValue(Action.ACCELERATOR_KEY), "myAction");
	}
	
	public Component configToolBarButton(Component button, Dimension dimension, Color color) {
		button.setMinimumSize(dimension);
		button.setPreferredSize(dimension);
		button.setMaximumSize(dimension);
		button.setBackground(color);
		return button;
	}
}
