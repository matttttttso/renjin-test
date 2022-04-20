package com.example.demo;

import java.util.HashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.renjin.eval.EvalException;
import org.renjin.parser.ParseException;
import org.renjin.primitives.matrix.Matrix;
import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.AttributeMap;
import org.renjin.sexp.DoubleArrayVector;
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
			method12(engine);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	private static void method1(ScriptEngine engine) throws ScriptException {
		engine.eval("df <- data.frame(x=1:10, y=(1:10)+rnorm(n=10))");
		engine.eval("print(df)");
		engine.eval("print(lm(y ~ x, df))");
		
//		String stream = context.getResource("classpath:jasperreports/jasper-template.jasper").getURL().getFile();
//		engine.eval(new FileReader("script.R"));
	}
	private static void method2(ScriptEngine engine) throws ScriptException {
		engine.eval("x <- seq(9)");
		engine.eval("print(class(x))");
		engine.eval("dim(x) <- c(3, 3)");
		engine.eval("print(class(x))");
		engine.eval("print(x)");
		
		engine.eval("y <- seq(8)");
		engine.eval("dim(y) <- c(2,2,2)");
		engine.eval("print(class(y))");
		engine.eval("print(y)");
	}
	private static void method3(ScriptEngine engine) throws ScriptException {
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
	}
	private static void method4(ScriptEngine engine) throws ScriptException {
		DoubleVector resDoubleVector = (DoubleVector)engine.eval("a <- 2; b <- 3; a*b");
		System.out.println(resDoubleVector);	// OUTPUT : 6.0
		Vector resVector = (Vector)engine.eval("a <- 2; b <- 3; a*b");
		System.out.println(resVector);			// OUTPUT : 6.0
	}
	
	private static void method5(ScriptEngine engine) throws ScriptException {
		Vector xVector = (Vector)engine.eval("x <- c(6, 7, 8, 9)");
		System.out.println("The vector 'x' has length " + xVector.length());
		for (int i = 0; i < xVector.length(); i++) {
		    System.out.println("Element x[" + (i + 1) + "] is " + xVector.getElementAsDouble(i));
		}
	}
	private static void method6(ScriptEngine engine) throws ScriptException {
		ListVector xListVector =(ListVector)engine.eval("x <- list(name = \"Jane\", age = 23, scores = c(6, 7, 8, 9))");
		System.out.println("List 'x' has length " + xListVector.length());
		// directly access the first (and only) element of the vector 'x$name':
		System.out.println("x$name is '" + xListVector.getElementAsString(0) + "'");
	}
	private static void method7(ScriptEngine engine) throws ScriptException {
		Vector res = (Vector)engine.eval("matrix(seq(9), nrow = 3)");
		if (res.hasAttributes()) {
		    AttributeMap attributes = res.getAttributes();
		    Vector dim = attributes.getDim();
		    if (dim == null) {
		        System.out.println("Result is a vector of length " +
		            res.length());

		    } else {
		        if (dim.length() == 2) {
		            System.out.println("Result is a " +
		                dim.getElementAsInt(0) + "x" +
		                dim.getElementAsInt(1) + " matrix.");
		        } else {
		            System.out.println("Result is an array with " +
		                dim.length() + " dimensions.");
		        }
		    }
		}
	}
	private static void method8(ScriptEngine engine) throws ScriptException {
		Vector res = (Vector)engine.eval("matrix(seq(9), nrow = 3)");
		try {
		    Matrix m = new Matrix(res);
		    System.out.println("Result is a " + m.getNumRows() + "x"
		        + m.getNumCols() + " matrix.");
		} catch(IllegalArgumentException e) {
		    System.out.println("Result is not a matrix: " + e);
		}
	}
	private static void method9(ScriptEngine engine) throws ScriptException {
		ListVector model = (ListVector)engine.eval("x <- 1:10; y <- x*3; lm(y ~ x)");
		Vector coefficients = model.getElementAsVector("coefficients");
		// same result, but less convenient:
		// int i = model.indexOfName("coefficients");
		// Vector coefficients = (Vector)model.getElementAsSEXP(i);

		System.out.println("intercept = " + coefficients.getElementAsDouble(0));
		System.out.println("slope = " + coefficients.getElementAsDouble(1));
	}
	private static void method10(ScriptEngine engine) throws ScriptException {
		try {
		    engine.eval("x <- 1 +/ 1");
		} catch (ParseException e) {
		    System.out.println("R script parse error: " + e.getMessage());
		}
	}
	private static void method11(ScriptEngine engine) throws ScriptException {
		try {
		    engine.eval("stop(\"Hello world!\")");
		} catch (EvalException e) {
		    // getCondition() returns the condition as an R list:
		    Vector condition = (Vector)e.getCondition();
		    // the first element of the string contains the actual error message:
		    String msg = condition.getElementAsString(0);
		    System.out.println("The R script threw an error: " + msg);
		}
	}
	private static void method12(ScriptEngine engine) throws ScriptException {
		engine.put("x", 4);
		engine.put("y", new double[] { 1d, 2d, 3d, 4d });
		engine.put("z", new DoubleArrayVector(1,2,3,4,5));
		engine.put("hashMap", new HashMap());
		// some R magic to print all objects and their class with a for-loop:
		engine.eval("for (obj in ls()) { " +
		    "cmd <- parse(text = paste('typeof(', obj, ')', sep = ''));" +
		    "cat('type of ', obj, ' is ', eval(cmd), '\\n', sep = '') }");
	}
}
