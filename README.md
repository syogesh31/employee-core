# Google OAuth2 Spring Boot POC

This project is a Proof of Concept (POC) for a Spring Boot application that uses Google OAuth2 for authentication. It includes a `/whoami` endpoint that returns the details of the authenticated user from the Google JWT token.

## How to get a Google Client ID (FREE - No Credit Card Required)

1.  **Go to the [Google Cloud Console](https://console.cloud.google.com/)**.
2.  **Create a new project**:
    - Click the project dropdown at the top and select "New Project".
    - Give it a name and click "Create".
3.  **Configure the OAuth Consent Screen**:
    - Go to **APIs & Services > OAuth consent screen**.
    - Select **User Type: External** and click **Create**.
    - Fill in "App name", "User support email", and "Developer contact info".
    - Click **Save and Continue** through the "Scopes" and "Test users" pages.
4.  **Create Credentials**:
    - Go to **APIs & Services > Credentials**.
    - Click **+ Create Credentials** at the top and select **OAuth client ID**.
    - Select **Application type: Web application**.
    - **Name**: Spring Boot POC.
    - **Authorized redirect URIs**:
        - Add `http://localhost:8080/swagger-ui/oauth2-redirect.html`
    - Click **Create**.
5.  **Copy your Client ID and Client Secret**.

## How to run the application

1.  Update `src/main/resources/application.yml` with your `client-id` and `client-secret`.
2.  Run the application:
    ```bash
    mvn spring-boot:run
    ```
3.  Open Swagger UI: `http://localhost:8080/swagger-ui.html`
4.  Click **Authorize**:
    - Enter your `client-id`.
    - Enter `client-secret`.
    - Select `openid`, `profile`, and `email` scopes.
5.  Follow the Google login prompt.
6.  Once authorized, use the **Try it out** button on the `/whoami` endpoint.

## Important Note on Google Tokens
Google's **Access Token** (returned by default in the OAuth2 flow) is **opaque** and cannot be decoded as a JWT by the backend.
However, Google also returns an **ID Token** which is a valid JWT.

This POC is configured as a **JWT Resource Server**. If you call `/whoami` from Swagger and get a `401 Unauthorized` or a decoding error, it is because Swagger is sending the opaque `access_token`.

To see the JWT claims in this POC, you can:
1.  Manually copy the `id_token` from the browser's Network tab (after logging in via Swagger).
2.  Use the `id_token` in the "Authorize" header manually.

*In a production application, you would typically use `spring-boot-starter-oauth2-client` for web applications or configure the resource server to validate opaque tokens via Google's userinfo endpoint.*
