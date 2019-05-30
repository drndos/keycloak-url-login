/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.emeldi.poc.keycloak;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.KeycloakModelUtils;

public class URLLoginAuthenticator implements Authenticator {

    @Override
    public void action(AuthenticationFlowContext context) {
    }

    @Override
    public void authenticate(AuthenticationFlowContext context) {

        String username = context.getHttpRequest().getUri().getQueryParameters().getFirst("username");
        String password = context.getHttpRequest().getUri().getQueryParameters().getFirst("password");

        if (username == null || password == null) {
            context.attempted();
            return;
        }

        UserModel user = KeycloakModelUtils.findUserByNameOrEmail(context.getSession(), context.getRealm(), username);
        if (user == null) {
            context.failure(AuthenticationFlowError.INVALID_CREDENTIALS);
            return;
        }
        context.setUser(user);
        boolean valid = context.getSession().userCredentialManager().isValid(context.getRealm(), context.getUser(), UserCredentialModel.password(password));
        if (valid) {
            context.success();
        } else {
            context.failure(AuthenticationFlowError.INVALID_CREDENTIALS);
        }
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    }

    @Override
    public void close() {
    }

}
