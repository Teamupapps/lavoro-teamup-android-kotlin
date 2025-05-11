package lavoro.teamup.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import lavoro.teamup.data.model.entry.AssetEntry
import lavoro.teamup.data.model.entry.BrandEntry
import lavoro.teamup.data.model.entry.CityEntry
import lavoro.teamup.data.model.entry.ClientEntry
import lavoro.teamup.data.model.entry.HistoryEntry
import lavoro.teamup.data.model.entry.ProductEntry
import java.lang.reflect.Type

class DataConverter {
    @TypeConverter
    fun fromCityEntry(item: CityEntry?): String? {
        if (item == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<CityEntry?>() {}.type
        return gson.toJson(item, type)
    }

    @TypeConverter
    fun toCityEntry(string: String?): CityEntry? {
        if (string == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<CityEntry?>() {}.type
        return gson.fromJson<CityEntry>(string, type)
    }

    @TypeConverter
    fun fromClientEntry(item: ClientEntry?): String? {
        if (item == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<ClientEntry?>() {}.type
        return gson.toJson(item, type)
    }

    @TypeConverter
    fun toClientEntry(string: String?): ClientEntry? {
        if (string == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<ClientEntry?>() {}.type
        return gson.fromJson<ClientEntry>(string, type)
    }

    @TypeConverter
    fun fromAssetEntry(item: AssetEntry?): String? {
        if (item == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<AssetEntry?>() {}.type
        return gson.toJson(item, type)
    }

    @TypeConverter
    fun toAssetEntry(string: String?): AssetEntry? {
        if (string == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<AssetEntry?>() {}.type
        return gson.fromJson<AssetEntry>(string, type)
    }

    @TypeConverter
    fun fromBrandEntry(item: BrandEntry?): String? {
        if (item == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<BrandEntry?>() {}.type
        return gson.toJson(item, type)
    }

    @TypeConverter
    fun toBrandEntry(string: String?): BrandEntry? {
        if (string == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<BrandEntry?>() {}.type
        return gson.fromJson<BrandEntry>(string, type)
    }

    @TypeConverter
    fun fromProductEntry(item: ProductEntry?): String? {
        if (item == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<ProductEntry?>() {}.type
        return gson.toJson(item, type)
    }

    @TypeConverter
    fun toProductEntry(string: String?): ProductEntry? {
        if (string == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<ProductEntry?>() {}.type
        return gson.fromJson<ProductEntry>(string, type)
    }

    @TypeConverter
    fun fromHistoryEntry(item: HistoryEntry?): String? {
        if (item == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<HistoryEntry?>() {}.type
        return gson.toJson(item, type)
    }

    @TypeConverter
    fun toHistoryEntry(string: String?): HistoryEntry? {
        if (string == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<HistoryEntry?>() {}.type
        return gson.fromJson<HistoryEntry>(string, type)
    }

}
