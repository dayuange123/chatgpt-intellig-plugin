package com.lzy.component;


import javax.swing.*;

/**
 *
 */
public class ChatgptSettingsForm {


	private JTextField apiKeyField;
	private JPanel panel;
	private JLabel apiKeyLabel;




	public JPanel getPanel() {
		return panel;
	}

	public void set(ChatgptSettings data) {
		apiKeyField.setText(data.getApiKey());
	}

	public ChatgptSettings get() {
		ChatgptSettings data = new ChatgptSettings();

		data.setApiKey(apiKeyField.getText().trim());

		return data;
	}



}
