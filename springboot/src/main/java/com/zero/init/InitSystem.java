package com.zero.init;



import com.zero.entity.Film;
import com.zero.service.FilmService;
import com.zero.service.LinkService;
import com.zero.service.WebSiteInfoService;
import com.zero.service.WebSiteService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * 初始化加载数据
 * @author Administrator
 *
 */
@Component
public class InitSystem implements ServletContextListener,ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext=applicationContext;
	}
	
	/**
	 * 加载数据到application缓存中
	 */
	public void loadData(ServletContext application){
		FilmService filmService=(FilmService) applicationContext.getBean("filmService");
		LinkService linkService=(LinkService) applicationContext.getBean("linkService");
		WebSiteService webSiteService=(WebSiteService) applicationContext.getBean("webSiteService");
		WebSiteInfoService webSiteInfoService=(WebSiteInfoService) applicationContext.getBean("webSiteInfoService");
		
		application.setAttribute("newestInfoList", webSiteInfoService.list(null, 1, 10)); // 最新10条电影动态
		Film film=new Film();
		film.setHot(1);
		application.setAttribute("newestHotFilmList", filmService.list(film, 1, 10)); // 获取最新10条热门电影
		application.setAttribute("newestIndexHotFilmList", filmService.list(film, 1, 32)); // 获取最新德35条热门电影 首页显示用到
		application.setAttribute("newestWebSiteList", webSiteService.list(null, 1, 10)); // 最新10条电影网站收录
		application.setAttribute("newestFilmList", filmService.list(null, 1, 10)); // 最新10条电影收录
		application.setAttribute("linkList", linkService.listAll()); // 查询所有友情链接
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		loadData(sce.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	
}
