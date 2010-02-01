package bran;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.Test;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

import bran.japid.BranTemplateBase;
import bran.japid.DummyUrlMapper;
import bran.japid.SimpleMessageProvider;

import templates.Actions;
import templates.AllPost;
import templates.Msg;
import templates.Posts;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class PerfTests {
	@Test
	public void testSimpleJavaPerf() throws UnsupportedEncodingException, IOException {
		List<Post> posts = createPosts();

		ByteArrayOutputStream baos = new ByteArrayOutputStream(10000);
		long tt = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			// a simple loop without templating
			baos.reset();
			long t = System.currentTimeMillis();
			StringBuffer sb = new StringBuffer(1000);
			for (Post pp : posts) {
				sb.append("你");
				sb.append("Title: " + pp.getTitle());
				sb.append("Author name: " + pp.getAuthor().name);
				sb.append(pp.getAuthor().gender);
				sb.append(pp.getPostedAt());
				sb.append("\n" + "	the real title: 你好\n" + "");
			}
			String string = sb.toString();
			baos.write(string.getBytes("UTF-8"));
			System.out.println(System.currentTimeMillis() - t);
		}
		System.out.println("total time: " + (System.currentTimeMillis() - tt));
	}

	@Test
	public void testJapid () throws InterruptedException {
		List<Post> posts = createPosts();
		AllPost te = new AllPost(new org.apache.commons.io.output.ByteArrayOutputStream());
		long t = System.currentTimeMillis();
		long tt = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
//			System.out.println("run templating: " + i);
//			StringWriter out = new StringWriter(1000);
//			PrintWriter printWriter = new PrintWriter(out);
			te = new AllPost(new org.apache.commons.io.output.ByteArrayOutputStream());
			t = System.currentTimeMillis();
			te.render("抬头", posts);
			System.out.println(System.currentTimeMillis() - t);
			// System.out.println(out.toString());
//			Thread.sleep(5);
		}
		System.out.println("total time: " + (System.currentTimeMillis() - tt));
	}

	/**
	 * @return
	 */
	private static List<Post> createPosts() {
		Author a = new Author();
		a.name = "作者";
		a.birthDate = new Date();
		a.gender = 'm';

		final Post p = new Post();
		p.setAuthor(a);
		p.setContent("这是一个很好的内容开始和结束都很好. ");
		p.setPostedAt(new Date());
		p.setTitle("测试");

		List<Post> posts = new ArrayList<Post>();

		for (int i = 0; i < 500; i++) {
			posts.add(p);
		}
		return posts;
	}

	@Test
	public void simleRun() throws UnsupportedEncodingException {
		Author a = new Author();
		a.name = "作者";
		a.birthDate = new Date();
		a.gender = 'm';

		final Post p = new Post();
		p.setAuthor(a);
		p.setContent("这是一个很好的内容开始和结束都很好. ");
		p.setPostedAt(new Date());
		p.setTitle("测试");

		List<Post> posts = new ArrayList<Post>();

		for (int i = 0; i < 5; i++) {
			posts.add(p);
		}
//		StringWriter out = new StringWriter(1000);
//		PrintWriter printWriter = new PrintWriter(out);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		AllPost te = new AllPost(out);
		long t = System.currentTimeMillis();
		te.render("抬头", posts);
		System.out.println(System.currentTimeMillis() - t);
		System.out.println(out.toString("UTF-8"));
	}

	//
	// @Test
	// public void stringBufferVsPrintWriter() {
	// Author a = new Author();
	// a.name = "作者";
	// a.birthDate = new Date();
	// a.gender = 'm';
	//
	// Post p = new Post();
	// p.setAuthor(a);
	// p.setContent("这是一个很好的内容开始和结束都很好. ");
	// p.setPostedAt(new Date());
	// p.setTitle("测试");
	//
	// List<Post> posts = new ArrayList<Post>();
	//
	// for (int i = 0; i < 1000; i++) {
	// posts.add(p);
	// }
	//
	// {
	// System.out.println("test PrintWriter");
	// StringWriter sw = new StringWriter(1000);
	// PrintWriter pw = new PrintWriter(sw);
	// long t = System.currentTimeMillis();
	// for (Post pp : posts) {
	// pw.println("Title: " + pp.getTitle());
	// pw.println("Author name: " + pp.getAuthor().name);
	// pw.println("Author gender: " + pp.getAuthor().gender);
	// pw.println(JavaExtensions.format(pp.getPostedAt(), ("yy-MMM-dd")));
	// pw.println("the real Title: " + pp.getTitle());
	// }
	// System.out.println(System.currentTimeMillis() - t);
	// }
	//
	// {
	// System.out.println("test StringBuffer");
	// StringBuffer sb = new StringBuffer(1000);
	// long t = System.currentTimeMillis();
	// for (Post pp : posts) {
	// sb.append("Title: " + pp.getTitle());
	// sb.append("Author name: " + pp.getAuthor().name);
	// sb.append("Author gender: " + pp.getAuthor().gender);
	// sb.append(JavaExtensions.format(pp.getPostedAt(), ("yy-MMM-dd")));
	// sb.append("the real Title: " + pp.getTitle());
	// }
	// System.out.println(System.currentTimeMillis() - t);
	// }
	//		
	// System.out.println("Second iteration ----");
	//
	// {
	// System.out.println("test PrintWriter");
	// StringWriter sw = new StringWriter(1000);
	// PrintWriter pw = new PrintWriter(sw);
	// long t = System.currentTimeMillis();
	// for (Post pp : posts) {
	// pw.println("Title: " + pp.getTitle());
	// pw.println("Author name: " + pp.getAuthor().name);
	// pw.println("Author gender: " + pp.getAuthor().gender);
	// pw.println(JavaExtensions.format(pp.getPostedAt(), ("yy-MMM-dd")));
	// pw.println("the real Title: " + pp.getTitle());
	// }
	// System.out.println(System.currentTimeMillis() - t);
	// }
	//
	// {
	// System.out.println("test StringBuffer");
	// StringBuffer sb = new StringBuffer(1000);
	// long t = System.currentTimeMillis();
	// for (Post pp : posts) {
	// sb.append("Title: " + pp.getTitle());
	// sb.append("Author name: " + pp.getAuthor().name);
	// sb.append("Author gender: " + pp.getAuthor().gender);
	// sb.append(JavaExtensions.format(pp.getPostedAt(), ("yy-MMM-dd")));
	// sb.append("the real Title: " + pp.getTitle());
	// }
	// System.out.println(System.currentTimeMillis() - t);
	// }
	//		
	// }

	@Test
	public void testMvel() throws Exception {
		// compile the template
		String src = "@foreach{post : posts} \r\n" + " - title: @{post.title}\r\n" + " - date: @{post.postedAt}\r\n"
				+ " - author @{post.author.name} @{post.author.gender}\r\n" + "@end{}\r\n" + "";
		CompiledTemplate compiled = TemplateCompiler.compileTemplate(src);
		final List<Post> posts = createPosts();
		Map<String, Object> m = new HashMap<String, Object>() {
			{
				put("posts", posts);
			}
		};
		// execute the template
		long tt = System.currentTimeMillis();
		for (int i = 0; i < 200; i++) {
			long t = System.currentTimeMillis();
			System.out.println("run templating: " + i);
			String output = (String) TemplateRuntime.execute(compiled, m);
			System.out.println(System.currentTimeMillis() - t);
			// System.out.println(out.toString());
//			Thread.sleep(5);
		}
		System.out.println("total time: " + (System.currentTimeMillis() - tt));

	}

	@Test
	public void testVelocity() throws Exception {
		final List<Post> posts = createPosts();
		Velocity.init();

		VelocityContext context = new VelocityContext();

		context.put( "posts", posts);

		org.apache.velocity.Template template = Velocity.getTemplate("tests/posts.vm");


		// execute the template
		long tt = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			long t = System.currentTimeMillis();
//			System.out.println("run templating: " + i);
			/* Merge data-model with template */
			StringWriter sw = new StringWriter();
			
			template.merge( context, sw );

			// System.out.println(out.toString());
			// out.flush();
			System.out.println(System.currentTimeMillis() - t);
			// System.out.println(out.toString());
//			Thread.sleep(5);
		}
		System.out.println("total time: " + (System.currentTimeMillis() - tt));

	}	
	
	@Test
	public void testSinglePageJapid() throws Exception {
		final List<Post> posts = createPosts();
		// execute the template
		long tt = System.currentTimeMillis();
		Posts te = new Posts(new org.apache.commons.io.output.ByteArrayOutputStream());
		te.render("抬头", posts);
		te = new Posts(new org.apache.commons.io.output.ByteArrayOutputStream());
		te.render("抬头", posts);
		for (int i = 0; i < 100; i++) {
			long t = System.currentTimeMillis();
//			System.out.println("run templating: " + i);
			/* Merge data-model with template */
			
//			StringWriter out = new StringWriter(1000);
//			PrintWriter printWriter = new PrintWriter(out);
			te = new Posts(new org.apache.commons.io.output.ByteArrayOutputStream());
			te.render("抬头", posts);
//			 System.out.println(out.toString());
			// out.flush();
			System.out.println(System.currentTimeMillis() - t);
			// System.out.println(out.toString());
//			Thread.sleep(5);
		}
		System.out.println("total time: " + (System.currentTimeMillis() - tt));

	}
		@Test
	public void testFreeMarker() throws Exception {
		final List<Post> posts = createPosts();
		Map<String, Object> m = new HashMap<String, Object>() {
			{
				put("posts", posts);
			}
		};

		/* ------------------------------------------------------------------- */
		/* You should do this ONLY ONCE in the whole application life-cycle: */

		/* Create and adjust the configuration */
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File("D:\\eclipse-workspace\\Japid\\tests\\"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		/* ------------------------------------------------------------------- */
		/* You usually do these for many times in the application life-cycle: */

		/* Get or create a template */
		Template temp = cfg.getTemplate("posts.ftl");

		// execute the template
		long tt = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			long t = System.currentTimeMillis();
//			System.out.println("run templating: " + i);
			/* Merge data-model with template */
			Writer out = new StringWriter();
			temp.process(m, out);
			// System.out.println(out.toString());
			// out.flush();
			System.out.println(System.currentTimeMillis() - t);
			// System.out.println(out.toString());
//			Thread.sleep(5);
		}
		System.out.println("total time: " + (System.currentTimeMillis() - tt));

	}

		/**
		 * one od the idea to improve template performace is to compile the static content as UTF-8 encoded byte array 
		 * instead of keep as string, which eventually will be converted to bytearray at runtime.
		 * 
		 * initial test show that 1000 iterations of convert a 40 chinese char string to byte array costs about 12 ms whereas direct array write
		 * costs only 2 ms, a 6 folds better performance. With ascii chars the gap is about 4 folds.
		 * 
		 * @throws IOException 
		 * 
		 */
	@Test
	public void testByteArrayVsString() throws IOException {
//		String ss =  "asdfasdfasdfasdfasdfasdfasdfasdf";
		String ss =  "仰望着天空寻找一位失去的故友悄无声息的离开了也带上了命运";

		byte[] ba = ss.getBytes("UTF-8");

		long t1 = 0;
		long t2 = 0;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// warm up
		baos.write(ba);
		baos.write(ba);
		baos.write(ba);
		baos.write(ba);
		Charset UTF8 = Charset.forName("UTF-8");
		for (int i = 0; i < 1000; i++) {
			{
				baos.reset();
				long t0 = System.nanoTime();
				baos.write(ba);
				t1 += System.nanoTime() - t0;
			}
			{
				baos.reset();
				long t0 = System.nanoTime();
//				baos.write(ss.getBytes(UTF8));
				baos.write(ss.getBytes("UTF-8")); // seems to be faster than using the Charset object?
				t2 += System.nanoTime() - t0;
			}
		}
		System.out.println("raw byte copy took nano: " + t1);
		System.out.println("String to bytes took nano: " + t2);
		
		List<byte[]> statics = new ArrayList<byte[]>();
		statics.add(new byte[] { 12, 23 });
		statics.add(new byte[] { 12, 23 });
	}
	
	@Test public void strings() {
		String ss =  "仰望着天空寻找一位失去的故友悄无声息的离开了也带上了命运";
		Charset UTF8 = Charset.forName("UTF-8");
		CharsetEncoder enc = UTF8.newEncoder();
//		enc.
		
	}
	
	@Test public void bytes() throws UnsupportedEncodingException {
		String src = "你我\n他";
		byte[] bytes = src.getBytes("UTF-8");
		System.out.println(bytesSrcNotation(bytes));
		
	}
	
	private String bytesSrcNotation(byte[] ba) {
		StringBuffer sb = new StringBuffer();
		sb.append("new byte[]{");
		for (byte b : ba) {
			sb.append(b).append(',');
		}
		sb.append("}");
		return sb.toString();
	}
	
	@Test public void testUrlMapper() {
		BranTemplateBase.urlMapper =  new DummyUrlMapper();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Actions ac = new Actions(baos);
		Post p = new Post();
		p.title = "hello";
		ac.render(p);
		System.out.println(baos.toString());
	}
	
	@Test public void testMessage() {
//		BranTemplateBase.urlMapper =  new DummyUrlMapper();
		BranTemplateBase.messageProvider =  new SimpleMessageProvider();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Msg ac = new Msg(baos);
		ac.render();
		System.out.println(baos.toString());
	}
	
}
