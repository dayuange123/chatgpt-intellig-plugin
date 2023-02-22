package com.lzy.util;

import com.intellij.notification.*;
import com.intellij.openapi.project.Project;

public class NotificationUtil {


	private static final NotificationGroup NOTIFICATION_GROUP = new NotificationGroup("Java2Json.NotificationGroup", NotificationDisplayType.BALLOON, true);

	public static void notifyError(Project project, String msg) {
		Notification error = NOTIFICATION_GROUP.createNotification(msg, NotificationType.ERROR);
		Notifications.Bus.notify(error, project);
	}


	public static void notifyInfo(Project project, String msg) {
		Notification error = NOTIFICATION_GROUP.createNotification(msg, NotificationType.INFORMATION);
		Notifications.Bus.notify(error, project);
	}
}
