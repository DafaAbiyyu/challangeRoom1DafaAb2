package com.example.challangeroom1dafaab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_siswa.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditSiswa : AppCompatActivity() {
        val db by lazy { dbsmksa(this) }
        private var noteId: Int = 0

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_edit_siswa)
            setupView()
            setupListener()

            noteId = intent.getIntExtra("intent_nis", 0)
            Toast.makeText(this, noteId.toString(), Toast.LENGTH_SHORT).show()
        }

        fun setupView() {
            val intentType = intent.getIntExtra("intentType", 0)
            when (intentType) {
                Constant.TYPE_CREATE -> {
                    button_update.visibility = View.GONE
                }
                Constant.TYPE_READ -> {
                    button_save.visibility = View.GONE
                    button_update.visibility = View.GONE
                    getsiswas()
                }
                Constant.TYPE_UPDATE -> {
                    button_save.visibility = View.GONE
                    getsiswas()
                }

            }
        }

        private fun setupListener() {

            button_save.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    db.siswaDAO().addSiswa(
                        tbsiswa(
                            edit_nis.text.toString().toInt(), edit_nama.text.toString(), edit_kelas.text.toString(),edit_alamat.text.toString())
                    )
                    finish()
                }
            }
            button_update.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    db.siswaDAO().updateSiswa(
                        tbsiswa(edit_nis.text.toString().toInt(), edit_nama.text.toString(), edit_kelas.text.toString(),edit_alamat.text.toString())
                    )
                    finish()
                }
            }
        }
        //menanpilkan data
        fun getsiswas() {
            noteId = intent.getIntExtra("intent_nis", 0)
            CoroutineScope(Dispatchers.IO).launch {
                val sisw =  db.siswaDAO().getSiswa(noteId)[0]
              //  edit_nis.setText(siswa.nis)
                edit_nama.setText(sisw.nama)
                edit_kelas.setText(sisw.kelas)
                edit_alamat.setText(sisw.alamat)
            }
        }

    }
