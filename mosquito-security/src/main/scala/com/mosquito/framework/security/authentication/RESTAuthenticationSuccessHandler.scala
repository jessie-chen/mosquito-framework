package com.mosquito.framework.security.authentication

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler

class RESTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  override def onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication): Unit =
    clearAuthenticationAttributes(request)
}
