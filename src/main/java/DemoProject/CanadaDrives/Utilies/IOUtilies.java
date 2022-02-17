package DemoProject.CanadaDrives.Utilies;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class IOUtilies {
	static File file = null;
	static FileInputStream input = null;
	static Workbook workbook = null;
	static Sheet sheet = null;
	static Row row = null;

	public static void readexcelData() {
		try {
			file = new File("C:\\Users\\Gorya\\Desktop\\test.xlsx");
			input = new FileInputStream(file);
			workbook = new XSSFWorkbook(input);
			sheet = workbook.getSheet("test");
			int totalRows = sheet.getLastRowNum() - sheet.getFirstRowNum();
			for (int i = 0; i < totalRows; i++) {
				row = sheet.getRow(i);
				System.out.println();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					try {
						System.out.print(row.getCell(j).toString() + "||");
					} catch (NullPointerException e) {
						System.out.print("");
					}
				}
			}
			input.close();
		} catch (FileNotFoundException f) {
			f.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
