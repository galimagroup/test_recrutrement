package sow.issa.recrutement.services;

import sow.issa.recrutement.models.request.RegisterRequest;
import sow.issa.recrutement.models.request.SignInRequest;
import sow.issa.recrutement.models.response.SignInResponse;

public interface AuthenticationService {
    void register(RegisterRequest request);
    SignInResponse singIn(SignInRequest signInRequest);
}
