package com.mosquito.framework.security.zuul

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import com.typesafe.scalalogging.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.util.{PatternMatchUtils, StringUtils}

/**
  * OAuth2HeaderFilter
  *
  * @author 01372461
  */
class OAuth2HeaderFilter extends ZuulFilter {

  val AUTHORIZATION_HEADER = "Authorization"
  val BEARER_TYPE = "Bearer"

  @Value("${oauth2-header-filter.ignore-paths}")
  val ignorePaths: String = null

  val log: Logger = Logger[OAuth2HeaderFilter]


  override def filterType(): String = "route"

  override def filterOrder(): Int = 0

  override def shouldFilter(): Boolean = {
    val context = RequestContext.getCurrentContext
    val req = context.getRequest
    val uri = req.getRequestURI
    val paths = StringUtils.trimArrayElements(StringUtils.commaDelimitedListToStringArray(ignorePaths))
    for (pattern <- paths) {
      if (PatternMatchUtils.simpleMatch(pattern, uri)) {
        log.debug("{} matched the ignored path, pass. ignore-paths: {}", uri, paths)
        return false
      }
    }
    log.debug("{} do not matched any ignored path. ignore-paths: {}", uri, paths)
    true
  }

  override def run(): AnyRef = {
    val context = RequestContext.getCurrentContext
    val req = context.getRequest
    val authHeader = req.getHeader(AUTHORIZATION_HEADER)
    if (authHeader != null && authHeader.startsWith(BEARER_TYPE)) {
      log.debug("Check Authorization header ({}) success.", authHeader)
    } else {
      // reject request// reject request
      log.debug("Not Authorization header found. Reject.")
      context.setSendZuulResponse(false)
      context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value)
      context.setResponseBody("Access is denied, authentication is required.")
    }
    null
  }
}
