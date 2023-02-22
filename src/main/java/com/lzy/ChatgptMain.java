
package com.lzy;


import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import com.lzy.component.ChatgptSettings;
import com.lzy.util.NotificationUtil;
import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.entity.common.Choice;
import com.unfbx.chatgpt.entity.completions.CompletionResponse;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


public class ChatgptMain extends PsiElementBaseIntentionAction {


	public static String calculateSplitText(Document document, int statementOffset, String addition) {

		String splitText = "";
		int lineNumber = document.getLineNumber(statementOffset);
		int lineStartOffset = document.getLineStartOffset(lineNumber);
		int lineEndOffset = document.getLineEndOffset(lineNumber);
		int cur = lineStartOffset;
		String text = document.getText(new TextRange(cur, cur + 1));

		while (text.equals(" ") || text.equals("\t")) {
			splitText = text + splitText;
			cur++;
			if (cur > lineEndOffset) {
				break;
			}
			text = document.getText(new TextRange(cur, cur + 1));
		}
		return splitText;
	}


	@Override
	public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {

		ChatgptSettings service = ServiceManager.getService(ChatgptSettings.class);
		if (!service.isValidate()) {
			NotificationUtil.notifyError(project, "please check chatgpt apikey config");
			return;
		}

		PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);
		PsiFile containingFile = element.getContainingFile();
		int textOffset = element.getTextOffset();
		String selectedText = editor.getSelectionModel().getSelectedText();

		OpenAiClient openAiClient = new OpenAiClient(service.getApiKey(), 10, 10, 10);
		CompletionResponse completions = openAiClient.completions(selectedText);
		Choice[] choices = completions.getChoices();

		Document document = psiDocumentManager.getDocument(containingFile);
		String s = calculateSplitText(document, textOffset, "");
		StringBuilder sb = new StringBuilder();
		for (Choice choice : choices) {
			char[] chars = choice.getText().toCharArray();
			for (char aChar : chars) {
				sb.append(aChar);
				if (aChar == '\n') {
					sb.append(s);
				}
			}
		}
		document.insertString(textOffset, sb.toString());
		if (document != null) {
			psiDocumentManager.doPostponedOperationsAndUnblockDocument(document);
			psiDocumentManager.commitDocument(document);
			FileDocumentManager.getInstance().saveDocument(document);
		}
	}

	@Override
	public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
		return true;
	}

	@Override
	public @NotNull @Nls(capitalization = Nls.Capitalization.Sentence) String getFamilyName() {
		return "Chatgpt Search";
	}

	@Override
	public @Nls(capitalization = Nls.Capitalization.Sentence) @NotNull String getText() {
		return "Chatgpt Search";
	}
}
