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

## Testing the /whoami Endpoint

This POC is configured to use **OpenID Connect (OIDC)** in Swagger. This ensures that Swagger uses the Google **ID Token** (which is a JWT) instead of the opaque Access Token.

### Steps to test:

1.  Click the **Authorize** button in Swagger UI.
2.  Select the `openid`, `profile`, and `email` scopes.
3.  Click **Authorize** and complete the Google login.
4.  Swagger will automatically receive the `id_token`.
5.  Now, call the `/whoami` GET endpoint using **Try it out**.
6.  It should return your user claims (name, email, etc.) as JSON!

### Troubleshooting "Malformed token"
If you still receive a "Malformed token" error, it means Swagger sent the opaque `access_token` instead of the `id_token`. This sometimes happens if the browser caches old OAuth sessions.
- Try clearing your browser cache or using an Incognito window.
- Ensure `use-id-token-with-authorization-code-grant: true` is set in your `application.yml`.
