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
import aibroker.model.Quotes;
import aibroker.model.SequenceDescriptor;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.model.update.sources.bvb.BvbSequenceDescriptionReader;

public class XlsxAnalysis {

    private final XSSFWorkbook       workbook = new XSSFWorkbook();

    private final SqlSequence        sequence;
    private final SequenceDescriptor descriptor;
    private final double             blockMultiplier;
    private final double             overallMultiplier;
    private final double             transactionPrice;
    private final double             transactionFee;
    private final double             profitPoint;
    private final double             profitIncrement;

    // private final CellStyle    timeStyle;
    // private final CellStyle    volumeStyle;
    private final CellStyle          priceStyle;
    private final CellStyle          dateStyle;

    public XlsxAnalysis(final SqlSequence sequence) throws IOException {
        this.sequence = sequence;
        descriptor = BvbSequenceDescriptionReader.readDescription(this.sequence.getSymbol());
        blockMultiplier = calculateBlockMultiplier();
        overallMultiplier = blockMultiplier * descriptor.getBlockSize();
        transactionPrice = descriptor.getLastPrice() * descriptor.getBlockSize() * blockMultiplier;
        transactionFee = (transactionPrice + 1.5) * 0.7 / 100 + 1.5;
        profitPoint = calculateProfitPoint();
        profitIncrement = calculateProfitIncrement();

        dateStyle = createCellStyle("yyyy-MM-dd");
        // timeStyle = createCellStyle("HH:mm:ss");
        priceStyle = createCellStyle(descriptor.getLastPrice() > 1000 ? "0" : "0.0000");
        // volumeStyle = createCellStyle("0");

        Sheet sheet = getSheet("General");
        fillGeneralSheet(sheet);
        sheet = getSheet(sequence.getSampling().toString());
        fillQuotesAnalysis(sheet, this.sequence.getQuotes());
    }

    public void save(final File xlsxFile) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(xlsxFile)) {
            workbook.write(fileOut);
        }
    }

    private double calculateBlockMultiplier() {
        double bm = 0.0001;
        final double blockPrice = descriptor.getLastPrice() * descriptor.getBlockSize();
        while (blockPrice * bm < 1000) {
            bm *= 10;
        }
        return bm;
    }

    private double calculateProfitIncrement() {
        return Math.pow(10, Math.round(Math.log10(profitPoint)));
    }

    private double calculateProfitPoint() {
        return Math.ceil(transactionFee) / overallMultiplier;
    }

    private CellStyle createCellStyle(final String format) {
        final CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(format));
        return cellStyle;
    }

    private void fillGeneralSheet(final Sheet sheet) throws IOException {
        int rownum = 0;
        Row row = sheet.createRow(rownum++);
        int colIdx = 0;
        row.createCell(colIdx++).setCellValue("Symbol:");
        row.createCell(colIdx++).setCellValue(descriptor.symbol());

        row = sheet.createRow(rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Last price:");
        Cell cell = row.createCell(colIdx++);
        cell.setCellValue(descriptor.getLastPrice());
        cell.setCellStyle(priceStyle);

        row = sheet.createRow(rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Block size:");
        row.createCell(colIdx++).setCellValue(descriptor.getBlockSize());

        row = sheet.createRow(rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Block multiplier:");
        row.createCell(colIdx++).setCellValue(blockMultiplier);

        row = sheet.createRow(rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Overall multiplier:");
        row.createCell(colIdx++).setCellValue(overallMultiplier);

        row = sheet.createRow(rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Transaction price:");
        cell = row.createCell(colIdx++);
        cell.setCellValue(transactionPrice);
        cell.setCellStyle(priceStyle);

        row = sheet.createRow(rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Transaction fee:");
        cell = row.createCell(colIdx++);
        cell.setCellValue(transactionFee);
        cell.setCellStyle(priceStyle);

        row = sheet.createRow(rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Overall price:");
        cell = row.createCell(colIdx++);
        cell.setCellValue(transactionPrice + transactionFee);
        cell.setCellStyle(priceStyle);

        row = sheet.createRow(rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Profit point:");
        row.createCell(colIdx++).setCellValue(profitPoint);

        row = sheet.createRow(rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Profit increment:");
        row.createCell(colIdx++).setCellValue(profitIncrement);

        for (int i = 0; i < colIdx; ++i) {
            sheet.autoSizeColumn(i);
        }
    }

    private void fillQuotesAnalysis(final Sheet sheet, final Quotes quotes) {
        int rownum = 0;
        Row row = sheet.createRow(rownum++);

        int colIdx = 0;
        row.createCell(colIdx++).setCellValue("DATE"); // A
        row.createCell(colIdx++).setCellValue("O"); // B
        row.createCell(colIdx++).setCellValue("H"); // C
        row.createCell(colIdx++).setCellValue("L"); // D
        row.createCell(colIdx++).setCellValue("C"); // E
        row.createCell(colIdx++).setCellValue("dH"); // F
        row.createCell(colIdx++).setCellValue("dL"); // G
        row.createCell(colIdx++).setCellValue("dA"); // H
        row.createCell(colIdx++).setCellValue("dC"); // I

        for (final Ohlc ohlc : quotes) {
            row = sheet.createRow(rownum);
            colIdx = 0;

            Cell cell = row.createCell(colIdx++);
            cell.setCellValue(ohlc.moment.toIsoDate());
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
            cell.setCellValue(ohlc.high - ohlc.open);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(colIdx++);
            cell.setCellValue(ohlc.open - ohlc.low);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(colIdx++);
            cell.setCellValue(ohlc.high - ohlc.low);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(colIdx++);
            cell.setCellValue(ohlc.close - ohlc.open);
            cell.setCellStyle(priceStyle);

            rownum++;
        }

        for (int i = 0; i < colIdx; ++i) {
            // sheet.autoSizeColumn(i);
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
