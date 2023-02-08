package Serenity.Web.Admin.IVA.Common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import au.com.bytecode.opencsv.CSVWriter;
import net.serenitybdd.core.pages.WebElementFacade;

public class Util {
	/**
	 * Hàm lấy ngày cách value ngày/tháng/năm so với ngày hiện tại value > 0 lấy
	 * thời điểm trong tương lại, value < 0 lấy thời điểm trong quá khứ
	 * 
	 * @param type:   theo ngày, tháng, năm
	 * @param value:  số ngày/tháng/năm cách thời điểm hiện tại muốn lấy.
	 * @param format: định dạng hiển thị của ngày trả về
	 * @return
	 */
	public static String getDay(String type, int value, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal= Calendar.getInstance();
		Date date2 = null;
		switch (type) {
		case "day":
			cal.add(Calendar.DATE, value);
			date2 = cal.getTime();
			break;
		case "month":
			cal.add(Calendar.MONTH, value);
			date2 = cal.getTime();
			break;
		case "year":
			cal.add(Calendar.YEAR, value);
			date2 = cal.getTime();
			break;
		default:
			break;
		}
		return dateFormat.format(date2);
	}

	public static String getNumberRandom(int from, int to) {
		Random r = new Random();
		int low = from;
		int high = to;
		int result = r.nextInt(high - low) + low;
		String s = String.valueOf(result);
//		if (s.length() == 1) {
//			s = "00" + s;
//		} else if (s.length() == 2) {
//			s = "0" + s;
//		}
		return s;
	}

	public static long getCurrenTime() {
		Calendar cal_startTime = Calendar.getInstance();
		Date date2 = cal_startTime.getTime();
		long startTime = date2.getTime();
		return startTime;
	}



	public static String readPassWordInMail(String body, String frontText, String behindText) {
//		String content = StringUtils.substringBetween(body, "Mật khẩu: ", " Đăng Nhập");
		String content = StringUtils.substringBetween(body, frontText, behindText);
		return content;
	}


	// them tat ca du lieu test vao file csv
	/*
	 * path: duong dan den file muon ghi header: ten header file csv value: su lieu
	 * tuong ung voi header
	 */
//	public static void writeAllDataCSV(String path, String header[], String value[]) throws IOException {
//		File file = new File(path);
//		try {
//			FileWriter outputfile = new FileWriter(file);
//
//			CSVWriter writer = new CSVWriter(outputfile);
//
//			writer.writeNext(header);
//			writer.writeNext(value);
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public static void writeAllDataCSV(String path, String[] header, String[] value) {
		File file = new File(path);
		try {
			PrintWriter outputfile = new PrintWriter(file, "UTF-8");
			CSVWriter writer = new CSVWriter(outputfile);
			String[] header_csv = null, value_csv = null;
			header_csv = addElementToArray(header_csv, header);
			value_csv = addElementToArray(value_csv, value);

			writer.writeNext(header_csv);
			writer.writeNext(value_csv);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String toCSVTest(String value) {

		String v = null;
		boolean doWrap = false;

		if (value != null) {

			v = value;
			if (v.contains("\\")) {
				v = v.replace("\\", "\\\\"); // escape embedded double quotes
				doWrap = true;
			}
			if (v.contains("\"")) {
				v = v.replace("\"", "\"\""); // escape embedded double quotes
				doWrap = true;
			}
			if (v.contains("\"\"")) {
				v = v.replace("\"\"", "\"\"\""); // escape embedded double quotes
				doWrap = true;
			}
			if (doWrap) {
//	        v = "\"" + v + "\""; // wrap with double quotes to hide the comma
			}
		}

		return v.replace("\"\"\"", "\"");
	}

	public static String[] addElementToArray(String[] array, String[] value) {
		
		int size = value.length;
		System.out.println(size);
		array = new String[size];
		for (int i = 0; i < size; i++) {

			array[i] = toCSVTest(value[i]);
		}
		
		return array;

	}

	// sau khi có file csv sẵn , muốn thêm 1 số cột vào file csv
	/*
	 * path: duong dan den file muon ghi newCol :mảng cột cần thêm
	 */
	public static void addColumn(String path, String newpath, String[] data) throws IOException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		final String lineSep = System.getProperty("line.separator");

		try {
			File file = new File(path);
			File file2 = new File(newpath);// so the
			// names don't conflict or just use different folders

			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2)));
			String line = null;
			int i = 0;
			for (line = br.readLine(); line != null; line = br.readLine(), i++) {

				String addedColumn = "," + String.valueOf(data[i]);
				bw.write(line + addedColumn + lineSep);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (br != null)
				br.close();
			if (bw != null)
				bw.close();
		}
	}

	public static void highLighterElementFacade(WebDriver driver, WebElementFacade a) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", a);
	}

	public static void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void highLighterElement(WebDriver driver, WebElement a) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", a);
	}

	public static void replaceTextCSV(String path, String header, String text) {

	}

	public static void scrollToElement(WebDriver driver, WebElementFacade element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void scrollToWebElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void clickUsingJavascript(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}
	
	public static void clickElementFacade(WebDriver driver, WebElementFacade element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	public static String[] readCSVFile(String csvFile) {
		String[] data = null;
		String filePath = System.getProperty("user.dir") + File.separator + csvFile;
		try {
			File file = new File(filePath);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
//				line = line.replaceAll("\"", "");
//				data = line.split(",");
				data = StringUtils.substringsBetween(line, "\"", "\"");
				if (line.contains("\"")) {
					String[] text = StringUtils.substringsBetween(line, "\"", "\"");
					for (int i = 0; i < text.length; i++) {

					}
				}
				System.out.println(data);
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return data;
	}

//	public static void main(String[] args) {
//		String[] s = readCSVFile("data\\test.csv");
//		int index = readCSVFile("data\\test.csv").length;
//		System.out.println(index);
//		for(int i = 0; i < index;i++)
//			System.out.println(s[i]);
//	}

	public static String chungThuSoString(String chungThuSo) {
		String cts = "";
		if (chungThuSo.equals("1")) {
			cts = "Chữ ký số server";
		} else if (chungThuSo.equals("2")) {
			cts = "Usb Token";
		} else if (chungThuSo.equals("3")) {
			cts = "Sim CA";
		}
		return cts;
	}

	public static void createCSVFile(String fileName, String[] header, String[] value) {
		String path = System.getProperty("user.dir") + File.separator + fileName;
//		String header[] = { "maTaiLieu", "maTaiLieuCon" };
//		String value[] = { maTaiLieu, maTaiLieuCon };
		try {
			Util.writeAllDataCSV(path, header, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read multi row
	 */
	public static List<List<String>> readCSVMultiRows(String pathFile) {
		List<List<String>> records = new ArrayList<>();
		String COMMA_DELIMITER = ",";
		try (FileInputStream fis = new FileInputStream(new File(pathFile));
				InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
				BufferedReader br = new BufferedReader(isr)) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(COMMA_DELIMITER);
				records.add(Arrays.asList(values));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		records.remove(0);
		return records;
	}

	/**
	 * Save multi row
	 */

	public static boolean writeDataMultiLineCSV(String filePath, List<String[]> data) {
		boolean rs = false;
		File file = new File(filePath);
		OutputStreamWriter outputfile = null;
		CSVWriter writer = null;
		try {
			FileOutputStream os = new FileOutputStream(file);
			outputfile = new OutputStreamWriter(os, "UTF-8");
			writer = new CSVWriter(outputfile, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
					CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
			writer.writeAll(data);
			rs = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputfile != null) {
				try {
					outputfile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
//closing writer connection
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return rs;
	}

///////ham xu ly gia tri null trong file data////////////
	public static String prepareData(String value) {
		if (value == null || value.length() == 0) {
			return "null";
		} else {
			return value;
		}
	}

	/**
	 * Check existing file
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isFileExisting(String filePath) {
		boolean rs = false;
		if (filePath != null && filePath.length() > 0) {
			File f = new File(filePath);
			if (f.exists() && !f.isDirectory()) {
				rs = true;
			}
		}
		return rs;
	}

	public static boolean deleteFileIfExist(String path) {
		boolean rs = false;
		try {
			rs = Files.deleteIfExists(Paths.get(path));
		} catch (IOException e) {
			rs = false;
		}
		return rs;
	}

	private static final String COMMA_DELIMITER = ",";

	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			String line;
			String path = System.getProperty("user.dir") + File.separator + "data" + File.separator + "test.csv";
			br = new BufferedReader(new FileReader(path));

			int count = 0;
			while ((line = br.readLine()) != null) {
				if (line != null) {
					String[] splitData = line.split(COMMA_DELIMITER);

					StringBuilder builder = new StringBuilder();
					for (int i = 0; i < splitData.length; i++) {
						builder.append(splitData[i]).append(",");
					}

					List<String> rs = new LinkedList<String>();

					int countPhay = 0;
					String word = "";

					for (int i = 0; i < builder.toString().length(); i++) {
						char c = builder.toString().charAt(i);
						if (c == '\"') {
							countPhay++;
							if (countPhay == 1) {
								String[] t = word.split(",");
								rs.addAll(Arrays.asList(t));
								word = "";
							} else if (countPhay == 2) {
								countPhay = 0;
								rs.add(word);
								word = "";
							}

						} else if (c == ',') {
							word += c;
						} else {
							word += c;
						}
					}
					if (!word.isEmpty()) {
						String[] t = word.split(",");
						rs.addAll(Arrays.asList(t));
					}

					System.out.println("Kết quả dòng thứ " + (++count) + ":");
					rs = filter(rs);
					for (String s : rs) {
						System.out.println(s);
					}
					System.out.println("=======================================");
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException crunchifyException) {
				crunchifyException.printStackTrace();
			}
		}
	}

	public static List<List<String>> getDataCSVFileMultiRow(String fileName) {
		BufferedReader br = null;
		List<List<String>> listData = new ArrayList<List<String>>();
		try {
			String line;
			String path = System.getProperty("user.dir") + File.separator + "data" + File.separator + fileName;
			br = new BufferedReader(new FileReader(path));

			int count = 0;
			while ((line = br.readLine()) != null) {
				if (line != null) {
					String[] splitData = line.split(COMMA_DELIMITER);

					StringBuilder builder = new StringBuilder();
					for (int i = 0; i < splitData.length; i++) {
						builder.append(splitData[i]).append(",");
					}

					List<String> rs = new LinkedList<String>();

					int countPhay = 0;
					String word = "";

					for (int i = 0; i < builder.toString().length(); i++) {
						char c = builder.toString().charAt(i);
						if (c == '\"') {
							countPhay++;
							if (countPhay == 1) {
								String[] t = word.split(",");
								rs.addAll(Arrays.asList(t));
								word = "";
							} else if (countPhay == 2) {
								countPhay = 0;
								rs.add(word);
								word = "";
							}

						} else if (c == ',') {
							word += c;
						} else {
							word += c;
						}
					}
					if (!word.isEmpty()) {
						String[] t = word.split(",");
						rs.addAll(Arrays.asList(t));
					}

//					System.out.println("Kết quả dòng thứ " + (++count) + ":");
					rs = filter(rs);
//					for (String s : rs) {
//						System.out.println(s);
//					}
					listData.add(rs);
//					System.out.println("=======================================");
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException crunchifyException) {
				crunchifyException.printStackTrace();
			}
		}
		listData.remove(0);
		return listData;
	}

	private static List<String> filter(List<String> rs) {
		List<String> arr = new LinkedList<>();

		for (String s : rs) {
			if (!s.isEmpty()) {
				arr.add(s.trim());
			}
		}

		return arr;
	}

	public static List<String> parseCsvLine(String csvLine) {
		List<String> result = new ArrayList<String>();
		if (csvLine != null) {
			String[] splitData = csvLine.split(COMMA_DELIMITER);
			for (int i = 0; i < splitData.length; i++) {
				result.add(splitData[i]);
			}
		}
		return result;
	}

	public static String jvmBitVersion() {
		return System.getProperty("sun.arch.data.model");
	}
	


//	public static AutoItX setAutoIt() {
//		String jacobDllVersionToUse = "";
//		if (jvmBitVersion().contains("32")) {
//			jacobDllVersionToUse = "jacob-1.19-x86.dll";
//		} else {
//			jacobDllVersionToUse = "jacob-1.19-x64.dll";
//		}
//		Util.pause(1000);
//		File file = new File("autoit", jacobDllVersionToUse); // path to the jacob dll
//		System.out.println(file.getAbsolutePath());
//		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
//		AutoItX x = new AutoItX();
//		System.out.println("pass setup");
//		return x;
//	}

	
	public static boolean compareMap(Map<String, String> first, Map<String, String> second) {
	    if (first.size() != second.size()) {
	        return false;
	    }
	 
	    return first.entrySet().stream()
	      .allMatch(e -> String.valueOf(e.getValue()).equals(String.valueOf(second.get(e.getKey()))));
	}
	
	/**
	 * function delete file has name fileName in directory
	 * 
	 * @param fileName
	 * @param dir
	 */
	public static void deleteAllFile(String fileName, File dir) {
		File[] dirContents = dir.listFiles();

		for (int i = 0; i < dirContents.length; i++) {
			if (dirContents[i].getName().contains(fileName)) {
				dirContents[i].delete();
			}
		}

	}
	
	public static String getStringJson(String s) {
		String jsonString = "";
		String[] listLine = s.split("\n"); 
		for(int i = 0; i < listLine.length; i++) {
			jsonString+=listLine[i].trim();
		}
//		System.out.println(jsonString.replaceAll(": ", ":"));
		return jsonString.replaceAll(": ", ":");
	}
	
	@Test
	public void testReadCSV() {
		String row4 = getDataCSVFileMultiRow("XemThongTinDoanhNghiep.csv").get(0).get(3);
		System.out.println(row4);
	}
	
	public static String getDateTimeNow() {
		String dateNow = java.time.LocalDateTime.now() + "";
		dateNow = dateNow.trim().replaceAll("[-:.]", "");
		return dateNow;
	}
	
	public static void typeJavaScript(WebDriver driver, WebElementFacade element, String value) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
//		jse.executeScript("document.getElementById('NgaySinhFormat').setAttribute('value', '"+string+"')");
		jse.executeScript("arguments[0].value='" + value + "';", element);
	}
	
	public static String getFileNameContainText(String text, File dir) {
		File[] dirContents = dir.listFiles();
		String fileName = "";
		for (int i = 0; i < dirContents.length; i++) {
			if (dirContents[i].getName().contains(text)) {
				fileName = dirContents[i].getName();
			}
		}
		return fileName;
	}
	
	public static boolean checkFileNameExist(String text, File dir) {
		File[] dirContents = dir.listFiles();
		String fileName = "";
		for (int i = 0; i < dirContents.length; i++) {
			if (dirContents[i].getName().contains(text)) {
				return true;
			}
		}
		return false;
	}
	
	public static void copyFileNIO(String from, String to) throws IOException {

        Path fromFile = Paths.get(from);
        Path toFile = Paths.get(to);

        // if fromFile doesn't exist, Files.copy throws NoSuchFileException
        if (Files.notExists(fromFile)) {
            System.out.println("File doesn't exist? " + fromFile);
            return;
        }

        // if toFile folder doesn't exist, Files.copy throws NoSuchFileException
        // if toFile parent folder doesn't exist, create it.
        Path parent = toFile.getParent();
        if(parent!=null){
            if(Files.notExists(parent)){
                Files.createDirectories(parent);
            }
        }

        // default - if toFile exist, throws FileAlreadyExistsException
        Files.copy(fromFile, toFile);

        // if toFile exist, replace it.
        // Files.copy(fromFile, toFile, StandardCopyOption.REPLACE_EXISTING);

        // multiple StandardCopyOption
        /*CopyOption[] options = { StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES,
                LinkOption.NOFOLLOW_LINKS };

        Files.copy(fromFile, toFile, options);*/

    }
	
}
