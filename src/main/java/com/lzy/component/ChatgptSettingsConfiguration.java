package com.lzy.component;

import com.google.gson.Gson;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nls.Capitalization;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

/**
 *
 */
public class ChatgptSettingsConfiguration implements Configurable {

	private ChatgptSettingsForm form;

	@Nls(capitalization = Capitalization.Title)
	@Override
	public String getDisplayName() {
		return "Chatgpt Config";
	}

	@Nullable
	@Override
	public JComponent createComponent() {
		if (form == null) {
			form = new ChatgptSettingsForm();
		}
		return form.getPanel();
	}

	@Override
	public boolean isModified() {
		ChatgptSettings settings = ChatgptSettings.getInstance();
		ChatgptSettings chatgptSettings = form.get();
		return !chatgptSettings.equals(settings);
	}

	@Override
	public void apply() {
		ChatgptSettings data = form.get();
		System.out.println(new Gson().toJson(data));
		ChatgptSettings.storeInstance(data);
	}

	@Override
	public void reset() {
		ChatgptSettings settings = ChatgptSettings.getInstance();
		if (Objects.nonNull(settings)) {
			form.set(settings);
		}
	}

	@Override
	public void disposeUIResources() {
		this.form = null;
	}
}
