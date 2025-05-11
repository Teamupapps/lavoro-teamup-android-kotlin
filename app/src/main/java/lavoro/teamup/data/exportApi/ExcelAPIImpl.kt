package lavoro.teamup.data.exportApi

import lavoro.teamup.core.getCalendarDateTime
import lavoro.teamup.core.launchASuspendTaskScope
import lavoro.teamup.core.wrapper.Result
import lavoro.teamup.data.model.transaction.Transaction
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFCellStyle
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



            }
        }
}