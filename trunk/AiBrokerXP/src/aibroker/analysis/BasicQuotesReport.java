package aibroker.analysis;

import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.domains.Sampling;
import aibroker.model.drivers.sql.SqlDb;
import aibroker.model.drivers.sql.SqlSeq;
import aibroker.util.SamplingUtil;

public class BasicQuotesReport extends Report {

    public BasicQuotesReport(final SqlDb database, final SqlSeq sequence) throws IOException {
        super(database, sequence);
    }

    @Override
    public void fill() {
        fillGeneralPage();

        final Quotes quotes = sequence.getQuotes();

        Sheet sheet = getSheet(Sampling.DAILY.toString());
        fillQuotesAnalysis(sheet, quotes);

        sheet = getSheet(Sampling.WEEKLY.toString());
        fillQuotesAnalysis(sheet, SamplingUtil.resample(quotes, Sampling.DAILY, Sampling.WEEKLY));

        sheet = getSheet(Sampling.MONTHLY.toString());
        fillQuotesAnalysis(sheet, SamplingUtil.resample(quotes, Sampling.DAILY, Sampling.MONTHLY));
    }

    private void fillQuotesAnalysis(final Sheet sheet, final Quotes quotes) {
        Cell cell = null;

        int rownum = 0;
        Row row = ensureRow(sheet, rownum++);

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
            row = ensureRow(sheet, rownum);
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

        row = ensureRow(sheet, rownum++);
        colIdx = summaryCol;
        row.createCell(colIdx++).setCellValue(""); // L
        row.createCell(colIdx++).setCellValue("CNT"); // M
        row.createCell(colIdx++).setCellValue("MAX"); // N
        row.createCell(colIdx++).setCellValue("AVG"); // O

        row = ensureRow(sheet, rownum++);
        colIdx = summaryCol;
        row.createCell(colIdx++).setCellValue("dH");
        row.createCell(colIdx++).setCellFormula("COUNTA(F2:F" + quotes.size() + 1 + ")");
        cell = row.createCell(colIdx++);
        cell.setCellFormula("MAX(F2:F" + quotes.size() + 1 + ")");
        cell.setCellStyle(priceStyle);
        cell = row.createCell(colIdx++);
        cell.setCellFormula("AVERAGE(F2:F" + quotes.size() + 1 + ")");
        cell.setCellStyle(priceStyle);

        row = ensureRow(sheet, rownum++);
        colIdx = summaryCol;
        row.createCell(colIdx++).setCellValue("dL");
        row.createCell(colIdx++).setCellFormula("COUNTA(G2:G" + quotes.size() + 1 + ")");
        cell = row.createCell(colIdx++);
        cell.setCellFormula("MAX(G2:G" + quotes.size() + 1 + ")");
        cell.setCellStyle(priceStyle);
        cell = row.createCell(colIdx++);
        cell.setCellFormula("AVERAGE(G2:G" + quotes.size() + 1 + ")");
        cell.setCellStyle(priceStyle);

        row = ensureRow(sheet, rownum++);
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

        row = ensureRow(sheet, rownum++);
        colIdx = summaryCol;
        row.createCell(colIdx++).setCellValue("PNT"); // L
        row.createCell(colIdx++).setCellValue("CNT"); // M
        row.createCell(colIdx++).setCellValue("MIN"); // N
        row.createCell(colIdx++).setCellValue("UP"); // O

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

            row = ensureRow(sheet, rownum++);
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

            pricePoint += profitIncrement;
        } while (eventCount > 0);

        rownum++;
        rownum++;
        rownum++;

        row = ensureRow(sheet, rownum++);
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

            row = ensureRow(sheet, rownum++);
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

}
