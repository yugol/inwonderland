package aibroker.model.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import aibroker.model.Ohlc;
import aibroker.model.Sequence;

public class XlsxExporter {

    private final XSSFWorkbook workbook = new XSSFWorkbook();

    public XlsxExporter() {
    }

    public void add(final Sequence seq) {
        final Sheet sheet = getSheet(seq.getName());
        int rownum = 0;
        for (final Ohlc ohlc : seq.getQuotes()) {
            final Row row = sheet.createRow(rownum);
            final Cell cell = row.createCell(0);
            cell.setCellValue(ohlc.open);
            rownum++;
        }
    }

    public void save(final File xlsxFile) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(xlsxFile)) {
            workbook.write(fileOut);
        }
    }

    private Sheet getSheet(final String name) {
        Sheet sheet = workbook.getSheet(name);
        if (sheet == null) {
            sheet = workbook.createSheet(name);
        }
        return sheet;
    }

}
