package lavoro.teamup.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import lavoro.teamup.data.room.brand.BrandDao
import lavoro.teamup.data.room.brand.RoomBrand
import lavoro.teamup.data.room.city.CityDao
import lavoro.teamup.data.room.city.RoomCity
import lavoro.teamup.data.room.client.ClientDao
import lavoro.teamup.data.room.client.RoomClient
import lavoro.teamup.data.room.note.NoteDao
import lavoro.teamup.data.room.note.RoomNote
import lavoro.teamup.data.room.product.ProductDao
import lavoro.teamup.data.room.product.RoomProduct
import lavoro.teamup.data.room.transaction.RoomTransaction
import lavoro.teamup.data.room.transaction.TransactionDao
import lavoro.teamup.data.room.user.RoomUser
import lavoro.teamup.data.room.user.UserDao

private const val DATABASE = "teamup_db"

@Database(
    entities = [
        RoomUser::class,
        RoomTransaction::class,
        RoomBrand::class,
        RoomProduct::class,
        RoomCity::class,
        RoomClient::class,
        RoomNote::class
    ], version = 1, exportSchema = false
)

@TypeConverters(DataConverter::class)
abstract class TeamDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao
    abstract fun brandDao(): BrandDao
    abstract fun productDao(): ProductDao
    abstract fun cityDao(): CityDao
    abstract fun clientDao(): ClientDao
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: TeamDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TeamDatabase::class.java, DATABASE
            ).build()
    }
}