/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.app.web;

import com.tc.website.common.config.Global;
import com.tc.website.common.persistence.Page;
import com.tc.website.common.utils.RandomUtils;
import com.tc.website.common.utils.StringUtils;
import com.tc.website.common.web.BaseController;
import com.tc.website.modules.app.entity.ApiKey;
import com.tc.website.modules.app.service.ApiKeyService;
import com.tc.website.modules.sys.utils.UserUtils;
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

/**
 * 应用信息Controller
 * @author Jack Yu
 * @version 2018-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/app/apiKey")
public class ApiKeyController extends BaseController {

	@Autowired
	private ApiKeyService apiKeyService;
	
	@ModelAttribute
	public ApiKey get(@RequestParam(required=false) String id) {
		ApiKey entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = apiKeyService.get(id);
		}
		if (entity == null){
			entity = new ApiKey();
		}
		return entity;
	}
	
	@RequiresPermissions("app:apiKey:view")
	@RequestMapping(value = {"list", ""})
	public String list(ApiKey apiKey, HttpServletRequest request, HttpServletResponse response, Model model) {
		apiKey.setUser(UserUtils.getUser()); // 当前用户
		Page<ApiKey> page = apiKeyService.findPage(new Page<ApiKey>(request, response), apiKey); 
		model.addAttribute("page", page);
		return "modules/app/apiKeyList";
	}

	@RequiresPermissions("app:apiKey:view")
	@RequestMapping(value = "form")
	public String form(ApiKey apiKey, Model model) {
		model.addAttribute("apiKey", apiKey);
		return "modules/app/apiKeyForm";
	}

	@RequiresPermissions("app:apiKey:edit")
	@RequestMapping(value = "save")
	public String save(ApiKey apiKey, Model model, RedirectAttributes redirectAttributes) {
		if(StringUtils.isEmpty(apiKey.getId())){
			// 新添加
			apiKey.setUser(UserUtils.getUser());
			apiKey.setApiKey(RandomUtils.generateMixString(30));
			apiKey.setApiSecret(RandomUtils.generateMixString(30));
		}
		if (!beanValidator(model, apiKey)){
			return form(apiKey, model);
		}
		apiKeyService.save(apiKey);
		addMessage(redirectAttributes, "保存API Key成功");
		return "redirect:"+Global.getAdminPath()+"/app/apiKey/?repage";
	}
	
	@RequiresPermissions("app:apiKey:edit")
	@RequestMapping(value = "delete")
	public String delete(ApiKey apiKey, RedirectAttributes redirectAttributes) {
		apiKeyService.delete(apiKey);
		addMessage(redirectAttributes, "删除API Key成功");
		return "redirect:"+Global.getAdminPath()+"/app/apiKey/?repage";
	}

}