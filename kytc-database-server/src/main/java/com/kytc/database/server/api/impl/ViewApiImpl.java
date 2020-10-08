package com.kytc.database.server.api.impl;

import java.util.List;

import com.kytc.database.server.service.ViewService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("viewController")
@RequestMapping("view")
@Api(tags = "视图操作")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class ViewApiImpl {
	private final ViewService viewService;
	@GetMapping(value="list")
	public List<String> list(@RequestParam("database") String database){
		return viewService.list(database);
	}
}
