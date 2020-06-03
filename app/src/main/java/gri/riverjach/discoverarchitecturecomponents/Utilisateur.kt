package gri.riverjach.discoverarchitecturecomponents

import android.graphics.Bitmap
import androidx.room.*

data class Address(
    var street: String = "",
    var city: String = "",
    var country: String = "France"
)

@Entity
data class Company(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String
)

@Entity(
    foreignKeys = [ForeignKey(
        entity = Utilisateur::class,
        parentColumns = ["id"],
        childColumns = ["userId"]
    )
    ]
)
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var userId: Long,
    var text: String
)

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["name", "age"], unique = true)
    ],
    foreignKeys = [ForeignKey(
        entity = Company::class,
        parentColumns = ["id"],
        childColumns = ["companyId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.NO_ACTION
    )
    ]
)
data class Utilisateur(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var companyId: Int,
    @ColumnInfo(name = "full_name")
    var name: String,
    var age: Int,

    @Embedded
    var address: Address = Address(),

    @Ignore
    var picture: Bitmap
)