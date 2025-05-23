package com.learn.ecom.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class utils {
    public static <T, U> U convertModel(T source, Class<U> targetClass) {
        try {
            U target = targetClass.getDeclaredConstructor().newInstance();
            java.lang.reflect.Field[] sourceFields = source.getClass().getDeclaredFields();
            java.lang.reflect.Field[] targetFields = targetClass.getDeclaredFields();

            for (java.lang.reflect.Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                for (java.lang.reflect.Field targetField : targetFields) {
                    targetField.setAccessible(true);
                    if (sourceField.getName().equals(targetField.getName()) &&
                        sourceField.getType().equals(targetField.getType())) {
                        targetField.set(target, sourceField.get(source));
                    }
                }
            }
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Error converting models", e);
        }
    }

    public static <T, U> java.util.List<U> convertModelList(java.util.List<T> sourceList, Class<U> targetClass) {
        java.util.List<U> targetList = new java.util.ArrayList<>();
        for (T source : sourceList) {
            targetList.add(convertModel(source, targetClass));
        }
        return targetList;
    }

    public static <T> void updateModel(T source, T target) {
        try {
            java.lang.reflect.Field[] sourceFields = source.getClass().getDeclaredFields();
            java.lang.reflect.Field[] targetFields = target.getClass().getDeclaredFields();

            for (java.lang.reflect.Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                for (java.lang.reflect.Field targetField : targetFields) {
                    targetField.setAccessible(true);
                    if (sourceField.getName().equals(targetField.getName()) &&
                        sourceField.getType().equals(targetField.getType())) {
                        Object value = sourceField.get(source);
                        if (value != null) { // Only update non-null values
                            targetField.set(target, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating model", e);
        }
    }

    public static String getAuth0TokenFromPropertiesTest() {
        try {
            java.util.Properties properties = new java.util.Properties();
            try (java.io.InputStream input = utils.class.getClassLoader().getResourceAsStream("application-test.properties")) {
                if (input == null) {
                    throw new RuntimeException("Unable to find application.properties");
                }
                properties.load(input);
            }
            String clientId = System.getenv("OKTA_OAUTH2_CLIENT_ID");
            if (clientId == null || clientId.isEmpty()) {
                log.error("Environment variable OKTA_OAUTH2_CLIENT_ID is null or empty");
            }

            String clientSecret = System.getenv("OKTA_OAUTH2_CLIENT_SECRET");
            if (clientSecret == null || clientSecret.isEmpty()) {
                log.error("Environment variable OKTA_OAUTH2_CLIENT_SECRET is null or empty");
            }

            String audience = System.getenv("OKTA_OAUTH2_AUDIENCE");
            if (audience == null || audience.isEmpty()) {
                log.error("Environment variable OKTA_OAUTH2_AUDIENCE is null or empty");
            }

            String auth0Domain = System.getenv("OKTA_OAUTH2_ISSUER");
            if (auth0Domain == null || auth0Domain.isEmpty()) {
                log.error("Environment variable OKTA_OAUTH2_ISSUER is null or empty");
            }

            if (auth0Domain != null && auth0Domain.endsWith("/")) {
                auth0Domain = auth0Domain.substring(0, auth0Domain.length() - 1);
            }

            java.net.URL url = new java.net.URL(auth0Domain + "/oauth/token");
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String payload = String.format(
                "{\"client_id\":\"%s\",\"client_secret\":\"%s\",\"audience\":\"%s\",\"grant_type\":\"client_credentials\"}",
                clientId, clientSecret, audience
            );

            try (java.io.OutputStream os = connection.getOutputStream()) {
                byte[] input = payload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                try (java.io.BufferedReader br = new java.io.BufferedReader(
                        new java.io.InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    org.json.JSONObject jsonResponse = new org.json.JSONObject(response.toString());
                    return jsonResponse.getString("access_token");
                }
            } else {
                throw new RuntimeException("Failed to get token: HTTP error code " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching Auth0 token", e);
        }
    }
}
