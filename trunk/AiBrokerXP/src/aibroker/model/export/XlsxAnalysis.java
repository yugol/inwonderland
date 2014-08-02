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
import aibroker.model.domains.Sampling;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.model.update.sources.bvb.BvbSequenceDescriptionReader;
import aibroker.util.SamplingUtil;

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

        Quotes quotes = this.sequence.getQuotes();
        sheet = getSheet(sequence.getSampling().toString());
        fillQuotesAnalysis(sheet, quotes);

        quotes = SamplingUtil.resample(quotes, Sampling.DAILY, Sampling.WEEKLY);
        sheet = getSheet(Sampling.WEEKLY.toString());
        fillQuotesAnalysis(sheet, quotes);

        quotes = SamplingUtil.resample(quotes, Sampling.WEEKLY, Sampling.MONTHLY);
        sheet = getSheet(Sampling.MONTHLY.toString());
        fillQuotesAnalysis(sheet, quotes);
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
        return 0.01;
        // return Math.pow(10, Math.round(Math.log10(profitPoint)));
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
        Cell cell = null;

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

            cell = row.createCell(colIdx++);
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

        rownum = 0;
        final int summaryCol = colIdx + 2;

        row = sheet.getRow(rownum++);
        colIdx = summaryCol;
        row.createCell(colIdx++).setCellValue(""); // L
        row.createCell(colIdx++).setCellValue("CNT"); // M
        row.createCell(colIdx++).setCellValue("MAX"); // N
        row.createCell(colIdx++).setCellValue("AVG"); // O

        row = sheet.getRow(rownum++);
        colIdx = summaryCol;
        row.createCell(colIdx++).setCellValue("dH");
        row.createCell(colIdx++).setCellFormula("COUNTA(F2:F" + quotes.size() + 1 + ")");
        cell = row.createCell(colIdx++);
        cell.setCellFormula("MAX(F2:F" + quotes.size() + 1 + ")");
        cell.setCellStyle(priceStyle);
        cell = row.createCell(colIdx++);
        cell.setCellFormula("AVERAGE(F2:F" + quotes.size() + 1 + ")");
        cell.setCellStyle(priceStyle);

        row = sheet.getRow(rownum++);
        colIdx = summaryCol;
        row.createCell(colIdx++).setCellValue("dL");
        row.createCell(colIdx++).setCellFormula("COUNTA(G2:G" + quotes.size() + 1 + ")");
        cell = row.createCell(colIdx++);
        cell.setCellFormula("MAX(G2:G" + quotes.size() + 1 + ")");
        cell.setCellStyle(priceStyle);
        cell = row.createCell(colIdx++);
        cell.setCellFormula("AVERAGE(G2:G" + quotes.size() + 1 + ")");
        cell.setCellStyle(priceStyle);

        row = sheet.getRow(rownum++);
        colIdx = summaryCol;
        row.createCell(colIdx++).setCellValue("dA");
        row.createCell(colIdx++).setCellFormula("COUNTA(H2:H" + quotes.size() + 1 + ")");
        cell = row.createCell(colIdx++);
        cell.setCellFormula("MAX(H2:H" + quotes.size() + 1 + ")");
        cell.setCellStyle(priceStyle);
        cell = row.createCell(colIdx++);
        cell.setCellFormula("AVERAGE(H2:H" + quotes.size() + 1 + ")");
        cell.setCellStyle(priceStyle);

        rownum++;
        rownum++;
        rownum++;

        row = sheet.getRow(rownum++);
        colIdx = summaryCol;
        row.createCell(colIdx++).setCellValue("PNT"); // L
        row.createCell(colIdx++).setCellValue("CNT"); // M
        row.createCell(colIdx++).setCellValue("MIN"); // N
        row.createCell(colIdx++).setCellValue("UP"); // O
        row.createCell(colIdx++).setCellValue("DOWN"); // P
        row.createCell(colIdx++).setCellValue("GAIN"); // Q

        double pricePoint = profitPoint;
        int eventCount = 0;
        double maxLow = 0;
        do {
            eventCount = 0;
            maxLow = 0;

            for (final Ohlc ohlc : quotes) {
                final double dH = ohlc.high - ohlc.open;
                if (dH >= pricePoint) {
                    eventCount++;
                    final double dL = ohlc.open - ohlc.low;
                    if (dL > maxLow) {
                        maxLow = dL;
                    }
                }
            }

            row = sheet.getRow(rownum++);
            colIdx = summaryCol;

            cell = row.createCell(colIdx++);
            cell.setCellValue(pricePoint);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(colIdx++);
            cell.setCellValue(eventCount);

            cell = row.createCell(colIdx++);
            cell.setCellValue(maxLow);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(colIdx++);
            cell.setCellValue(pricePoint * eventCount);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(colIdx++);
            cell.setCellFormula(quotes.size() - eventCount + " * P7");
            cell.setCellStyle(priceStyle);

            cell = row.createCell(colIdx++);
            cell.setCellFormula("O" + rownum + " - P" + rownum);
            cell.setCellStyle(priceStyle);

            pricePoint += profitIncrement;
        } while (eventCount > 0);

        rownum++;
        rownum++;
        rownum++;

        row = sheet.getRow(rownum++);
        colIdx = summaryCol;
        row.createCell(colIdx++).setCellValue("PNT"); // L
        row.createCell(colIdx++).setCellValue("CNT"); // M
        row.createCell(colIdx++).setCellValue("DOWN"); // N

        pricePoint = profitIncrement;
        eventCount = 0;
        do {
            eventCount = 0;
            maxLow = 0;

            for (final Ohlc ohlc : quotes) {
                final double dL = ohlc.open - ohlc.low;
                if (dL <= pricePoint) {
                    eventCount++;
                }
            }

            row = sheet.getRow(rownum++);
            colIdx = summaryCol;

            cell = row.createCell(colIdx++);
            cell.setCellValue(pricePoint);
            cell.setCellStyle(priceStyle);

            cell = row.createCell(colIdx++);
            cell.setCellValue(eventCount);

            cell = row.createCell(colIdx++);
            cell.setCellValue(pricePoint * eventCount);
            cell.setCellStyle(priceStyle);

            pricePoint += profitIncrement;
        } while (pricePoint < 0.1);

        sheet.autoSizeColumn(0);
    }

    private Sheet getSheet(final String name) {
        Sheet sheet = workbook.getSheet(name);
        if (sheet == null) {
            sheet = workbook.createSheet(name);
        }
        return sheet;
    }

}
