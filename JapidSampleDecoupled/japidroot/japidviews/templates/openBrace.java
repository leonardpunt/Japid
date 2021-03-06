package japidviews.templates;
import java.util.*;
import java.io.*;
import cn.bran.japid.tags.Each;
import static play.templates.JavaExtensions.*;
import static cn.bran.play.JapidPlayAdapter.*;
import static play.data.validation.Validation.*;
import japidviews._layouts.*;
import play.i18n.Messages;
import play.data.validation.Validation;
import play.mvc.Scope.*;
import models.*;
import play.data.validation.Error;
import play.i18n.Lang;
import japidviews._tags.*;
import play.mvc.Http.*;
import controllers.*;
//
// NOTE: This file was generated from: japidviews/templates/openBrace.html
// Change to this file will be lost next time the template file is compiled.
//
public class openBrace extends cn.bran.play.JapidTemplateBase
{
	public static final String sourceTemplate = "japidviews/templates/openBrace.html";
	{
		putHeader("Content-Type", "text/html; charset=utf-8");
		setContentType("text/html; charset=utf-8");
	}

// - add implicit fields with Play

	final play.mvc.Http.Request request = play.mvc.Http.Request.current(); 
	final play.mvc.Http.Response response = play.mvc.Http.Response.current(); 
	final play.mvc.Scope.Session session = play.mvc.Scope.Session.current();
	final play.mvc.Scope.RenderArgs renderArgs = play.mvc.Scope.RenderArgs.current();
	final play.mvc.Scope.Params params = play.mvc.Scope.Params.current();
	final play.data.validation.Validation validation = play.data.validation.Validation.current();
	final cn.bran.play.FieldErrors errors = new cn.bran.play.FieldErrors(validation);
	final play.Play _play = new play.Play(); 

// - end of implicit fields with Play 


	public openBrace() {
		super(null);
	}
	public openBrace(StringBuilder out) {
		super(out);
	}
/* based on https://github.com/branaway/Japid/issues/12
 */
	public static final String[] argNames = new String[] {/* args of the template*/ };
	public static final String[] argTypes = new String[] {/* arg types of the template*/ };
	public static final Object[] argDefaults= new Object[] { };
	public static java.lang.reflect.Method renderMethod = getRenderMethod(japidviews.templates.openBrace.class);

	{
		setRenderMethod(renderMethod);
		setArgNames(argNames);
		setArgTypes(argTypes);
		setArgDefaults(argDefaults);
		setSourceTemplate(sourceTemplate);
	}
////// end of named args stuff

	public cn.bran.japid.template.RenderResult render() {
		long t = -1;
		try {super.layout();} catch (RuntimeException e) { super.handleException(e);} 
		return new cn.bran.japid.template.RenderResultPartial(getHeaders(), getOut(), t, actionRunners, sourceTemplate);
	}
	@Override protected void doLayout() {
		beginDoLayout(sourceTemplate);
//------
p("<p>hello</p>\n");// line 1
		int i = 3;// line 2
if(asBoolean(true)) {// line 3
    while (i-- > 0) {// line 4
		p("        <p>in while ");// line 4
		p(i);// line 5
		p("</p>\n" + 
"    ");// line 5
		}// line 6
		p("    <p>good</p>\n");// line 6
		}// line 8
		p("\n" + 
"<p>\n");// line 8
		for(i =0; i < 4; i++){// line 11
		p("    ");// line 11
		p(i);// line 12
		p(", \n");// line 12
		}// line 13
		p("<p/>\n" + 
"<p>good2</p>\n" + 
"\n");// line 13
		if (true) {// line 17
		p("    <p>good 3</p>\n");// line 17
		}// line 19
		p("<p/>\n" + 
"<p>\n" + 
"the result is ");// line 19
		if(asBoolean(true)) {// line 22
		p("got you!");// line 22
		}// line 22
		p(".\n" + 
"</p>\n");// line 22
		
		endDoLayout(sourceTemplate);
	}

}