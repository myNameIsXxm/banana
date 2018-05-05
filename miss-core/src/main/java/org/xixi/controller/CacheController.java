package org.xixi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xixi.cache.LRUCache;
import org.xixi.exception.AuthorizationException;
import org.xixi.exception.SqlException;
@Controller
public class CacheController {
	@RequestMapping(value = "/cache.html")
	@ResponseBody
	public String index(String userId) throws AuthorizationException,SqlException {
		return LRUCache.getInstance().getStats().toString();
	}
}