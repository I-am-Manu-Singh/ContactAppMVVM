package com.example.phoneapp

import android.app.Activity
import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.phoneapp.MVVM_acrh.AddEditActivityViewModel
import com.example.phoneapp.RoomDB.Entity.Contact
import com.example.phoneapp.databinding.ActivityAddEditBinding
import com.github.dhaval2404.imagepicker.ImagePicker

class AddEditActivity : AppCompatActivity() {
    private val binding: ActivityAddEditBinding by lazy {
        ActivityAddEditBinding.inflate(layoutInflater)

    }
    var contact = Contact()
    private lateinit var viewModel: AddEditActivityViewModel

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                binding.profileImg.setImageURI(fileUri)

                val imageBytes = contentResolver.openInputStream(fileUri)?.readBytes()
                contact.userProfileImg = imageBytes
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private var flag = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AddEditActivityViewModel::class.java]


        if (intent.hasExtra("FLAG")) {
            flag = intent.getIntExtra("FLAG", -1)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                contact = intent.getSerializableExtra("DATA", Contact::class.java)!!
            else {
                contact = intent.getSerializableExtra("DATA") as Contact

            }
            binding.saveBtn.text = "Update Contact"
            var imageByte = contact.userProfileImg

            if (imageByte != null) {
                var image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                binding.profileImg.setImageBitmap(image)
            }
            binding.inputEmail.setText(contact.userEmail)
            binding.inputPhone.setText(contact.userPhoneNo)
            binding.inputName.setText(contact.userName)
        }


        //open dialog for profile
        binding.profileImg.setOnLongClickListener {


            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_image)
            //set transparent background of dialog
            //set profile image in the dialog box show in full length
            val image = dialog.findViewById<ImageView>(R.id.image)
            val imageObject = binding.profileImg.drawable
            image.setImageDrawable(imageObject)

            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            // set dialog parameter height and width
            val lp = WindowManager.LayoutParams()
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT

            //blur background of dialog
            lp.flags = WindowManager.LayoutParams.FLAG_BLUR_BEHIND

            //set all attributes to dialog
            dialog.window?.attributes = lp
            dialog.show()
            true
        }

        binding.saveBtn.setOnClickListener {
            // Add data to Contact call by Contact database
            contact.userName = binding.inputName.text.toString()
            contact.userPhoneNo = binding.inputPhone.text.toString()
            contact.userEmail = binding.inputEmail.text.toString()


            if (flag == 1) {
                viewModel.updateData(contact) {
                    if (it != null) {
                        if (it > 0) {
                            Toast.makeText(
                                this@AddEditActivity,
                                "Update contact successfully!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            finish()
                        }
                    }
                }
            } else {
                if (contact.userName!!.isEmpty() && contact.userPhoneNo!!.isEmpty()) {
                    if (contact.userName!!.isEmpty()) {
//                    binding.inputName.error = "Enter name"
                        binding.editTxtLayoutName.helperText = "Required*"

                    }
                    if (contact.userPhoneNo!!.isEmpty()) {
                        binding.editTxtLayoutEmail.helperText = "Required*"
//                    binding.inputPhone.error = "Enter Phone Number"
                    }
                } else {
//                val db = DbBuilder.getDB(this)
//                val i = db?.contactDAO()?.createContact(contact)

                    viewModel.storeData(contact) {
                        if (it != null) {
                            if (it > 0) {
                                Toast.makeText(
                                    this@AddEditActivity,
                                    "Save contact successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }
                        }
                    }
                }
            }
        }
        binding.profileImg.setOnClickListener {
            ImagePicker.with(this)
//                .compress(1)
                .crop()
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
        binding.backBtn.setOnClickListener {
            finish()
        }


    }

}