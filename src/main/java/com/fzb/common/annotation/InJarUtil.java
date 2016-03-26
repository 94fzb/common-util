package com.fzb.common.annotation;

import org.dom4j.*;
import org.dom4j.dom.DOMAttribute;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class InJarUtil {

	
	@RunOnStart
	public void inJar(){
		if(InJarUtil.class.getResource("/")!=null){
			try {
				inJar("E:/work/",versionStr(),Start.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	public static void inJar(String jarSavePath,String versionStr, Class clazz) throws IOException{
		File buildFile=new File(Start.class.getClassLoader().getResource("").getPath()+"/build.xml");
		
		List<String> jarFiles = new ArrayList<String>();
		String projectPath = new File(Start.class.getResource("/").getPath()).getParentFile().toString().replace("\\", "/");
		List<DefaultElement> elements = loadClassPathFile(new File(
				projectPath+"/.classpath"));
		String binPath=new File(Start.class.getResource("/").getPath()).toString();
		
		String projectName=projectPath.substring(projectPath.lastIndexOf("/")+1);
		File destfile=new File(jarSavePath+projectName+"-"+versionStr+".jar");
		if(destfile.exists()){
			destfile.delete();
		}
		for (DefaultElement telement : elements) {
			if ("lib".equals(telement.attributeValue("kind"))) {
				jarFiles.add(projectPath.toString().replace("\\", "/") + "/"
						+ telement.attributeValue("path").toString());
			}
		}
		
		Element rootEm=new DOMElement("project");
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("name", "Create Runnable Jar for Project "+projectName);
		map.put("default", "create_run_jar");
		rootEm.setAttributes(mapToAttributes(map));
		Element target=new DOMElement("target");
		target.setAttributes(signAttribute("name", "create_run_jar"));
		Element jar=new DefaultElement("jar");
		map=new HashMap<String,String>();
		map.put("destfile", destfile.toString());
		map.put("filesetmanifest", "mergewithoutmain");
		jar.setAttributes(mapToAttributes(map));
		
		Element manifest=new DefaultElement("manifest");
		Element attribute=new DefaultElement("attribute");
		map=new HashMap<String,String>();
		map.put("name", "Main-Class");
		map.put("value", clazz.getCanonicalName());
		attribute.setAttributes(mapToAttributes(map));
		manifest.add(attribute);
		attribute=new DefaultElement("attribute");
		map=new HashMap<String,String>();
		map.put("name", "Class-Path");
		map.put("value", ".");
		attribute.setAttributes(mapToAttributes(map));
		manifest.add(attribute);
		jar.add(manifest);
		
		Element fileset=new DefaultElement("fileset");
		fileset.setAttributes(signAttribute("dir", binPath));
		jar.add(fileset);
		
		for (String jarFile:jarFiles) {
			Element zipfileset=new DefaultElement("zipfileset");
			map=new HashMap<String,String>();
			map.put("excludes", "META-INF/*.SF");
			map.put("src",jarFile);
			zipfileset.setAttributes(mapToAttributes(map));
			jar.add(zipfileset);
		}
		target.add(jar);
		
		rootEm.add(target);
		XMLWriter xw=new XMLWriter(new FileWriter(buildFile),OutputFormat.createCompactFormat());
		xw.write(rootEm);
		xw.close();
		File fBat=File.createTempFile("build",".bat").getAbsoluteFile();
		//生成bat 文件

		updateBat(fBat);
		
		String cmdStr =fBat.toString();
		Runtime rt = Runtime.getRuntime();
		try {
			Process proc=rt.exec(cmdStr);
			InputStream in=proc.getInputStream();
			byte b[]=new byte[1024];
			in.read(b);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 移除bat文件

		fBat.delete();
		
		
	}

	public static String versionStr() {
		SimpleDateFormat df = new SimpleDateFormat("MMdd");
		return df.format(new Date());
	}

	public static void updateBat(File fBat) {
		String runtimePath = Start.class.getResource("/").getFile()
				.substring(1);
		String batStr = "cd/d  " + runtimePath + " \nant -buildfile build.xml";
		try {
			FileOutputStream fout = new FileOutputStream(fBat);
			fout.write(batStr.getBytes());
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Attribute> mapToAttributes(Map<String,String> map){
		List<Attribute> attributes=new ArrayList<Attribute>();
		for (Entry<String, String> attribute : map.entrySet()) {
			Attribute tattr=new DOMAttribute(new QName(attribute.getKey()), attribute.getValue());
			attributes.add(tattr);
		}
		return attributes;
	}
	
	public static List<Attribute> signAttribute(String name,String value){
		List<Attribute> attributes=new ArrayList<Attribute>();
		Attribute tattr=new DOMAttribute(new QName(name), value);
		attributes.add(tattr);
		return attributes;
	}
	
	private static List<DefaultElement> loadClassPathFile(File classPath){
		SAXReader dr=new SAXReader(DocumentFactory.getInstance());
		try {
			Document doc=dr.read(classPath);
			return (List<DefaultElement>) ((Node)doc.getDocument().selectNodes("classpath").get(0)).selectObject("classpathentry");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return null;
	}
	
}
