package com.wso2telco.dep.common.mediation;

import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

import com.wso2telco.dep.common.mediation.service.APIService;

public class NotificationURLInfoStatusUpdaterMediator extends AbstractMediator {

	private void setErrorInContext(MessageContext synContext, String messageId,
			String errorText, String errorVariable, String httpStatusCode,
			String exceptionType) {

		synContext.setProperty("messageId", messageId);
		synContext.setProperty("errorText", errorText);
		synContext.setProperty("errorVariable", errorVariable);
		synContext.setProperty("httpStatusCode", httpStatusCode);
		synContext.setProperty("exceptionType", exceptionType);
	}

	public boolean mediate(MessageContext synContext) {

		try {

			String notifyurldid = (String) synContext
					.getProperty("NOTIFY_URL_ID");

			APIService apiService = new APIService();
			apiService.updateNotificationURLInformationStatus(Integer
					.parseInt(notifyurldid));
		} catch (Exception e) {

			log.error("error in NotificationURLInfoStatusUpdaterMediator mediate : "
					+ e.getMessage());
			setErrorInContext(
					synContext,
					"SVC0001",
					"A service error occurred. Error code is %1",
					"An internal service error has occured. Please try again later.",
					"500", "SERVICE_EXCEPTION");
			synContext.setProperty("INTERNAL_ERROR", "true");
		}

		return true;
	}
}
