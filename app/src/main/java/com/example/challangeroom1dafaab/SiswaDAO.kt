package com.example.challangeroom1dafaab

import androidx.room.*

@Dao
interface SiswaDAO{

    @Insert
     fun addSiswa (tbsis: tbsiswa)

    @Update
     fun updateSiswa (tbsis: tbsiswa)

    @Delete
     fun deleteSiswa (tbsis: tbsiswa)

    @Query("SELECT * FROM tbsiswa")
    fun getSiswas () : List<tbsiswa>

    @Query("SELECT * FROM tbsiswa WHERE nis =:siswa_nis")
    fun getSiswa (siswa_nis: Int) : List<tbsiswa>
}
