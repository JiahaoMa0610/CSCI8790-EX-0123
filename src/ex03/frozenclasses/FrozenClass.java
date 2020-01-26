package ex03.frozenclasses;

import java.io.File;
import util.UtilOption;
import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class FrozenClass {
   static String workDir = System.getProperty("user.dir");
   static String outputDir = workDir + File.separator + "output";

   public static void main(String[] args) {
      try {
         ClassPool pool = ClassPool.getDefault();
         insertClassPath(pool);

         CtClass cckid1 = pool.makeClass(UtilOption.kid[0]);
         cckid1.writeFile(outputDir); // debugWriteFile
         System.out.println("[DBG] write output to: " + outputDir);
         
         CtClass cckid2 = pool.makeClass(UtilOption.kid[1]);
         cckid2.writeFile(outputDir); // debugWriteFile
         System.out.println("[DBG] write output to: " + outputDir);

         CtClass ccdad = pool.makeClass(UtilOption.dad);
         ccdad.writeFile(outputDir);
         System.out.println("[DBG] write output to: " + outputDir);

         ccdad.defrost(); // modifications of the class definition will be permitted.
         ccdad.setSuperclass(cckid1);
         ccdad.setSuperclass(cckid2);
         ccdad.writeFile(outputDir);
         System.out.println("[DBG] write output to: " + outputDir);

      } catch (NotFoundException | CannotCompileException | IOException e) {
         e.printStackTrace();
      }
   }

   static void insertClassPath(ClassPool pool) throws NotFoundException {
      String strClassPath = outputDir;
      pool.insertClassPath(strClassPath);
      System.out.println("[DBG] insert classpath: " + strClassPath);
   }
}
