package com.lzy.component;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 *
 *
 * @author: liuzhiyuan
 * @date: 2022/09/02
 * @menu:
 **/
@State(name = "ChatgptSettings", storages = @Storage("$APP_CONFIG$/ChatgptSettings.xml"))
public class ChatgptSettings implements PersistentStateComponent<ChatgptSettings> {


	/**
	 * token
	 */
	private String apiKey = "";


	public static ChatgptSettings getInstance() {

		return ServiceManager.getService(ChatgptSettings.class);
	}

	public static void storeInstance(@NotNull ChatgptSettings state) {

		getInstance().loadState(state);
	}

	public boolean isValidate() {
		return StringUtils.isNotEmpty(apiKey);
	}

	@Nullable
	@Override
	public ChatgptSettings getState() {
		return this;
	}

	@Override
	public void loadState(@NotNull ChatgptSettings state) {
		XmlSerializerUtil.copyBean(state, this);
	}


	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ChatgptSettings that = (ChatgptSettings) o;
		return  Objects.equals(apiKey, that.apiKey) ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apiKey);
	}
}
