package com.zero.controller.admin;

import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zero.entity.WebSiteInfo;
import com.zero.init.InitSystem;
import com.zero.service.WebSiteInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



/**
 * 电影动态信息Controller类
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/admin/webSiteInfo")
public class WebSiteInfoAdminController {

	@Resource
	private WebSiteInfoService webSiteInfoService;
	
	@Resource
	private InitSystem initSystem;
	
	/**
	 * 分页查询电影动态信息
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Map<String,Object> list(WebSiteInfo webSiteInfo, @RequestParam(value="page",required=false)Integer page, @RequestParam(value="rows",required=false)Integer rows)throws Exception{
		List<WebSiteInfo> webSiteInfoList=webSiteInfoService.list(webSiteInfo,page,rows);
		Long total=webSiteInfoService.getCount(webSiteInfo);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("rows", webSiteInfoList);
		resultMap.put("total", total);
		return resultMap;
	}
	
	/**
	 * 添加网站动态信息
	 * @param link
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Map<String,Object> save(WebSiteInfo webSiteInfo,HttpServletRequest request)throws Exception{
		webSiteInfo.setPublishDate(new Date());
		Map<String, Object> resultMap = new HashMap<>();
		webSiteInfoService.save(webSiteInfo);
		initSystem.loadData(request.getServletContext());
		resultMap.put("success", true);
		return resultMap;
	}
	
	
	/**
	 * 删除电影动态信息
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String,Object> delete(@RequestParam(value="ids")String ids,HttpServletRequest request)throws Exception{
		String []idsStr=ids.split(",");
		Map<String, Object> resultMap = new HashMap<>();
		for(int i=0;i<idsStr.length;i++){
			webSiteInfoService.delete(Integer.parseInt(idsStr[i]));				
		}
		initSystem.loadData(request.getServletContext());
		resultMap.put("success", true);
		return resultMap;
	}
}
