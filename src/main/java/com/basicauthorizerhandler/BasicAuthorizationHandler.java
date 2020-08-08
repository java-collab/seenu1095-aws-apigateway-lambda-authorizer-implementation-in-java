package com.demo.basicauthorizationhandler;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.uber.eats.framework.common.CustomLogger;

/**
 * Handles Requests Authorization.
 * 
 * @author srinivas.shanigarapu
 * @since 2020-08-06
 */
public class BasicAuthorizationHandler implements RequestHandler<APIGatewayProxyRequestEvent, AuthorizerResponse> {

	/**
	 * Declaring log as variable
	 */
	static final Logger log = LogManager.getLogger(BasicAuthorizationHandler.class.getName());

	public AuthorizerResponse handleRequest(APIGatewayProxyRequestEvent request, Context context) {

		AuthorizerResponse authorizerResponse = new AuthorizerResponse();

		String requestId = context.getAwsRequestId();

		CustomLogger logger = new CustomLogger(log, requestId);

		logger.info("Loading Java Authorization Handler -->");

		Map<String, String> headers = request.getHeaders();
		String authorization = headers.get("Authorization");
		
		if (StringUtils.isBlank(authorization))
			throw new RuntimeException("Unauthorized");

		String[] encodedCreds = authorization.split("\\s+");
		byte[] decodedCred = Base64.getDecoder().decode(encodedCreds[1]);
		String decodedCredAsString = new String(decodedCred);
		String[] plainCredentials = decodedCredAsString.split(":");

		String username = plainCredentials[0];
		String password = plainCredentials[1];

		if ((username.equals("****") && password.equals("****")) == false)
			throw new RuntimeException("Unauthorized");
		
		String tmp[] = request.getMethodArn().split(":");		
		String awsRegion = tmp[3];

		APIGatewayProxyRequestEvent.ProxyRequestContext proxyContext = request.getRequestContext();
        APIGatewayProxyRequestEvent.RequestIdentity identity = proxyContext.getIdentity();
		
		String apiArn = "arn:aws:execute-api:" + awsRegion + ":" + proxyContext.getAccountId() + ":" +
				proxyContext.getApiId() +"/" + proxyContext.getStage() + "/"+proxyContext.getHttpMethod()+"/";
		
		List<Statement>  statements = new ArrayList<Statement>();		
		Statement statement = new Statement();
		
		statement.setEffect("Allow");
		statement.setResource(apiArn);
		statements.add(statement);
		
		PolicyDocument policyDocument = new PolicyDocument();		
		policyDocument.setStatement(statements);
		
		authorizerResponse.setPrincipalId(identity.getAccountId());
		authorizerResponse.setPolicyDocument(policyDocument);
		
		return authorizerResponse;

	}
}