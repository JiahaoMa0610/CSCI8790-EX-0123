package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class UtilOption {
	static String workDir = System.getProperty("user.dir");
	static String outputDir = workDir + File.separator + "output";
	public static String dad;
	public static String[] kid = new String[2];
	private static Scanner scanner = new Scanner(System.in);

	@Test
	public static void main(String[] args) {
		System.out.println("=============================================");
		System.out.println("Example Program");

		while (true) {
			System.out.println("=============================================");
			System.out.print("Enter three classes (\"q\" to terminate)\n");
			String[] inputs = getInputs();
			for (int i = 0; i < inputs.length; i++) {
				System.out.println("[DBG] " + i + ": " + inputs[i]);
			}
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

				cckid1.defrost(); // modifications of the class definition will be permitted.
				cckid1.setSuperclass(ccdad);
				cckid2.defrost();
				cckid2.setSuperclass(ccdad);
				cckid1.writeFile(outputDir);
				cckid2.writeFile(outputDir);
				System.out.println("[DBG] write output to: " + outputDir);

			} catch (NotFoundException | CannotCompileException | IOException e) {
				e.printStackTrace();
			}
		}

	}

	static void insertClassPath(ClassPool pool) throws NotFoundException {
		String strClassPath = outputDir;
		pool.insertClassPath(strClassPath);
		System.out.println("[DBG] insert classpath: " + strClassPath);
	}

	public static String[] getInputs() {
		String[] fathers = new String[3];
		String[] child = new String[3];
		int index = 0;
		int kindex = 0;
		String input = scanner.nextLine();
		if (input.trim().equalsIgnoreCase("q")) {
			System.err.println("Terminated.");
			System.exit(0);
		}
		List<String> list = new ArrayList<String>();
		String[] inputArr = input.split(",");
		int temp = 0;
		if (inputArr.length == 3) {
			for (String iElem : inputArr) {
				list.add(iElem.trim());
				if (iElem.startsWith("Common")) {
					fathers[index] = iElem.trim();
					index++;
				} else {
					child[kindex] = iElem.trim();
					kindex++;
				}
			}
			if (index == 1) {
				dad = fathers[0];
				kid[0] = child[0];
				kid[1] = child[1];
			} else if (index > 1) {
				for (String x : fathers) {
					if (x != null && x.length() > temp) {
						temp = x.length();
						dad = x;
					}
				}
				List<String> er = new ArrayList<String>(list);
				er.remove(dad);
				kid = er.toArray(new String[0]);
			} else {
				dad = inputArr[0].trim();
				kid[0] = inputArr[1].trim();
				kid[1] = inputArr[2].trim();
				
			}

		} else {
			System.out.println("[WRN] Invalid Input");
		}
		return list.toArray(new String[0]);
	}
}
