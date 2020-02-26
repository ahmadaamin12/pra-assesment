package com.d3ifcool.myapplication


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.d3ifcool.myapplication.databinding.FragmentSegtigaBinding
import kotlinx.android.synthetic.main.fragment_segtiga.*
import kotlin.math.sqrt

class Segitiga : Fragment() {
    private lateinit var binding: FragmentSegtigaBinding
    private var nilaiAlas: Double = 0.00
    private var nilaiTinggi: Double = 0.00
    private var nilaiSisiMiring: Double = 0.00
    private var nilaiLuas: Double = 0.00
    private var nilaiKeliling: Double = 0.00

    companion object {
        const val KEY_NILAIPYTHAGORAS = "key_nilaiPythagoras"
        const val KEY_NILAILUAS = "key_nilaiLuas"
        const val KEY_NILAIKELILING = "key_nilaiKeliling"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_segtiga, container, false)
        if (savedInstanceState != null) {
            nilaiSisiMiring = savedInstanceState.getDouble(KEY_NILAIPYTHAGORAS)
            nilaiLuas = savedInstanceState.getDouble(KEY_NILAILUAS)
            nilaiKeliling = savedInstanceState.getDouble(KEY_NILAIKELILING)
            updateNilai(nilaiSisiMiring, nilaiLuas, nilaiKeliling)
        }
        binding.ButtHitung.setOnClickListener {
            if (binding.Alas.text.toString().isNotEmpty() && binding.Tinggi.text.toString().isNotEmpty()) {
                nilaiAlas = binding.Alas.text.toString().toDouble()
                nilaiTinggi = binding.Tinggi.text.toString().toDouble()
                nilaiSisiMiring = sqrt(nilaiAlas * nilaiAlas + nilaiTinggi * nilaiTinggi)
                nilaiLuas = 0.5 * nilaiAlas * nilaiTinggi
                nilaiKeliling = nilaiAlas + nilaiTinggi + nilaiSisiMiring
                binding.LuasS.text = "Luas = $nilaiLuas"
                binding.KelilingS.text = "Keliling = $nilaiKeliling"
            } else {
                Toast.makeText(this.activity, "Field tidak boleh kosong!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.ButtShare.setOnClickListener {
            val textLuasSegitiga = LuasS.text.toString()
            val textKelilingSegitiga = KelilingS.text.toString()
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                textLuasSegitiga + "\n" + textKelilingSegitiga
            )
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Hasil hitung segitiga")
            startActivity(Intent.createChooser(shareIntent, "Share text via..."))
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble(KEY_NILAIPYTHAGORAS, nilaiSisiMiring)
        outState.putDouble(KEY_NILAIKELILING, nilaiKeliling)
        outState.putDouble(KEY_NILAILUAS, nilaiLuas)
    }

    @SuppressLint("SetTextI18n")
    private fun updateNilai(s1: Double, s2: Double, s3: Double) {
        binding.LuasS.setText("Luas = " + s2.toString())
        binding.KelilingS.setText("Keliling = " + s3.toString())
    }
}
