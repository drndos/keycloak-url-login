# PoC for URL Login authenticator for Keycloak

Allows users to authenticate through a URL using their username and password.

Quite possibly not safe, just a Proof of Concept !!! 

Use at own risk, only if you know what you are doing.

## Usage

1. Deploy to Keycloak:

    `mvn clean install wildfly:deploy`
    
    or 
   
    `docker cp url-login.jar <DOCKER CONTAINER>:/opt/rh-sso-7.2/standalone/deployments/url-login.jar`

3. Configure realm authentication flow

   * Create copy of Browser flow
   * Click on Actions next to "Copy Of Browser Forms" and click "Add execution"
   * Add "URL Login"
   * Set requirement "Alternative" on "URL Login" executor
   * Click on bindings and switch "Browser flow" to "Copy of browser flow" 
