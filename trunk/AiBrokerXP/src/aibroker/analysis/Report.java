package aibroker.analysis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import aibroker.model.SequenceDescriptor;
import aibroker.model.cloud.sources.bvb.BvbSequenceDescriptionReader;
import aibroker.model.drivers.sql.SqlDatabase;
import aibroker.model.drivers.sql.SqlSequence;

public abstract class Report {

    protected final SqlDatabase        database;
    protected final SqlSequence        sequence;

    protected final SequenceDescriptor descriptor;
    protected final double             blockMultiplier;
    protected final double             overallMultiplier;
    protected final double             transactionPrice;
    protected final double             transactionFee;
    protected final double             overallPrice;
    protected final double             profitPoint;
    protected final double             profitIncrement;

    private final XSSFWorkbook         workbook = new XSSFWorkbook();
    protected final CellStyle          dateStyle;
    protected final CellStyle          priceStyle;
    protected final CellStyle          timeStyle;
    protected final CellStyle          volumeStyle;

    public Report(final SqlDatabase database, final SqlSequence sequence) throws IOException {
        this.database = database;
        this.sequence = sequence;
        descriptor = readDescriptor();
        blockMultiplier = calculateBlockMultiplier();
        overallMultiplier = blockMultiplier * descriptor.blockSize();
        transactionPrice = calculateTransactionPrice();
        transactionFee = calculateTransactionFee();
        overallPrice = transactionPrice + transactionFee;
        profitPoint = calculateProfitPoint();
        profitIncrement = calculateProfitIncrement();

        dateStyle = createCellStyle("yyyy-MM-dd");
        priceStyle = createCellStyle(descriptor.lastPrice() > 1000 ? "0" : "0.0000");
        timeStyle = createCellStyle("HH:mm:ss");
        volumeStyle = createCellStyle("0");
    }

    public abstract void fill();

    public String getNameHint() {
        return sequence.getSymbol() + "-" + getClass().getSimpleName() + ".xlsx";
    }

    public void save(final File xlsxFile) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(xlsxFile)) {
            workbook.write(fileOut);
        }
    }

    private double calculateBlockMultiplier() {
        if (sequence.isRegular()) {
            double bm = 0.0001;
            final double blockPrice = descriptor.lastPrice() * descriptor.blockSize();
            while (blockPrice * bm < 1000) {
                bm *= 10;
            }
            if (blockPrice * bm > 2500) {
                bm /= 10;
            }
            return bm;
        }
        return 1;
    }

    private double calculateProfitIncrement() {
        int inc = 0;
        double pp = profitPoint;
        while (pp < 1) {
            pp *= 10;
            inc--;
        }
        return Math.pow(10, inc);
    }

    private double calculateProfitPoint() {
        return Math.ceil(transactionFee) / overallMultiplier;
    }

    private double calculateTransactionFee() {
        if (sequence.isRegular()) { return (transactionPrice + 1.5) * 0.7 / 100 + 1.5; }
        return 1;
    }

    private double calculateTransactionPrice() {
        if (sequence.isRegular()) { return descriptor.lastPrice() * descriptor.blockSize() * blockMultiplier; }
        return sequence.getMargin();
    }

    private CellStyle createCellStyle(final String format) {
        final CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(format));
        return cellStyle;
    }

    private SequenceDescriptor readDescriptor() throws IOException {
        if (sequence.isRegular()) { return BvbSequenceDescriptionReader.readDescription(sequence.getSymbol()); }
        return null;
    }

    protected Row ensureRow(final Sheet sheet, final int rownum) {
        Row row = sheet.getRow(rownum);
        if (row == null) {
            row = sheet.createRow(rownum);
        }
        return row;
    }

    protected void fillGeneralPage() {
        final Sheet sheet = getSheet("General");

        int rownum = 0;
        Row row = ensureRow(sheet, rownum++);
        int colIdx = 0;
        row.createCell(colIdx++).setCellValue("Symbol:");
        row.createCell(colIdx++).setCellValue(descriptor.symbol());

        if (sequence.isRegular()) {
            row = ensureRow(sheet, rownum++);
            colIdx = 0;
            row.createCell(colIdx++).setCellValue("Company:");
            row.createCell(colIdx++).setCellValue(descriptor.name());
        }

        row = ensureRow(sheet, rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Market:");
        row.createCell(colIdx++).setCellValue(sequence.isRegular() ? "BVB REGS" : "SIBEX FUTURES");

        row = ensureRow(sheet, rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Last price:");
        Cell cell = row.createCell(colIdx++);
        cell.setCellValue(descriptor.lastPrice());
        cell.setCellStyle(priceStyle);

        row = ensureRow(sheet, rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Block size:");
        row.createCell(colIdx++).setCellValue(descriptor.blockSize());

        row = ensureRow(sheet, rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Block multiplier:");
        row.createCell(colIdx++).setCellValue(blockMultiplier);

        row = ensureRow(sheet, rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Overall multiplier:");
        row.createCell(colIdx++).setCellValue(overallMultiplier);

        row = ensureRow(sheet, rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Transaction price:");
        cell = row.createCell(colIdx++);
        cell.setCellValue(transactionPrice);
        cell.setCellStyle(priceStyle);

        row = ensureRow(sheet, rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Transaction fee:");
        cell = row.createCell(colIdx++);
        cell.setCellValue(transactionFee);
        cell.setCellStyle(priceStyle);

        row = ensureRow(sheet, rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Overall price:");
        cell = row.createCell(colIdx++);
        cell.setCellValue(transactionPrice + transactionFee);
        cell.setCellStyle(priceStyle);

        row = ensureRow(sheet, rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Profit point:");
        row.createCell(colIdx++).setCellValue(profitPoint);

        row = ensureRow(sheet, rownum++);
        colIdx = 0;
        row.createCell(colIdx++).setCellValue("Profit increment:");
        row.createCell(colIdx++).setCellValue(profitIncrement);

        for (int i = 0; i < colIdx; ++i) {
            sheet.autoSizeColumn(i);
        }
    }

    protected Sheet getSheet(final String name) {
        Sheet sheet = workbook.getSheet(name);
        if (sheet == null) {
            sheet = workbook.createSheet(name);
        }
        return sheet;
    }

}
