package com.example.estudiantemateria.Models

import android.os.Parcel
import android.os.Parcelable
// Clase Estudiante
class Estudiante(
    var codUnico: Int,
    var nombre: String?,
    var fechaNacimiento: String?, // Assuming DATE is stored as a String
    var carrera: String?,
    var IRA: Double,
    var idMateria: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(codUnico)
        parcel.writeString(nombre)
        parcel.writeString(fechaNacimiento)
        parcel.writeString(carrera)
        parcel.writeDouble(IRA)
        parcel.writeInt(idMateria)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Estudiante> {
        override fun createFromParcel(parcel: Parcel): Estudiante {
            return Estudiante(parcel)
        }

        override fun newArray(size: Int): Array<Estudiante?> {
            return arrayOfNulls(size)
        }
    }
}