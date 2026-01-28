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

## Testing the /whoami Endpoint (Handling "Malformed token")

Google returns an **Access Token** (opaque string) and an **ID Token** (JWT). Swagger UI sends the Access Token by default, which causes an "invalid_token: Malformed token" error in this POC because the backend expects a JWT.

### Steps to test successfully in Swagger:

1.  Click **Authorize** in Swagger UI.
2.  Log in via the `google_oauth` section.
3.  Once authorized, **open your browser's Developer Tools (F12)**.
4.  Go to the **Network** tab and find the request to `token` (on the `oauth2.googleapis.com` domain).
5.  In the **Response** body of that request, copy the long `id_token` string.
6.  Go back to the Swagger **Authorize** dialog.
7.  Scroll down to the **manual_jwt** section.
8.  Paste the `id_token` you copied into the "Value" box and click **Authorize**.
9.  Now, call the `/whoami` endpoint. It will succeed and return the JSON claims from your Google JWT!

*Note: In a production web app, the frontend would handle extracting the ID Token and sending it to the backend.*
