/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.app.web;

import com.tc.website.common.config.Global;
import com.tc.website.common.persistence.Page;
import com.tc.website.common.utils.IdGen;
import com.tc.website.common.utils.StringUtils;
import com.tc.website.common.utils.VDirUtil;
import com.tc.website.common.web.BaseController;
import com.tc.website.modules.app.entity.Config;
import com.tc.website.modules.app.service.ConfigService;
import com.tc.website.modules.app.utils.ConfigUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 参数配置Controller
 * @author Jack Yu
 * @version 2018-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/app/config")
public class ConfigController extends BaseController {

	@Autowired
	private ConfigService configService;
	
	@ModelAttribute
	public Config get(@RequestParam(required=false) String id) {
		Config entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = configService.get(id);
		}
		if (entity == null){
			entity = new Config();
		}
		return entity;
	}
	
	@RequiresPermissions("app:config:view")
	@RequestMapping(value = {"list", ""})
	public String list(Config config, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Config> page = configService.findPage(new Page<Config>(request, response), config); 
		model.addAttribute("page", page);
		return "modules/app/configList";
	}

	@RequiresPermissions("app:config:view")
	@RequestMapping(value = "form")
	public String form(Config config, Model model) {
		List<Config> list = configService.findList(null);
		if(list.size() != 1){
			for (Config item : list){
				configService.delete(item);
			}
			config = new Config();
			config.setId(IdGen.uuid());
			config.setIsNewRecord(true);
			config.setDiskPath("D:\\");
			config.setTempPath("temp\\");
			config.setIsClearTemp(1);
			configService.save(config);
			list = configService.findList(null);
		}else {
			config = list.get(0);
		}
		model.addAttribute("config", config);
		return "modules/app/configForm";
	}

	@RequiresPermissions("app:config:edit")
	@RequestMapping(value = "save")
	public String save(Config config, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, config)){
			return form(config, model);
		}
		configService.save(config);
		// 创建虚拟目录
		VDirUtil.addVirtualDir(config.getDiskPath(), "/file");
		ConfigUtil.refreshConfig(configService); // 更新缓存
		addMessage(redirectAttributes, "保存参数配置成功");
		return "modules/app/configForm";
	}
	
	@RequiresPermissions("app:config:edit")
	@RequestMapping(value = "delete")
	public String delete(Config config, RedirectAttributes redirectAttributes) {
		configService.delete(config);
		ConfigUtil.refreshConfig(configService); // 更新缓存
		addMessage(redirectAttributes, "删除参数配置成功");
		return "redirect:"+Global.getAdminPath()+"/app/config/?repage";
	}
}