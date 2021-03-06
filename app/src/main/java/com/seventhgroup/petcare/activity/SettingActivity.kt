package com.seventhgroup.petcare.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.seventhgroup.petcare.R
import com.seventhgroup.petcare.databinding.ActivityRegisterBinding
import com.seventhgroup.petcare.databinding.ActivitySettingBinding
import com.seventhgroup.petcare.databinding.FragmentSettingBinding
import com.seventhgroup.petcare.model.Pet
import com.seventhgroup.petcare.utils.FirebaseUtils

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var addPet: String
    private lateinit var addCollar: String
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.buttonAddPet.setOnClickListener {
            addPetToDb()
        }

        val settingNav: BottomNavigationView = findViewById(R.id.bottom_navigation_setting)
        settingNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btn_profile -> {
                    val mIntent = Intent(this@SettingActivity, ProfileActivity::class.java)
                    startActivity(mIntent)
                    finish()
                }
                R.id.btn_history -> {
                    val mIntent = Intent(this@SettingActivity, HistoryActivity::class.java)
                    startActivity(mIntent)
                    finish()
                    }
                R.id.btn_setting -> {
                    Toast.makeText(this, "You are already in the Setting SCREEN", Toast.LENGTH_SHORT).show()
                }
            }
            true}

    }

    private fun addPetToDb() {
        addPet = binding.addPetName.text.toString().trim()
        addCollar = binding.addCollarSerialNumber.text.toString().trim()

        val delim = ","
        val listPet = addPet.split(delim)
        val listCollar = addCollar.split(delim)

        for(i in listPet.indices){
            val petData = Pet(
                listPet[i],
                listCollar[i]
            )

            val db = FirebaseFirestore.getInstance()
            val user = FirebaseUtils.firebaseAuth.currentUser
            if (user != null) {
                uid = user.uid
            }
            db.collection("userData").document(uid).collection("pet")
                .add(petData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
                }
        }
    }
}