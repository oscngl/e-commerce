package com.osc.ecommerce.business.abstracts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TokenService {

    void refreshToken(HttpServletRequest request, HttpServletResponse response);

}
