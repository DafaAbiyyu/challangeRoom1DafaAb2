package com.example.challangeroom1dafaab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { dbsmksa (this)}
    lateinit var noteAdapter: AdapterSiswa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()
    }

    fun setupListener(){
        buttonCreate.setOnClickListener {
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(noteId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext,EditSiswa::class.java)
                .putExtra("intent_nis",noteId)
                .putExtra("intentType",intentType)
        )
    }

    override fun onStart() {
        super.onStart()
        loadNote()
    }

    fun loadNote (){
        CoroutineScope(Dispatchers.IO).launch {
            val sekolah = db.siswaDAO().getSiswas()
            Log.d("MainActivity","dbResponse:$sekolah")
            withContext(Dispatchers.Main) {
                noteAdapter.setData(sekolah)
            }
        }
    }

    private fun setupRecyclerView(){
        noteAdapter = AdapterSiswa(arrayListOf(),object : AdapterSiswa.OnAdapterListener{
            override fun onClick(siswa: tbsiswa) {
                // read detail note
                intentEdit(siswa.nis,Constant.TYPE_READ)
            }

            override fun onUpdate(siswa: tbsiswa) {
                intentEdit(siswa.nis,Constant.TYPE_UPDATE)
            }
            override fun onDelete(siswa: tbsiswa) {
               deleteDialog( siswa )
            }
        })

        listData.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = noteAdapter

        }
    }

    private fun deleteDialog(siswa: tbsiswa) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("konfirmasi")
            setMessage("yakin hapus ${siswa.nama}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.siswaDAO().deleteSiswa(siswa)
                    loadNote()
                }
            }
        }
        alertDialog.show()
  }

}
