package com.example.phoneapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phoneapp.MVVM_acrh.MainActivityViewModel
import com.example.phoneapp.RoomDB.Entity.Contact
import com.example.phoneapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //    var contactList = ArrayList<Contact>()
    var contactList = ArrayList<Contact>()
    var viewModel: MainActivityViewModel? = null
    lateinit var adapter: ContactAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.addContact.setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java))
        }


        // for search the element in the ContactList
        binding.searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null) {
                    adapter.contactList = contactList
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this@MainActivity, "null condition", Toast.LENGTH_SHORT).show()
                } else {
                    if (s.length == 0 || s.isNullOrBlank() || s.isNullOrEmpty()) {
                        adapter.contactList = contactList
                        adapter.notifyDataSetChanged()

                        Toast.makeText(
                            this@MainActivity,
                            "No Contact Found!",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        var tempList = ArrayList<Contact>()
                        contactList.forEach {
                            if (it.userName != null) {
                                if (it.userName!!.contains(
                                        s,
                                        ignoreCase = true
                                    ) || it.userPhoneNo!!.contains(s, ignoreCase = true)
                                ) {
                                    tempList.add(it)
                                }
                            }
                        }
                        adapter.contactList = tempList
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })


        //If we have permission, then the part of the IF is working, otherwise the part of the ELSE will work.

        //contextCompat use for find the permission of our app
        if (ContextCompat.checkSelfPermission(
                this/*context of activity*/,
                Manifest.permission.CALL_PHONE/*find permission from manifest*/
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //if we have permission
            createUI()

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CALL_PHONE
            )
        ) {
            val alertDialog = AlertDialog.Builder(this)
                .setMessage("This app require read video permission to run")
                .setTitle("Permission Required")
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.CALL_PHONE
                        ), DIAl_CALL_PERMISSION
                    )
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .create()
            alertDialog.show()

        } else {
            //we don't have permission
            ActivityCompat.requestPermissions(
                this@MainActivity, arrayOf(Manifest.permission.CALL_PHONE),
                DIAl_CALL_PERMISSION
            )
        }
    }

    private fun createUI() {
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        binding.addContact.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddEditActivity::class.java))
        }
        viewModel!!.data.observeForever {
            contactList.clear()
            it.forEach {
                contactList.add(it)
            }
            adapter.notifyDataSetChanged()

//        for the empty item
            if (contactList.isEmpty()) {
                binding.emptyContact.visibility = View.VISIBLE
                binding.rvContact.visibility = View.GONE
            } else {
                binding.emptyContact.visibility = View.GONE
                binding.rvContact.visibility = View.VISIBLE
            }
        }


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            1,
            ItemTouchHelper.LEFT
                    or ItemTouchHelper.RIGHT
        ) {


            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (ItemTouchHelper.LEFT == direction) {
                    Toast.makeText(
                        this@MainActivity,
                        "Contact Deleted Successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel!!.deleteContact(contactList[viewHolder.adapterPosition])
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Call sent to " + contactList[viewHolder.adapterPosition].userName,
                        Toast.LENGTH_SHORT
                    ).show()
                    //set background for swap item
                    viewHolder.itemView.setBackgroundResource(R.drawable.back_shape)
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data =
                        Uri.parse("tel:${contactList[viewHolder.adapterPosition].userPhoneNo}")
                    startActivity(intent)
                    adapter.notifyDataSetChanged()
                }
            }

        }).attachToRecyclerView(binding.rvContact)

        adapter = ContactAdapter(contactList, this)
        binding.rvContact.layoutManager = LinearLayoutManager(this)
        binding.rvContact.adapter = adapter

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == DIAl_CALL_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createUI()
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CALL_PHONE
                )
            ) {
                val alertDialog = AlertDialog.Builder(this)
                    .setMessage("This app require Call permission to run")
                    .setTitle("Permission Required")
                    .setCancelable(false)
                    .setPositiveButton("OK") { dialog, which ->
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(
                                Manifest.permission.CALL_PHONE
                            ), DIAl_CALL_PERMISSION
                        )
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()
                alertDialog.show()
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf(Manifest.permission.CALL_PHONE),
                    DIAl_CALL_PERMISSION
                )
            }
        }
    }
}