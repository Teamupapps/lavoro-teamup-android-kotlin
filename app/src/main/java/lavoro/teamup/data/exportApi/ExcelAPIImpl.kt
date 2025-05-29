package lavoro.teamup.data.exportApi

import android.os.Environment
import lavoro.teamup.core.getCalendarDateTime
import lavoro.teamup.core.getTotal
import lavoro.teamup.core.launchASuspendTaskScope
import lavoro.teamup.core.toFormatedString
import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.transaction.Transaction
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

private const val DATE_EXPORT_FORMAT = "dd-MM-yyyy-HHmm"
private const val FILE_NAME = "teamup-backups"
private const val COL_WIDTH = 12800

class ExcelAPIImpl : ExcelAPI {

    private fun XSSFWorkbook.getHeaderCellStyle(): XSSFCellStyle {
        val headerStyle = this.createCellStyle()
        val font = this.createFont()
        font.bold = true
        font.color = IndexedColors.WHITE.getIndex()
        headerStyle.setAlignment(HorizontalAlignment.CENTER)
        headerStyle.fillForegroundColor = IndexedColors.BLUE_GREY.getIndex()
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)
        headerStyle.setFont(font)
        return headerStyle
    }

    private fun XSSFWorkbook.getSubHeaderCellStyle(): XSSFCellStyle {
        val headerStyle: XSSFCellStyle = this.createCellStyle()
        headerStyle.setAlignment(HorizontalAlignment.CENTER)
        return headerStyle
    }

    override suspend fun buildTransactionListFile(list: List<Transaction>): Result<Exception, Unit> =
        Result.build {

            launchASuspendTaskScope {

                if (list.isEmpty()) return@launchASuspendTaskScope

                val strDate = getCalendarDateTime(DATE_EXPORT_FORMAT)

                val root = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                    FILE_NAME
                )
                if (!root.exists()) root.mkdirs()

                val path = File(root, "/transaction-$strDate.xlsx")

                val workbook = XSSFWorkbook()
                val outputStream = FileOutputStream(path)
                val sheet: XSSFSheet = workbook.createSheet("Data-Backup")
                var row: XSSFRow = sheet.createRow(0)


                var cell: XSSFCell = row.createCell(0)
                cell.setCellValue("Creation Date")
                cell.cellStyle = workbook.getHeaderCellStyle()

                cell = row.createCell(1)
                cell.setCellValue("Creation By")
                cell.cellStyle = workbook.getHeaderCellStyle()

                cell = row.createCell(2)
                cell.setCellValue("Type")
                cell.cellStyle = workbook.getHeaderCellStyle()

                cell = row.createCell(3)
                cell.setCellValue("Client")
                cell.cellStyle = workbook.getHeaderCellStyle()

                cell = row.createCell(4)
                cell.setCellValue("Product")
                cell.cellStyle = workbook.getHeaderCellStyle()


                cell = row.createCell(5)
                cell.setCellValue("Unit Price")
                cell.cellStyle = workbook.getHeaderCellStyle()

                cell = row.createCell(6)
                cell.setCellValue("Qunatity")
                cell.cellStyle = workbook.getHeaderCellStyle()

                cell = row.createCell(7)
                cell.setCellValue("Total")
                cell.cellStyle = workbook.getHeaderCellStyle()

                cell = row.createCell(8)
                cell.setCellValue("Note")
                cell.cellStyle = workbook.getHeaderCellStyle()

                for (i in list.indices) {

                    row = sheet.createRow(i + 1)

                    cell = row.createCell(0)
                    cell.setCellValue(list[i].historyEntry.creationDate)
                    cell.cellStyle = workbook.getSubHeaderCellStyle()
                    sheet.setColumnWidth(0, COL_WIDTH)

                    cell = row.createCell(1)
                    cell.setCellValue(list[i].historyEntry.createdBy)
                    cell.cellStyle = workbook.getSubHeaderCellStyle()
                    sheet.setColumnWidth(1, COL_WIDTH)

                    cell = row.createCell(2)
                    cell.setCellValue("Transaction")
                    cell.cellStyle = workbook.getSubHeaderCellStyle()
                    sheet.setColumnWidth(2, COL_WIDTH)

                    cell = row.createCell(3)
                    cell.setCellValue(list[i].clientEntry.name + "-" + list[i].clientEntry.city)
                    cell.cellStyle = workbook.getSubHeaderCellStyle()
                    sheet.setColumnWidth(3, COL_WIDTH)

                    cell = row.createCell(4)
                    cell.setCellValue(list[i].productEntry.name + "-" + list[i].productEntry.brand)
                    cell.cellStyle = workbook.getSubHeaderCellStyle()
                    sheet.setColumnWidth(4, COL_WIDTH)

                    cell = row.createCell(5)
                    cell.setCellValue(list[i].assetEntry.unitPrice ?: 0.0)
                    cell.cellStyle = workbook.getSubHeaderCellStyle()
                    sheet.setColumnWidth(5, COL_WIDTH)

                    cell = row.createCell(6)
                    cell.setCellValue(list[i].assetEntry.quantity ?: 0.0)
                    cell.cellStyle = workbook.getSubHeaderCellStyle()
                    sheet.setColumnWidth(6, COL_WIDTH)

                    cell = row.createCell(7)
                    cell.setCellValue(getTotal(list[i].assetEntry.unitPrice, list[i].assetEntry.quantity).toFormatedString())
                    cell.cellStyle = workbook.getSubHeaderCellStyle()
                    sheet.setColumnWidth(7, COL_WIDTH)

                    cell = row.createCell(8)
                    cell.setCellValue((list[i].note))
                    cell.cellStyle = workbook.getSubHeaderCellStyle()
                    sheet.setColumnWidth(8, COL_WIDTH)

                }


                workbook.write(outputStream)
                outputStream.close()
            }
        }
}