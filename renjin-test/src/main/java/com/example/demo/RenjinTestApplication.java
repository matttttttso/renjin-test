package com.example.demo;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.DoubleVector;
import org.renjin.sexp.ListVector;
import org.renjin.sexp.SEXP;
import org.renjin.sexp.Vector;
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
			
			engine.eval("x <- seq(9)");
			engine.eval("print(class(x))");
			engine.eval("dim(x) <- c(3, 3)");
			engine.eval("print(class(x))");
			engine.eval("print(x)");
			
			engine.eval("y <- seq(8)");
			engine.eval("dim(y) <- c(2,2,2)");
			engine.eval("print(class(y))");
			engine.eval("print(y)");
			
			// evaluate Renjin code from String:
			SEXP res = (SEXP)engine.eval("a <- 2; b <- 3; a*b");

			// print the result to stdout:
			System.out.println("The result of a*b is: " + res);
			// determine the Java class of the result:
			System.out.println("Java class of 'res' is: " + res.getClass().getName());
			// use the getTypeName() method of the SEXP object to get R's type name:
			System.out.println("In R, typeof(res) would give '" + res.getTypeName() + "'");
			/* OUTPUT CONTENT↓
			 * The result of a*b is: 6.0
			 * Java class of 'res' is: org.renjin.sexp.DoubleArrayVector
			 * In R, typeof(res) would give 'double'
			 */
			
			DoubleVector resDoubleVector = (DoubleVector)engine.eval("a <- 2; b <- 3; a*b");
			System.out.println(resDoubleVector);	// OUTPUT : 6.0
			Vector resVector = (Vector)engine.eval("a <- 2; b <- 3; a*b");
			System.out.println(resVector);			// OUTPUT : 6.0
			
			Vector xVector = (Vector)engine.eval("x <- c(6, 7, 8, 9)");
			System.out.println("The vector 'x' has length " + xVector.length());
			for (int i = 0; i < xVector.length(); i++) {
			    System.out.println("Element x[" + (i + 1) + "] is " + xVector.getElementAsDouble(i));
			}
			
			ListVector xListVector =(ListVector)engine.eval("x <- list(name = \"Jane\", age = 23, scores = c(6, 7, 8, 9))");
			System.out.println("List 'x' has length " + xListVector.length());
			// directly access the first (and only) element of the vector 'x$name':
			System.out.println("x$name is '" + xListVector.getElementAsString(0) + "'");
		} catch (ScriptException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
