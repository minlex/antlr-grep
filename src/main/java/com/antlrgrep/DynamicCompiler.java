package com.antlrgrep;
/**
* Created with IntelliJ IDEA.
* User: alexmin
* Date: 11/23/13
* Time: 9:01 PM
* To change this template use File | Settings | File Templates.
*/
    import com.google.common.io.Files;

    import java.io.File;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.lang.reflect.Method;
    import java.net.MalformedURLException;
    import java.net.URI;
    import java.net.URL;
    import java.net.URLClassLoader;
    import java.nio.charset.StandardCharsets;


    import java.util.*;

    import javax.tools.Diagnostic;
    import javax.tools.DiagnosticListener;
    import javax.tools.JavaCompiler;
    import javax.tools.JavaFileObject;
    import javax.tools.SimpleJavaFileObject;
    import javax.tools.StandardJavaFileManager;
    import javax.tools.ToolProvider;

  public class DynamicCompiler
    {
        private static String classOutputFolder;

        public static class InMemoryJavaFileObject extends SimpleJavaFileObject
        {
            private String contents = null;

            public InMemoryJavaFileObject(String className, String contents) throws Exception
            {
                super(URI.create("string:///" + className.replace('.', '/')
                        + Kind.SOURCE.extension), Kind.SOURCE);
                this.contents = contents;
            }

            public CharSequence getCharContent(boolean ignoreEncodingErrors)
                    throws IOException
            {
                return contents;
            }
        }

        public static void compileFromFile(String fileName) throws Exception
        {
           String source =  Files.toString(new File(fileName), StandardCharsets.UTF_8);
           String className = fileName.substring(0, fileName.length()-5);
           Map<String, String> classInfo = new HashMap<>();
           classInfo.put(className, source);
           compileFromString(classInfo);

        }

        public static Map<String, Class> compileFromFiles(List<String> fileNames) throws Exception
        {
            Map<String, String> classInfo = new HashMap<>();

            for(String fileName: fileNames){
                String source =  Files.toString(new File(fileName), StandardCharsets.UTF_8);
                String className = fileName.substring(0, fileName.length()-5);
                classInfo.put(className, source);
            }

           return compileFromString(classInfo);

        }

        public static Map<String, Class> compileFromString(Map<String, String> classInfo) throws Exception
        {

            classOutputFolder = System.getProperty("java.io.tmpdir");
            List<JavaFileObject> files = new ArrayList<>();
            //1.Construct an in-memory java source file from your dynamic code
            for(Map.Entry<String, String> entry: classInfo.entrySet()){
                String className = entry.getKey() ;
                String source = entry.getValue();
                JavaFileObject file = new InMemoryJavaFileObject(className, source);
                files.add(file);
            }

           // Iterable<? extends JavaFileObject> files = Arrays.asList(file);

            //2.Compile your files by JavaCompiler
            compile(files);

            //3.Load your class by URLClassLoader, then instantiate the instance, and call method by reflection
            return runIt(classInfo.keySet());
        }



        private static Map<String, Class> runIt(Set<String> classes) {
            // Create a File object on the root of the directory
            // containing the class file
            File file = new File(classOutputFolder);


            try
            {
                // Convert File to a URL
                URL url = file.toURL(); // file:/classes/demo
                URL[] urls = new URL[] { url };

                // Create a new class loader with the directory
                ClassLoader loader = new URLClassLoader(urls);

                // Load in the class; Class.childclass should be located in
                // the directory file:/class/demo/

                Map<String, Class> compiledClasses = new HashMap<>();
                for(String className: classes){
                    String name = className;
                    if (className.endsWith("Lexer"))
                            name = "Lexer";
                    else if (className.endsWith("Parser"))
                            name = "Parser";
                    else if  (className.endsWith("Listener"))
                        name = "Listener";

                    compiledClasses.put(name,loader.loadClass(className));
                }

                return compiledClasses;
            /*    Class params[] = {};
                Object paramsObj[] = {};
                Object instance = thisClass.newInstance();
                Method thisMethod = thisClass.getDeclaredMethod("testAdd", params);

                // run the testAdd() method on the instance:
                thisMethod.invoke(instance, paramsObj);   */
            }
            catch (MalformedURLException e)
            {
            }
            catch (ClassNotFoundException e)
            {
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return null;
        }

        private static void compile(Iterable<? extends JavaFileObject> files) {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null,
                    Locale.ENGLISH,
                    null);
            Iterable options = Arrays.asList("-d", classOutputFolder);
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
                    null, options, null,
                    files);
            Boolean result = task.call();
            if (result == true)
            {
                System.out.println("Succeeded");
            }

        }
        
        
 }
