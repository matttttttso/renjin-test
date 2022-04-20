package com.example.demo;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.renjin.script.RenjinScriptEngineFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RenjinTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RenjinTestApplication.class, args);
		// create a script engine manager:
		RenjinScriptEngineFactory factory = new RenjinScriptEngineFactory();
		// create a Renjin engine:
		ScriptEngine engine = factory.getScriptEngine();
		// ... put your Java code here ...
		try {
			engine.eval("df <- data.frame(x=1:10, y=(1:10)+rnorm(n=10))");
			engine.eval("print(df)");
			engine.eval("print(lm(y ~ x, df))");
			
//			String stream = context.getResource("classpath:jasperreports/jasper-template.jasper").getURL().getFile();
//			engine.eval(new FileReader("script.R"));
		} catch (ScriptException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
