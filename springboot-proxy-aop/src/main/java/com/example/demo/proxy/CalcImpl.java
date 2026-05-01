package com.example.demo.proxy;

import org.springframework.stereotype.Component;

// 計算器實現並交給Spring管理
@Component
public class CalcImpl implements Calc{

	@Override
	public Integer add(Integer x, Integer y) {
		// 業務邏輯
		return x+y;
	}

	@Override
	public Integer div(Integer x, Integer y) {
		// 業務邏輯
		return x/y;
	}

}
