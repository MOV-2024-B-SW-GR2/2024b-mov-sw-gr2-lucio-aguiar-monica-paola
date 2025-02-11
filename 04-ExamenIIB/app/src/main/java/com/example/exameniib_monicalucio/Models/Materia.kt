package com.example.exameniib_monicalucio.Models

import android.os.Parcel
import android.os.Parcelable

// Clase Materia
class Materia(
    var id: Int,
    var nombre: String?,
    var codigo: String?,
    var estado: Boolean,
    var codigoProfesor: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(codigo)
        parcel.writeByte(if (estado) 1 else 0)
        parcel.writeString(codigoProfesor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Materia> {
        override fun createFromParcel(parcel: Parcel): Materia {
            return Materia(parcel)
        }

        override fun newArray(size: Int): Array<Materia?> {
            return arrayOfNulls(size)
        }
    }
}