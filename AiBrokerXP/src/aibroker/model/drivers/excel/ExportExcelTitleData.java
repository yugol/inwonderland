package aibroker.model.drivers.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import aibroker.model.Ohlc;
import aibroker.model.drivers.sql.SqlSeq;

public class ExportExcelTitleData {

    private final XSSFWorkbook workbook = new XSSFWorkbook();
    private final SqlSeq       sequence;

    protected final CellStyle  dateStyle;
    protected final CellStyle  priceStyle;
    protected final CellStyle  volumeStyle;

    public ExportExcelTitleData(final SqlSeq sequence) {
        this.sequence = sequence;
        dateStyle = createCellStyle("yyyy-MM-dd");
        priceStyle = createCellStyle("0.0000");
        volumeStyle = createCellStyle("0");
    }

    public void build() {
        final Sheet sheet = ensureSheet("BVB_REGS");

        int rownum = 0;
        Row row = ensureRow(sheet, rownum++);
        int colIdx = 0;
        row.createCell(colIdx++).setCellValue("Date");
        row.createCell(colIdx++).setCellValue("Open");
        row.createCell(colIdx++).setCellValue("High");
        row.createCell(colIdx++).setCellValue("Low");
        row.createCell(colIdx++).setCellValue("Close");
        row.createCell(colIdx++).setCellValue("Volume");

        final List<Ohlc> quotes = sequence.getQuotes();
        for (final Ohlc ohlc : quotes) {
            row = ensureRow(sheet, rownum++);
            colIdx = 0;

            Cell cell = row.createCell(colIdx++);
            cell.setCellValue(ohlc.moment.getTime());
            cell.setCellStyle(dateStyle);

            cell = row.createCell(colIdx++);
            cell.setCellValue(ohlc.open);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(colIdx++);
            cell.setCellValue(ohlc.high);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(colIdx++);
            cell.setCellValue(ohlc.low);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(colIdx++);
            cell.setCellValue(ohlc.close);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(colIdx++);
            cell.setCellValue(ohlc.volume);
            cell.setCellStyle(volumeStyle);
        }

        for (int i = 0; i < 6; ++i) {
            sheet.autoSizeColumn(i);
        }
    }

    public void saveTo(final File xlsxFile) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(xlsxFile)) {
            workbook.write(fileOut);
        }
    }

    private CellStyle createCellStyle(final String format) {
        final CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(format));
        return cellStyle;
    }

    protected Row ensureRow(final Sheet sheet, final int rownum) {
        Row row = sheet.getRow(rownum);
        if (row == null) {
            row = sheet.createRow(rownum);
        }
        return row;
    }

    protected Sheet ensureSheet(final String name) {
        Sheet sheet = workbook.getSheet(name);
        if (sheet == null) {
            sheet = workbook.createSheet(name);
        }
        return sheet;
    }

}
