/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.test.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tc.website.common.utils.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tc.website.common.config.Global;
import com.tc.website.common.persistence.Page;
import com.tc.website.common.web.BaseController;
import com.tc.website.common.utils.StringUtils;
import com.tc.website.modules.test.entity.HospitalArchivesInfo;
import com.tc.website.modules.test.service.HospitalArchivesInfoService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 医院信息Controller
 * @author cyh
 * @version 2018-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/test/hospitalArchivesInfo")
public class HospitalArchivesInfoController extends BaseController {

	@Autowired
	private HospitalArchivesInfoService hospitalArchivesInfoService;
	
	@ModelAttribute
	public HospitalArchivesInfo get(@RequestParam(required=false) String id) {
		HospitalArchivesInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hospitalArchivesInfoService.get(id);
		}
		if (entity == null){
			entity = new HospitalArchivesInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("test:hospitalArchivesInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(HospitalArchivesInfo hospitalArchivesInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HospitalArchivesInfo> page = hospitalArchivesInfoService.findPage(new Page<HospitalArchivesInfo>(request, response), hospitalArchivesInfo);
		Date date = null;
		if (hospitalArchivesInfo.getBeginDate() == null){
			date = DateUtils.setDays(new Date(), 1);
			hospitalArchivesInfo.setBeginDate(DateUtils.formatDate(date, "yyyy-MM-dd"));
		}
		if (hospitalArchivesInfo.getEndDate() == null){
			;
			hospitalArchivesInfo.setEndDate(DateUtils.formatDate(DateUtils.addDays(DateUtils.addMonths(date, 1), -1), "yyyy-MM-dd"));
		}
		model.addAttribute("hospitalArchivesInfo", hospitalArchivesInfo);
		model.addAttribute("page", page);

		return "modules/test/hospitalArchivesInfoList";
	}

	@RequiresPermissions("test:hospitalArchivesInfo:view")
	@RequestMapping(value = "form")
	public String form(HospitalArchivesInfo hospitalArchivesInfo, Model model) {
		model.addAttribute("hospitalArchivesInfo", hospitalArchivesInfo);
		return "modules/test/hospitalArchivesInfoForm";
	}

	@RequiresPermissions("test:hospitalArchivesInfo:edit")
	@RequestMapping(value = "save")
	public String save(HospitalArchivesInfo hospitalArchivesInfo, Model model, RedirectAttributes redirectAttributes) {
		//获取当前时间
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String format = df.format(new Date());
		try {
			date = df.parse(format);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(hospitalArchivesInfo.getCreatedTime() == null){
			hospitalArchivesInfo.setCreatedTime(date);
		}
		hospitalArchivesInfo.setUpdatedTime(date);
		hospitalArchivesInfo.setDataState("Enable");
		if (!beanValidator(model, hospitalArchivesInfo)){
			return form(hospitalArchivesInfo, model);
		}
		hospitalArchivesInfoService.save(hospitalArchivesInfo);
		addMessage(redirectAttributes, "保存医院信息表成功");
		return "redirect:"+Global.getAdminPath()+"/test/hospitalArchivesInfo/?repage";
	}
	
	@RequiresPermissions("test:hospitalArchivesInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(HospitalArchivesInfo hospitalArchivesInfo, RedirectAttributes redirectAttributes) {
		hospitalArchivesInfoService.delete(hospitalArchivesInfo);
		addMessage(redirectAttributes, "删除医院信息表成功");
		return "redirect:"+Global.getAdminPath()+"/test/hospitalArchivesInfo/?repage";
	}

}