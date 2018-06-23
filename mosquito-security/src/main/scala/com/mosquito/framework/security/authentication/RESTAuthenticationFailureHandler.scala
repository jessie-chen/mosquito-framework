package com.mosquito.framework.security.authentication

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler

class RESTAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  override def onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException): Unit =
    super.onAuthenticationFailure(request, response, exception)

}
