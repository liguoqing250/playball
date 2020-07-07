package org.jeecg.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

/**
 * 单数据源配置（jeecg.datasource.open = false时生效）
 * @Author zhoujf
 *
 */
@Configuration
@MapperScan(value={"org.jeecg.modules.**.mapper*"})
public class MybatisPlusConfig {

		//注册分页插件 
		@Bean 
		public PaginationInterceptor paginationInterceptor() {
			return new PaginationInterceptor();
		}
		
		
		@Bean //SQL执行效率查看组件   3.2.0不支持  
		@Profile({"dev","test"})// 设置 dev test 环境开启、保证效率
		public PerformanceInterceptor performanceInterceptor() {
			PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
			performanceInterceptor.setMaxTime(300); // ms设置sql执行的最大时间，如果超过了则不 执行    
			performanceInterceptor.setFormat(true); // 是否格式化代码    
			return performanceInterceptor; 
		}
}
