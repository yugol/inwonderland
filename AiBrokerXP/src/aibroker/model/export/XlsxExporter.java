package aibroker.model.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import aibroker.model.Ohlc;
import aibroker.model.Sequence;

public class XlsxExporter {

    private final XSSFWorkbook workbook = new XSSFWorkbook();

    // private final CellStyle    timeStyle;
    // private final CellStyle    volumeStyle;
    private final CellStyle    priceStyle;
    private final CellStyle    dateStyle;

    public XlsxExporter() {
        dateStyle = createCellStyle("yyyy-MM-dd");
        // timeStyle = createCellStyle("HH:mm:ss");
        priceStyle = createCellStyle("General");
        // volumeStyle = createCellStyle("0");
    }

    public void add(final Sequence seq) {
        final Sheet sheet = getSheet(seq.getName());
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("D");
        row.createCell(1).setCellValue("O");
        row.createCell(2).setCellValue("H");
        row.createCell(3).setCellValue("L");
        row.createCell(4).setCellValue("C");
        row.createCell(5).setCellValue("dH");
        row.createCell(6).setCellValue("dL");
        row.createCell(7).setCellValue("dC");

        int rownum = 1;
        for (final Ohlc ohlc : seq.getQuotes()) {
            row = sheet.createRow(rownum);

            Cell cell = row.createCell(0);
            cell.setCellValue(ohlc.moment.toIsoDate());
            cell.setCellStyle(dateStyle);

            cell = row.createCell(1);
            cell.setCellValue(ohlc.open);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(2);
            cell.setCellValue(ohlc.high);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(3);
            cell.setCellValue(ohlc.low);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(4);
            cell.setCellValue(ohlc.close);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(5);
            cell.setCellValue(ohlc.high - ohlc.open);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(6);
            cell.setCellValue(ohlc.open - ohlc.low);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(7);
            cell.setCellValue(ohlc.close - ohlc.open);
            cell.setCellStyle(priceStyle);

            rownum++;
        }

        for (int i = 0; i < 8; ++i) {
            sheet.autoSizeColumn(i);
        }
    }

    public void save(final File xlsxFile) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(xlsxFile)) {
            workbook.write(fileOut);
        }
    }

    private CellStyle createCellStyle(final String format) {
        final CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(format));
        return cellStyle;
    }

    private Sheet getSheet(final String name) {
        Sheet sheet = workbook.getSheet(name);
        if (sheet == null) {
            sheet = workbook.createSheet(name);
        }
        return sheet;
    }

}
